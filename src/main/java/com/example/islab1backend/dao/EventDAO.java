package com.example.islab1backend.dao;

import com.example.islab1backend.models.Event;
import com.example.islab1backend.models.EventType;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class EventDAO {
    @PersistenceContext
    private EntityManager em;

    public void save(Event event) {
        em.persist(event);
    }

    public void update(Long eventId, String name, EventType eventType, int ticketsCount, String username) {
        Event event = em.find(Event.class, eventId);
        if (event != null) {
            if (Objects.equals(event.getCreationBy(), username)) {
                event.setName(name);
                event.setEventType(eventType);
                event.setTicketsCount(ticketsCount);
            } else {
                throw new RuntimeException("You don't have enough rights");
            }
        } else {
            throw new RuntimeException("Event not found");
        }

    }

    public Event findById(Long eventId) {
        return em.find(Event.class, eventId);
    }

    public void delete(Long eventId, String username) {
        Event event = findById(eventId);
        if (Objects.equals(event.getCreationBy(), username)) {
            em.remove(event);
        }
    }

    public List<Event> findEvent() {
        return em.createQuery("SELECT c FROM Event c", Event.class)
                .getResultList();
    }
}
