package com.example.islab1backend.controllers;

import com.example.islab1backend.models.Event;
import com.example.islab1backend.models.Location;
import com.example.islab1backend.services.EventService;
import com.example.islab1backend.services.LocationService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@RequestScoped
@Path("/event")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EventController {
    @Inject
    private EventService eventService;

    @POST
    @Path("/create")
    public Response createEvent(Event event) {
        try {
            eventService.createEvent(event);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid username or password").build();
        }
    }
}
