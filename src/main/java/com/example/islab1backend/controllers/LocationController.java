package com.example.islab1backend.controllers;

import com.example.islab1backend.dto.responses.ErrorResponse;
import com.example.islab1backend.models.Location;
import com.example.islab1backend.services.AuditService;
import com.example.islab1backend.services.LocationService;
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
@Path("/location")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LocationController {
    @Inject
    private LocationService locationService;

    @Inject
    private AuditService auditService;

    @POST
    public Response createLocation(@Context SecurityContext securityContext, Location location) {
        try {
            Principal userPrincipal = securityContext.getUserPrincipal();
            String username = userPrincipal.getName();
            location.setCreationBy(username);
            String action = "create";
            locationService.createLocation(location);
            auditService.saveAudit(username, action);
            return Response.ok().build();
        }catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse(e.getMessage())).build();
        }  catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid data").build();
        }
    }

    @POST
    @Path("/{id}")
    public Response updateLocation(@Context SecurityContext securityContext, @PathParam("id") Long locationId, Location location) {
        try {
            Principal userPrincipal = securityContext.getUserPrincipal();
            String username = userPrincipal.getName();
            String action = "update";
            locationService.updateLocation(locationId, location, username);
            auditService.saveAudit(username, action);
            return Response.ok().build();
        }catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse(e.getMessage())).build();
        }  catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid data").build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteLocation(@Context SecurityContext securityContext, @PathParam("id") Long locationId, @QueryParam("replace") String repl) {
        Long replaceId = Long.parseLong(repl);
        try {
            Principal userPrincipal = securityContext.getUserPrincipal();
            String username = userPrincipal.getName();
            String action = "delete";
            locationService.deleteLocation(locationId, username, replaceId);
            auditService.saveAudit(username, action);
            return Response.ok().build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse(e.getMessage())).build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid data").build();
        }
    }

    @GET
    public Response showAllLocation(@QueryParam("page") @DefaultValue("1") int page,
                                    @QueryParam("size") @DefaultValue("10") int size) {
        List<Location> locations = locationService.getLocationPage(page, size);
        return Response.ok(locations).build();
    }
}
