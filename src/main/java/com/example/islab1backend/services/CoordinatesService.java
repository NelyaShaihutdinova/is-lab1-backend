package com.example.islab1backend.services;

import com.example.islab1backend.dao.CoordinatesDAO;
import com.example.islab1backend.dao.LocationDAO;
import com.example.islab1backend.models.Coordinates;
import com.example.islab1backend.models.Location;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class CoordinatesService {
    @Inject
    private CoordinatesDAO coordinatesDAO;

    public void createCoordinates(Coordinates coordinates) {
        coordinatesDAO.save(coordinates);
    }

}
