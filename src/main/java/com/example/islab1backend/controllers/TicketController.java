package com.example.islab1backend.controllers;

import com.example.islab1backend.dto.DTOParser;
import com.example.islab1backend.dto.requests.TicketRequest;
import com.example.islab1backend.dto.responses.ErrorResponse;
import com.example.islab1backend.filters.TicketValidator;
import com.example.islab1backend.models.Ticket;
import com.example.islab1backend.services.*;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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
    private final TicketValidator ticketValidator = new TicketValidator();

    @PersistenceContext
    private EntityManager em;

    @POST
    public Response createTicket(@Context SecurityContext securityContext, TicketRequest ticketRequest) {
        try {
            Principal userPrincipal = securityContext.getUserPrincipal();
            String username = userPrincipal.getName();
            Ticket ticket = parser.parseTicket(ticketRequest, venueService, personService, coordinatesService, eventService, username);
            String action = "create";
            ticketService.createTicket(em ,ticket);
            auditService.saveAudit(username, action);
            return Response.ok().build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse(e.getMessage())).build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(new ErrorResponse(e.getMessage())).build();
        }
    }

    @POST
    @Path("/{id}")
    public Response updateTicket(@Context SecurityContext securityContext, @PathParam("id") Long ticketId, TicketRequest ticketRequest) {
        try {
            Principal userPrincipal = securityContext.getUserPrincipal();
            String username = userPrincipal.getName();
            Ticket ticket = parser.parseTicket(ticketRequest, venueService, personService, coordinatesService, eventService, username);
            String action = "update";
            ticketValidator.validateUniqueTicketNameInEvent(em, ticket);
            ticketValidator.validateDiscountLimit(em, ticket);
            ticketValidator.validateVenueCapacity(em, ticket);
            ticketService.updateTicket(em, ticketId, ticket, username);
            auditService.saveAudit(username, action);
            return Response.ok().build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse(e.getMessage())).build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid data").build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteTicket(@Context SecurityContext securityContext, @PathParam("id") Long ticketId) {
        try {
            Principal userPrincipal = securityContext.getUserPrincipal();
            String username = userPrincipal.getName();
            String action = "delete";
            ticketService.deleteTicket(ticketId, username);
            auditService.saveAudit(username, action);
            return Response.ok().build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse(e.getMessage())).build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid data").build();
        }
    }

    @GET
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
        try {
            Principal userPrincipal = securityContext.getUserPrincipal();
            String username = userPrincipal.getName();
            String action = "delete";
            ticketService.deleteByEvent(eventId, username);
            auditService.saveAudit(username, action);
            return Response.ok().build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse(e.getMessage())).build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid data").build();
        }
    }

    @POST
    @Path("/by-person/{id}")
    public Response deleteTicketsByPerson(@Context SecurityContext securityContext, @PathParam("id") Long personId) {
        try {
            Principal userPrincipal = securityContext.getUserPrincipal();
            String username = userPrincipal.getName();
            String action = "delete";
            ticketService.deleteByPerson(personId, username);
            auditService.saveAudit(username, action);
            return Response.ok().build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorResponse(e.getMessage())).build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid data").build();
        }
    }
}
