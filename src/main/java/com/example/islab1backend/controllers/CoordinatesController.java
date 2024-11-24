package com.example.islab1backend.controllers;

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
    @Path("/create")
    public Response createCoordinates(@Context SecurityContext securityContext, Coordinates coordinates) {
        Principal userPrincipal = securityContext.getUserPrincipal();
        String username = userPrincipal.getName();
        coordinates.setCreationBy(username);
        String action = "create";
        try {
            coordinatesService.createCoordinates(coordinates);
            auditService.saveAudit(username, action);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid data").build();
        }
    }

    @POST
    @Path("/update/{id}")
    public Response updateCoordinates(@Context SecurityContext securityContext, @PathParam("id") Long coordinatesId, Coordinates coordinates) {
        Principal userPrincipal = securityContext.getUserPrincipal();
        String username = userPrincipal.getName();
        String action = "update";
        try {
            coordinatesService.updateCoordinates(coordinatesId, coordinates, username);
            auditService.saveAudit(username, action);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid data").build();
        }
    }

    @GET
    @Path("/info/{id}")
    public Response getCoordinates(@Context SecurityContext securityContext, @PathParam("id") Long coordinatesId) {
        try {
            return Response.ok(coordinatesService.getCoordinates(coordinatesId)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid data").build();
        }
    }

    @POST
    @Path("/delete/{id}")
    public Response deleteCoordinates(@Context SecurityContext securityContext, @PathParam("id") Long coordinatesId) {
        Principal userPrincipal = securityContext.getUserPrincipal();
        String username = userPrincipal.getName();
        String action = "delete";
        try {
            coordinatesService.deleteCoordinates(coordinatesId, username);
            auditService.saveAudit(username, action);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid data").build();
        }
    }

    @GET
    @Path("/show")
    public Response getAllCoordinates(@QueryParam("page") @DefaultValue("1") int page,
                               @QueryParam("size") @DefaultValue("10") int size){
        List<Coordinates> coordinates = coordinatesService.getCoordinatesPage(page, size);
        return Response.ok(coordinates).build();
    }
}
