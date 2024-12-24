package com.example.islab1backend.controllers;

//import com.example.islab1backend.dto.requests.FileUploadForm;

import com.example.islab1backend.dto.DTOParser;
import com.example.islab1backend.dto.responses.ErrorResponse;
import com.example.islab1backend.dto.responses.HistoryResponse;
import com.example.islab1backend.models.ImportHistory;
import com.example.islab1backend.models.ImportStatus;
import com.example.islab1backend.models.Ticket;
import com.example.islab1backend.services.ImportService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Path("/import")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.MULTIPART_FORM_DATA)
public class ImportController {
    @Inject
    private ImportService importService;

    private final DTOParser dtoParser = new DTOParser();


    @POST
    @Path("/upload")
    @Consumes("multipart/form-data")
    public Response uploadFile(List<EntityPart> parts, @Context SecurityContext securityContext) {
        var file = parts.stream().filter(part -> "file".equals(part.getName())).findFirst().orElseThrow();
        Principal userPrincipal = securityContext.getUserPrincipal();
        String username = userPrincipal.getName();
        try {
            InputStream fileInputStream = file.getContent();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            IOUtils.copy(fileInputStream, baos);
            InputStream fileStreamForDb = new ByteArrayInputStream(baos.toByteArray());
            InputStream fileStreamForMinio = new ByteArrayInputStream(baos.toByteArray());
            String json = IOUtils.toString(fileStreamForDb, StandardCharsets.UTF_8);
            ObjectMapper objectMapper = new ObjectMapper();
            List<Ticket> tickets = objectMapper.readValue(json,
                    objectMapper.getTypeFactory().constructCollectionType(List.class, Ticket.class));

            importService.importUserFile(username, tickets, file.getFileName().orElseThrow(), fileStreamForMinio);
            return Response.ok().build();
        } catch (IllegalArgumentException e) {
            ImportHistory history = new ImportHistory();
            history.setFileName(file.getFileName().orElseThrow());
            history.setUsername(username);
            history.setImportTime(LocalDateTime.now());
            history.setStatus(ImportStatus.FAILED);
            history.setNumberOfImportedRecords(0);
            importService.save(history);
            return Response.status(Response.Status.BAD_REQUEST).entity("Import failed: " + e.getMessage()).build();
        } catch (Exception e) {
            ImportHistory history = new ImportHistory();
            history.setFileName(file.getFileName().orElseThrow());
            history.setUsername(username);
            history.setImportTime(LocalDateTime.now());
            history.setStatus(ImportStatus.FAILED);
            history.setNumberOfImportedRecords(0);
            importService.save(history);
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Import failed: " + e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/history")
    public Response getHistory(@Context SecurityContext securityContext, @QueryParam("page") @DefaultValue("1") int page,
                               @QueryParam("size") @DefaultValue("10") int size,
                               @QueryParam("filter-value") String filter,
                               @QueryParam("filter-column") String filterColumn,
                               @QueryParam("sorted") String sorted) {
        Principal userPrincipal = securityContext.getUserPrincipal();
        String username = userPrincipal.getName();
        List<ImportHistory> historyList = importService.getHistory(username, page, size, filter, filterColumn, sorted);
        List<HistoryResponse> historyResponseList = new ArrayList<>();
        for (ImportHistory history : historyList) {
            HistoryResponse historyResponse = dtoParser.parseResponseHistory(history);
            historyResponseList.add(historyResponse);
        }
        return Response.ok(historyResponseList).build();
    }

    @POST
    @Path("/download/{id}")
    public Response downloadFile(@Context SecurityContext securityContext, @PathParam("id") Long importId) {
        try {
            Principal userPrincipal = securityContext.getUserPrincipal();
            String username = userPrincipal.getName();
            InputStream fileStream = importService.downloadFile(username, importId);

            return Response.ok(fileStream, MediaType.APPLICATION_OCTET_STREAM)
                    .header("Content-Disposition", "attachment; filename=\"" + importService.getFileNameById(importId) + "\"")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Failed to download file: " + e.getMessage())
                    .build();
        }
    }
}
