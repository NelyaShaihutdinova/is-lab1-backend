package com.example.islab1backend.controllers;

import com.example.islab1backend.dto.DTOParser;
import com.example.islab1backend.dto.requests.PersonRequest;
import com.example.islab1backend.models.Person;
import com.example.islab1backend.services.AuditService;
import com.example.islab1backend.services.LocationService;
import com.example.islab1backend.services.PersonService;
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
@Path("/person")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PersonController {
    @Inject
    private PersonService personService;

    @Inject
    private AuditService auditService;

    @Inject
    private LocationService locationService;

    private final DTOParser parser = new DTOParser();

    @POST
    @Path("/create")
    public Response createPerson(@Context SecurityContext securityContext, PersonRequest personRequest) {
        Principal userPrincipal = securityContext.getUserPrincipal();
        String username = userPrincipal.getName();
        Person person = parser.parsePersonWithLocation(personRequest, locationService, username);
        String action = "create";
        try {
            personService.createPerson(person);
            auditService.saveAudit(username, action);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid data").build();
        }
    }

    @POST
    @Path("/update/{id}")
    public Response updatePerson(@Context SecurityContext securityContext, @PathParam("id") Long personId, PersonRequest personRequest) {
        Principal userPrincipal = securityContext.getUserPrincipal();
        String username = userPrincipal.getName();
        Person person = parser.parsePersonWithLocation(personRequest, locationService, username);
        String action = "update";
        try {
            personService.updatePerson(personId, person, username);
            auditService.saveAudit(username, action);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid data").build();
        }
    }

    @GET
    @Path("/info/{id}")
    public Response getPerson(@Context SecurityContext securityContext, @PathParam("id") Long personId) {
        try {
            return Response.ok(personService.getPerson(personId)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid data").build();
        }
    }

    @POST
    @Path("/delete/{id}")
    public Response deletePerson(@Context SecurityContext securityContext, @PathParam("id") Long personId) {
        Principal userPrincipal = securityContext.getUserPrincipal();
        String username = userPrincipal.getName();
        String action = "delete";
        try {
            personService.deletePerson(personId, username);
            auditService.saveAudit(username, action);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid data").build();
        }
    }

    @GET
    @Path("/show")
    public Response showAllPerson(@QueryParam("page") @DefaultValue("1") int page,
                                  @QueryParam("size") @DefaultValue("10") int size) {
        List<Person> personList = personService.getPersonPage(page, size);
        return Response.ok(personList).build();
    }
}
