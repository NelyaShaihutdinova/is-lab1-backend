package com.example.islab1backend.controllers;

import com.example.islab1backend.models.Venue;
import com.example.islab1backend.services.AuditService;
import com.example.islab1backend.services.VenueService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import java.security.Principal;

@RequestScoped
@Path("/venue")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class VenueController {
    @Inject
    private VenueService venueService;

    @Inject
    private AuditService auditService;

    @POST
    @Path("/create")
    public Response createVenue(@Context SecurityContext securityContext, Venue venue) {
        Principal userPrincipal = securityContext.getUserPrincipal();
        String username = userPrincipal.getName();
        venue.setCreationBy(username);
        String action = "create";
        try {
            venueService.createVenue(venue);
            auditService.saveAudit(username, action);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid data").build();
        }
    }

    @POST
    @Path("/update/{id}")
    public Response updateVenue(@Context SecurityContext securityContext, @PathParam("id") Long venueId, Venue venue) {
        Principal userPrincipal = securityContext.getUserPrincipal();
        String username = userPrincipal.getName();
        String action = "update";
        try {
            venueService.updateVenue(venueId, venue, username);
            auditService.saveAudit(username, action);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid data").build();
        }
    }

    @GET
    @Path("/info/{id}")
    public Response getVenue(@Context SecurityContext securityContext, @PathParam("id") Long venueId) {
        try {
            return Response.ok(venueService.getVenue(venueId)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid data").build();
        }
    }

    @POST
    @Path("/delete/{id}")
    public Response deleteVenue(@Context SecurityContext securityContext, @PathParam("id") Long venueId) {
        Principal userPrincipal = securityContext.getUserPrincipal();
        String username = userPrincipal.getName();
        String action = "delete";
        try {
            venueService.deleteVenue(venueId, username);
            auditService.saveAudit(username, action);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid data").build();
        }
    }

    @GET
    @Path("/show")
    public Response showVenue(@Context SecurityContext securityContext) {
        return Response.ok(venueService.getAllVenue()).build();
    }
}
