package com.example.islab1backend.services;

import com.example.islab1backend.dao.LocationDAO;
import com.example.islab1backend.dao.VenueDAO;
import com.example.islab1backend.models.Location;
import com.example.islab1backend.models.Venue;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class VenueService {
    @Inject
    private VenueDAO venueDAO;

    public void createVenue(Venue venue) {
        venueDAO.save(venue);
    }

}
