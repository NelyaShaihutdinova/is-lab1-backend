package com.example.islab1backend.services;

import com.example.islab1backend.dao.EventDAO;
import com.example.islab1backend.dao.LocationDAO;
import com.example.islab1backend.models.Event;
import com.example.islab1backend.models.Location;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class EventService {
    @Inject
    private EventDAO eventDAO;

    public void createEvent(Event event) {
        eventDAO.save(event);
    }

}
