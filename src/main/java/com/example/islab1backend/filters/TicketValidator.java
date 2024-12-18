package com.example.islab1backend.filters;

import com.example.islab1backend.models.Ticket;
import jakarta.persistence.EntityManager;

public class TicketValidator {
    public void validateUniqueTicketNameInEvent(EntityManager entityManager, Ticket ticket) {
        Long count = (Long) entityManager.createQuery(
                        "SELECT COUNT(t) FROM Ticket t WHERE t.name = :name AND t.event = :event"
                )
                .setParameter("name", ticket.getName())
                .setParameter("event", ticket.getEvent())
                .getSingleResult();

        if (count > 0) {
            throw new IllegalArgumentException("Ticket name must be unique within the same event");
        }
    }

    public void validateDiscountLimit(EntityManager entityManager, Ticket ticket) {
        Long count = (Long) entityManager.createQuery(
                        "SELECT COUNT(t) FROM Ticket t WHERE t.event = :event AND t.discount = :discount"
                )
                .setParameter("event", ticket.getEvent())
                .setParameter("discount", ticket.getDiscount())
                .getSingleResult();

        if (count >= 10) {
            throw new IllegalArgumentException("Cannot create more than 10 tickets with the same discount for an event");
        }
    }

    public void validateVenueCapacity(EntityManager entityManager, Ticket ticket) {
        Long ticketsCount = (Long) entityManager.createQuery(
                        "SELECT COUNT(t) FROM Ticket t WHERE t.venue = :venue"
                )
                .setParameter("venue", ticket.getVenue())
                .getSingleResult();

        if (ticketsCount >= ticket.getVenue().getCapacity()) {
            throw new IllegalArgumentException("Cannot create tickets exceeding venue capacity");
        }
    }
}
