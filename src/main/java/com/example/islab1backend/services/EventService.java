package com.example.islab1backend.services;

import com.example.islab1backend.dao.EventDAO;
import com.example.islab1backend.models.Coordinates;
import com.example.islab1backend.models.Event;
import com.example.islab1backend.models.EventType;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class EventService {
    @Inject
    private EventDAO eventDAO;

    @Transactional
    public void createEvent(Event event) {
        eventDAO.save(event);
    }

    @Transactional
    public void updateEvent(Long eventId, Event event, String username) {
        String name = event.getName();
        EventType eventType = event.getEventType();
        int ticketCount = event.getTicketsCount();
        eventDAO.update(eventId, name, eventType, ticketCount, username);
    }

    @Transactional
    public Event getEvent(Long eventId) {
        return eventDAO.findById(eventId);
    }

    @Transactional
    public void deleteEvent(Long eventId, String username) {
        eventDAO.delete(eventId, username);
    }

    @Transactional
    public List<Event> getEventPage(int pageNumber, int pageSize) {
        return eventDAO.getPaginatedEvent(pageNumber, pageSize);
    }

}
