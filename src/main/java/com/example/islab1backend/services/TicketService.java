package com.example.islab1backend.services;

import com.example.islab1backend.dao.TicketDAO;
import com.example.islab1backend.models.Ticket;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class TicketService {
    @Inject
    private TicketDAO ticketDAO;

    public void createTicket(Ticket ticket) {
        ticketDAO.save(ticket);
    }

}
