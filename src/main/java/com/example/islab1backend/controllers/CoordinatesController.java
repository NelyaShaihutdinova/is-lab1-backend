package com.example.islab1backend.controllers;

import com.example.islab1backend.models.Coordinates;
import com.example.islab1backend.services.CoordinatesService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import java.security.Principal;

@RequestScoped
@Path("/coordinates")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CoordinatesController {
    @Inject
    private CoordinatesService coordinatesService;

    @POST
    @Path("/create")
    public Response createCoordinates(@Context SecurityContext securityContext, Coordinates coordinates) {
        Principal userPrincipal = securityContext.getUserPrincipal();
        String username = userPrincipal.getName();
        try {
            coordinatesService.createCoordinates(coordinates);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid data").build();
        }
    }
}
