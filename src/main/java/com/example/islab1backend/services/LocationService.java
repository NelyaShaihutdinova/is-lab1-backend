package com.example.islab1backend.services;

import com.example.islab1backend.dao.LocationDAO;
import com.example.islab1backend.dao.TicketDAO;
import com.example.islab1backend.models.Location;
import com.example.islab1backend.models.Ticket;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class LocationService {
    @Inject
    private LocationDAO locationDAO;

    public void createLocation(Location location) {
        locationDAO.save(location);
    }

}
