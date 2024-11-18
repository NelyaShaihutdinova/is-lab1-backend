package com.example.islab1backend.controllers;

import com.example.islab1backend.models.Ticket;
import com.example.islab1backend.services.TicketService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@RequestScoped
@Path("/ticket")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TicketController {
    @Inject
    private TicketService ticketService;

    @POST
    @Path("/create")
    public Response createTicket(Ticket ticket) {
        try {
            ticketService.createTicket(ticket);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid username or password").build();
        }
    }
}
