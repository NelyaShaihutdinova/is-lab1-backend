package com.example.islab1backend.controllers;

import com.example.islab1backend.models.Location;
import com.example.islab1backend.models.Venue;
import com.example.islab1backend.services.LocationService;
import com.example.islab1backend.services.VenueService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@RequestScoped
@Path("/venue")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class VenueController {
    @Inject
    private VenueService venueService;

    @POST
    @Path("/create")
    public Response createVenue(Venue venue) {
        try {
            venueService.createVenue(venue);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid username or password").build();
        }
    }
}
