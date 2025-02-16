package com.example.islab1backend.controllers;

import com.example.islab1backend.dto.responses.ErrorResponse;
import com.example.islab1backend.models.Coordinates;
import com.example.islab1backend.services.AuditService;
import com.example.islab1backend.services.CoordinatesService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import java.security.Principal;
import java.util.List;

@RequestScoped
@Path("/coordinates")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CoordinatesController {
    @Inject
    private CoordinatesService coordinatesService;

    @Inject
    private AuditService auditService;

    @POST
    public Response createCoordinates(@Context SecurityContext securityContext, Coordinates coordinates) {
        try {
            Principal userPrincipal = securityContext.getUserPrincipal();
            String username = userPrincipal.getName();
            coordinates.setCreationBy(username);
            String action = "create";
            coordinatesService.createCoordinates(coordinates);
            auditService.saveAudit(username, action);
            return Response.ok().build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse(e.getMessage())).build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid data").build();
        }
    }

    @POST
    @Path("/{id}")
    public Response updateCoordinates(@Context SecurityContext securityContext, @PathParam("id") Long coordinatesId, Coordinates coordinates) {
        try {
            Principal userPrincipal = securityContext.getUserPrincipal();
            String username = userPrincipal.getName();
            String action = "update";
            coordinatesService.updateCoordinates(coordinatesId, coordinates, username);
            auditService.saveAudit(username, action);
            return Response.ok().build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse(e.getMessage())).build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid data").build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteCoordinates(@Context SecurityContext securityContext, @PathParam("id") Long coordinatesId, @QueryParam("replace") String repl) {
        Long replaceId = Long.parseLong(repl);
        try {
            Principal userPrincipal = securityContext.getUserPrincipal();
            String username = userPrincipal.getName();
            String action = "delete";
            coordinatesService.deleteCoordinates(coordinatesId, username, replaceId);
            auditService.saveAudit(username, action);
            return Response.ok().build();
        }catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse(e.getMessage())).build();
        }  catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid data").build();
        }
    }

    @GET
    public Response getAllCoordinates(@QueryParam("page") @DefaultValue("1") int page,
                               @QueryParam("size") @DefaultValue("10") int size){
        List<Coordinates> coordinates = coordinatesService.getCoordinatesPage(page, size);
        return Response.ok(coordinates).build();
    }
}
