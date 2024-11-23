package com.example.islab1backend.services;

import com.example.islab1backend.dao.CoordinatesDAO;
import com.example.islab1backend.models.Coordinates;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class CoordinatesService {
    @Inject
    private CoordinatesDAO coordinatesDAO;

    @Transactional
    public void createCoordinates(Coordinates coordinates) {
        coordinatesDAO.save(coordinates);
    }

}
