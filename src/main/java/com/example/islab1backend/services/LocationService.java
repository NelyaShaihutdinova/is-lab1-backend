package com.example.islab1backend.services;

import com.example.islab1backend.dao.LocationDAO;
import com.example.islab1backend.models.Location;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class LocationService {
    @Inject
    private LocationDAO locationDAO;

    @Transactional
    public void createLocation(Location location) {
        locationDAO.save(location);
    }

    @Transactional
    public void updateLocation(Long locationId, Location location, String username) {
        int x = location.getX();
        long y = location.getY();
        long z = location.getZ();
        locationDAO.update(locationId, x, y, z, username);
    }

    @Transactional
    public Location getLocation(Long locationId) {
        return locationDAO.findById(locationId);
    }

    @Transactional
    public void deleteLocation(Long locationId, String username) {
        locationDAO.delete(locationId, username);
    }

    @Transactional
    public List<Location> getAllLocation() {
        return locationDAO.findLocation();
    }
}
