package com.example.islab1backend.services;

import com.example.islab1backend.dao.TicketDAO;
import com.example.islab1backend.models.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class TicketService {
    @Inject
    private TicketDAO ticketDAO;

    @Transactional
    public void createTicket(Ticket ticket) {
        ticketDAO.save(ticket);
    }

    @Transactional
    public void updateTicket(Long ticketId, Ticket ticket, String username) {
        String name = ticket.getName();
        Coordinates coordinates = ticket.getCoordinates();
        Person person = ticket.getPerson();
        Event event = ticket.getEvent();
        int price = ticket.getPrice();
        TicketType ticketType = ticket.getTicketType();
        Long discount = ticket.getDiscount();
        float number = ticket.getNumber();
        String comment = ticket.getComment();
        boolean refundable = ticket.isRefundable();
        Venue venue = ticket.getVenue();
        ticketDAO.update(ticketId, name, coordinates, person, event, price, ticketType, discount, number, comment, refundable, venue, username);
    }

    @Transactional
    public Ticket getTicket(Long ticketId) {
        return ticketDAO.findById(ticketId);
    }

    @Transactional
    public void deleteTicket(Long ticketId, String username) {
        ticketDAO.delete(ticketId, username);
    }

    @Transactional
    public List<Ticket> getTicketPage(int pageNumber, int pageSize, String filter, String filterColumn, String sorted) {
        return ticketDAO.getPaginatedTicket(pageNumber, pageSize, filter, filterColumn, sorted);
    }

    @Transactional
    public long getByNumber(float number) {
        return ticketDAO.getCountOfTicketsByNumber(number);
    }

    @Transactional
    public List<Ticket> getByRefundable(boolean refundable) {
        return ticketDAO.getTicketsByRefundable(refundable);
    }

    @Transactional
    public List<Ticket> getByVenue(String venue) {
        return ticketDAO.getTicketsByVenue(venue);
    }

    @Transactional
    public void deleteByEvent(Long eventId, String username) {
        ticketDAO.cancelEvent(eventId, username);
    }

    @Transactional
    public void deleteByPerson(Long personId, String username) {
        ticketDAO.cancelBookingForPerson(personId, username);
    }
}
