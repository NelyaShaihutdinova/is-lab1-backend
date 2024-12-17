//package com.example.islab1backend.controllers;
//
//import com.example.islab1backend.dto.requests.FileUploadForm;
//import com.example.islab1backend.dto.responses.ErrorResponse;
//import com.example.islab1backend.models.ImportHistory;
//import com.example.islab1backend.models.Ticket;
////import com.example.islab1backend.services.ImportService;
//import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import jakarta.inject.Inject;
//import jakarta.ws.rs.*;
//import jakarta.ws.rs.core.Context;
//import jakarta.ws.rs.core.MediaType;
//import jakarta.ws.rs.core.Response;
//import jakarta.ws.rs.core.SecurityContext;
//import org.apache.commons.io.IOUtils;
//
//import java.io.InputStream;
//import java.nio.charset.StandardCharsets;
//import java.security.Principal;
//import java.util.List;
//
//@Path("/import")
//@Produces(MediaType.APPLICATION_JSON)
//@Consumes(MediaType.MULTIPART_FORM_DATA)
//public class ImportController {
////    @Inject
////    private ImportService importService;
//
//    @POST
//    @Path("/upload")
//    @Consumes(MediaType.MULTIPART_FORM_DATA)
//    public Response uploadFile(@Context SecurityContext securityContext,
//                               /*@MultipartForm*/ FileUploadForm form) {
//        try {
//            InputStream fileInputStream = form.getFile();
//            String json = IOUtils.toString(fileInputStream, StandardCharsets.UTF_8);
//
//            // Парсим JSON
//            ObjectMapper objectMapper = new ObjectMapper();
//            List<Ticket> tickets = objectMapper.readValue(json,
//                    objectMapper.getTypeFactory().constructCollectionType(List.class, Ticket.class));
//
//            // Основная логика обработки
//            Principal userPrincipal = securityContext.getUserPrincipal();
//            String username = userPrincipal.getName();
////            ImportHistory history = importService.importObjects(username, tickets);
//
//            return Response.ok(/*history*/).build();
//        } catch (IllegalArgumentException e) {
//            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse(e.getMessage())).build();
//        } catch (Exception e) {
//            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
//                    .entity("Import failed: " + e.getMessage())
//                    .build();
//        }
//    }
//
//    @GET
//    @Path("/history")
//    public Response getHistory(@Context SecurityContext securityContext) {
//        Principal userPrincipal = securityContext.getUserPrincipal();
//        String username = userPrincipal.getName();
////        List<ImportHistory> historyList = importService.getHistory(username);
//        return Response.ok(/*historyList*/).build();
//    }
//}
