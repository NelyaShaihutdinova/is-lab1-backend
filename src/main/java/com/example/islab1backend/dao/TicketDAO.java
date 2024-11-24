package com.example.islab1backend.dao;

import com.example.islab1backend.models.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

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

    public List<Ticket> getPaginatedTicket(int pageNumber, int pageSize, String filterValue, String filterField, String sorted) {

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Ticket> query = cb.createQuery(Ticket.class);
        Root<Ticket> root = query.from(Ticket.class);

        if (filterField != null && filterValue != null) {
            Predicate filterPredicate = cb.like(root.get(filterField).as(String.class), "%" + filterValue + "%");
            query.where(filterPredicate);
        }

        if (sorted != null && !sorted.isEmpty()) {
            query.orderBy(cb.asc(root.get(sorted)));
        } else {
            query.orderBy(cb.asc(root.get("id")));
        }

        TypedQuery<Ticket> typedQuery = em.createQuery(query);

        typedQuery.setFirstResult((pageNumber - 1) * pageSize);
        typedQuery.setMaxResults(pageSize);

        return typedQuery.getResultList();
    }

    public long getCountOfTicketsByNumber(float number) {
        return em.createQuery("SELECT COUNT(t) FROM Ticket t WHERE t.number < :number", Long.class)
                .setParameter("number", number)
                .getSingleResult();
    }

    public List<Ticket> getTicketsByRefundable(boolean refundable) {
        return em.createQuery("SELECT t FROM Ticket t WHERE t.refundable < :refundable", Ticket.class)
                .setParameter("refundable", refundable)
                .getResultList();
    }

    public List<Ticket> getTicketsByVenue(String venueName) {
        return em.createQuery("SELECT t FROM Ticket t WHERE t.venue.name > :venueName", Ticket.class)
                .setParameter("venueName", venueName)
                .getResultList();
    }

    public void cancelEvent(Long eventId, String username) {
        Event event = em.find(Event.class, eventId);
        if (Objects.equals(event.getCreationBy(), username)) {
            em.createQuery("DELETE FROM Ticket t WHERE t.event.id = :eventId")
                    .setParameter("eventId", eventId)
                    .executeUpdate();

            em.createQuery("DELETE FROM Event e WHERE e.id = :eventId")
                    .setParameter("eventId", eventId)
                    .executeUpdate();
        } else {
            throw new RuntimeException("You don't have enough rights");
        }
    }

    public void cancelBookingForPerson(Long personId, String username) {
        Person person = em.find(Person.class, personId);
        if (Objects.equals(person.getCreationBy(), username)) {
            em.createQuery("DELETE FROM Ticket t WHERE t.person.id = :personId")
                    .setParameter("personId", personId)
                    .executeUpdate();
        } else {
            throw new RuntimeException("You don't have enough rights");
        }
    }
}
