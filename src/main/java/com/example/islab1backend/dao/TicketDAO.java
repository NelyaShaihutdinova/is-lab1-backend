package com.example.islab1backend.dao;

import com.example.islab1backend.models.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class TicketDAO {
    @PersistenceContext
    private EntityManager em;

    public void save(Ticket ticket) {
        em.persist(ticket);
    }

    public void update(Long ticketId, String name, Coordinates coordinates, Person person, Event event, int price, TicketType ticketType, Long discount, float number, String comment, boolean refundable, Venue venue, String username) {
        Ticket ticket = em.find(Ticket.class, ticketId);
        if (ticket != null) {
            if (Objects.equals(ticket.getCreationBy(), username)) {
                ticket.setName(name);
                ticket.setCoordinates(coordinates);
                ticket.setPerson(person);
                ticket.setEvent(event);
                ticket.setPrice(price);
                ticket.setTicketType(ticketType);
                ticket.setDiscount(discount);
                ticket.setNumber(number);
                ticket.setComment(comment);
                ticket.setRefundable(refundable);
                ticket.setVenue(venue);
            } else {
                throw new RuntimeException("You don't have enough rights");
            }
        } else {
            throw new RuntimeException("Ticket not found");
        }

    }

    public Ticket findById(Long ticketId) {
        return em.find(Ticket.class, ticketId);
    }

    public void delete(Long ticketId, String username) {
        Ticket ticket = findById(ticketId);
        if (Objects.equals(ticket.getCreationBy(), username)) {
            em.remove(ticket);
        } else {
            throw new RuntimeException("You don't have enough rights");
        }
    }

    public List<Ticket> getPaginatedTicket(int pageNumber, int pageSize) {
        int offset = (pageNumber - 1) * pageSize;
        return em.createQuery("SELECT c FROM Ticket c", Ticket.class)
                .setFirstResult(offset)
                .setMaxResults(pageSize)
                .getResultList();
    }
}
