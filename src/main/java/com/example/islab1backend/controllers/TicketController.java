package com.example.islab1backend.controllers;

import com.example.islab1backend.dto.DTOParser;
import com.example.islab1backend.dto.requests.TicketRequest;
import com.example.islab1backend.models.Ticket;
import com.example.islab1backend.services.*;
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
@Path("/ticket")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TicketController {
    @Inject
    private TicketService ticketService;

    @Inject
    private AuditService auditService;

    @Inject
    private CoordinatesService coordinatesService;

    @Inject
    private PersonService personService;

    @Inject
    private VenueService venueService;

    @Inject
    private EventService eventService;

    private final DTOParser parser = new DTOParser();

    @POST
    @Path("/create")
    public Response createTicket(@Context SecurityContext securityContext, TicketRequest ticketRequest) {
        Principal userPrincipal = securityContext.getUserPrincipal();
        String username = userPrincipal.getName();
        Ticket ticket = parser.parseTicket(ticketRequest, venueService, personService, coordinatesService, eventService, username);
        String action = "create";
        try {
            ticketService.createTicket(ticket);
            auditService.saveAudit(username, action);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid data").build();
        }
    }

    @POST
    @Path("/update/{id}")
    public Response updateTicket(@Context SecurityContext securityContext, @PathParam("id") Long ticketId, TicketRequest ticketRequest) {
        Principal userPrincipal = securityContext.getUserPrincipal();
        String username = userPrincipal.getName();
        Ticket ticket = parser.parseTicket(ticketRequest, venueService, personService, coordinatesService, eventService, username);
        String action = "update";
        try {
            ticketService.updateTicket(ticketId, ticket, username);
            auditService.saveAudit(username, action);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid data").build();
        }
    }

    @GET
    @Path("/info/{id}")
    public Response getTicket(@Context SecurityContext securityContext, @PathParam("id") Long ticketId) {
        try {
            return Response.ok(ticketService.getTicket(ticketId)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid data").build();
        }
    }

    @POST
    @Path("/delete/{id}")
    public Response deleteTicket(@Context SecurityContext securityContext, @PathParam("id") Long ticketId) {
        Principal userPrincipal = securityContext.getUserPrincipal();
        String username = userPrincipal.getName();
        String action = "delete";
        try {
            ticketService.deleteTicket(ticketId, username);
            auditService.saveAudit(username, action);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid data").build();
        }
    }

    @GET
    @Path("/show")
    public Response showAllTicket(@QueryParam("page") @DefaultValue("1") int page,
                                  @QueryParam("size") @DefaultValue("10") int size,
                                  @QueryParam("filter-value") String filter,
                                  @QueryParam("filter-column") String filterColumn,
                                  @QueryParam("sorted") String sorted
    ) {
        List<Ticket> tickets = ticketService.getTicketPage(page, size, filter, filterColumn, sorted);
        return Response.ok(tickets).build();
    }

    @GET
    @Path("/by-number")
    public Response showTicketsByMaxNumber(@QueryParam("number") @DefaultValue("100") float number) {
        long count = ticketService.getByNumber(number);
        return Response.ok(count).build();
    }

    @GET
    @Path("/by-refundable")
    public Response showTicketsByMaxRefundable(@QueryParam("refundable") @DefaultValue("true") boolean refundable) {
        List<Ticket> tickets = ticketService.getByRefundable(refundable);
        return Response.ok(tickets).build();
    }

    @GET
    @Path("/by-venue")
    public Response showTicketsByMinVenue(@QueryParam("venue") String venueName) {
        return Response.ok(ticketService.getByVenue(venueName)).build();
    }

    @POST
    @Path("/by-event/{id}")
    public Response deleteTicketsByEvent(@Context SecurityContext securityContext, @PathParam("id") Long eventId) {
        Principal userPrincipal = securityContext.getUserPrincipal();
        String username = userPrincipal.getName();
        String action = "delete";
        try {
            ticketService.deleteByEvent(eventId, username);
            auditService.saveAudit(username, action);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid data").build();
        }
    }

    @POST
    @Path("/by-person/{id}")
    public Response deleteTicketsByPerson(@Context SecurityContext securityContext, @PathParam("id") Long personId) {
        Principal userPrincipal = securityContext.getUserPrincipal();
        String username = userPrincipal.getName();
        String action = "delete";
        try {
            ticketService.deleteByPerson(personId, username);
            auditService.saveAudit(username, action);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid data").build();
        }
    }
}
