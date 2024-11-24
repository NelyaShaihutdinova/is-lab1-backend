package com.example.islab1backend.services;

import com.example.islab1backend.dao.VenueDAO;
import com.example.islab1backend.models.Coordinates;
import com.example.islab1backend.models.Venue;
import com.example.islab1backend.models.VenueType;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class VenueService {
    @Inject
    private VenueDAO venueDAO;

    @Transactional
    public void createVenue(Venue venue) {
        venueDAO.save(venue);
    }

    @Transactional
    public void updateVenue(Long venueId, Venue venue, String username) {
        String name = venue.getName();
        Long capacity = venue.getCapacity();
        VenueType venueType = venue.getVenueType();
        venueDAO.update(venueId, name, capacity, venueType, username);
    }

    @Transactional
    public Venue getVenue(Long venueId) {
        return venueDAO.findById(venueId);
    }

    @Transactional
    public void deleteVenue(Long venueId, String username) {
        venueDAO.delete(venueId, username);
    }

    @Transactional
    public List<Venue> getVenuePage(int pageNumber, int pageSize) {
        return venueDAO.getPaginatedVenue(pageNumber, pageSize);
    }
}
