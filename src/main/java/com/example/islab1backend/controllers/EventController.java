package com.example.islab1backend.controllers;

import com.example.islab1backend.models.Event;
import com.example.islab1backend.services.AuditService;
import com.example.islab1backend.services.EventService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import java.security.Principal;

@RequestScoped
@Path("/event")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EventController {
    @Inject
    private EventService eventService;

    @Inject
    private AuditService auditService;

    @POST
    @Path("/create")
    public Response createEvent(@Context SecurityContext securityContext, Event event) {
        Principal userPrincipal = securityContext.getUserPrincipal();
        String username = userPrincipal.getName();
        event.setCreationBy(username);
        String action = "create";
        try {
            eventService.createEvent(event);
            auditService.saveAudit(username, action);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid data").build();
        }
    }

    @POST
    @Path("/update/{id}")
    public Response updateEvent(@Context SecurityContext securityContext, @PathParam("id") Long eventId, Event event) {
        Principal userPrincipal = securityContext.getUserPrincipal();
        String username = userPrincipal.getName();
        String action = "update";
        try {
            eventService.updateEvent(eventId, event, username);
            auditService.saveAudit(username, action);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid data").build();
        }
    }

    @GET
    @Path("/info/{id}")
    public Response getEvent(@Context SecurityContext securityContext, @PathParam("id") Long eventId) {
        try {
            return Response.ok(eventService.getEvent(eventId)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid data").build();
        }
    }

    @POST
    @Path("/delete/{id}")
    public Response deleteEvent(@Context SecurityContext securityContext, @PathParam("id") Long eventId) {
        Principal userPrincipal = securityContext.getUserPrincipal();
        String username = userPrincipal.getName();
        String action = "delete";
        try {
            eventService.deleteEvent(eventId, username);
            auditService.saveAudit(username, action);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid data").build();
        }
    }

    @GET
    @Path("/show")
    public Response showEvent(@Context SecurityContext securityContext) {
        return Response.ok(eventService.getAllEvent()).build();
    }
}
