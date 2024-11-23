package com.example.islab1backend.controllers;

import com.example.islab1backend.models.Location;
import com.example.islab1backend.models.Person;
import com.example.islab1backend.services.LocationService;
import com.example.islab1backend.services.PersonService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@RequestScoped
@Path("/person")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PersonController {
    @Inject
    private PersonService personService;

    @POST
    @Path("/create")
    public Response createPerson(Person person) {
        try {
            personService.createPerson(person);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid data").build();
        }
    }

}
