package com.example.islab1backend.dao;

import com.example.islab1backend.models.Coordinates;
import com.example.islab1backend.models.Event;
import com.example.islab1backend.models.EventType;
import com.example.islab1backend.models.Event;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class EventDAO {
    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserDAO userDAO;

    public void save(Event event) {
        em.persist(event);
    }

    public void update(Long eventId, String name, EventType eventType, int ticketsCount, String username) {
        Event event = em.find(Event.class, eventId);
        if (event != null) {
            if (Objects.equals(event.getCreationBy(), username) || Objects.equals(userDAO.findByUsername(username).get().getRole().toString(), "ADMIN")) {
                event.setName(name);
                event.setEventType(eventType);
                event.setTicketsCount(ticketsCount);
            } else {
                throw new IllegalArgumentException("You don't have enough rights");
            }
        } else {
            throw new IllegalArgumentException("Event with this id not found");
        }
    }

    public Event findById(Long eventId) {
        return em.find(Event.class, eventId);
    }

    public void delete(Long eventId, String username, Long replaceId) {
        Event event = findById(eventId);
        if (event != null) {
            Event replace = em.find(Event.class, replaceId);
            if (replace != null) {
                if (!Objects.equals(eventId, replaceId)) {
                    if (Objects.equals(event.getCreationBy(), username) || Objects.equals(userDAO.findByUsername(username).get().getRole().toString(), "ADMIN")) {
                        em.createQuery("UPDATE Ticket t SET t.event.id = :replaceId WHERE t.event.id = :eventId")
                                .setParameter("replaceId", replaceId).setParameter("eventId", eventId)
                                .executeUpdate();
                        em.remove(event);
                    } else {
                        throw new IllegalArgumentException("You don't have enough rights");
                    }
                } else {
                    throw new IllegalArgumentException("You delete this event. Change event id to replace");
                }
            } else {
                throw new IllegalArgumentException("Event to replace with this id not found");
            }
        } else {
            throw new IllegalArgumentException("Event with this id not found");
        }
    }

    public List<Event> getPaginatedEvent(int pageNumber, int pageSize) {
        int offset = (pageNumber - 1) * pageSize;
        return em.createQuery("SELECT c FROM Event c", Event.class)
                .setFirstResult(offset)
                .setMaxResults(pageSize)
                .getResultList();
    }
}
