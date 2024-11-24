package com.example.islab1backend.services;

import com.example.islab1backend.dao.CoordinatesDAO;
import com.example.islab1backend.models.Coordinates;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class CoordinatesService {
    @Inject
    private CoordinatesDAO coordinatesDAO;

    @Transactional
    public void createCoordinates(Coordinates coordinates) {
        coordinatesDAO.save(coordinates);
    }

    @Transactional
    public void updateCoordinates(Long coordinatesId, Coordinates coordinates, String username) {
        long x = coordinates.getX();
        long y = coordinates.getY();
        coordinatesDAO.update(coordinatesId, x, y, username);
    }

    @Transactional
    public Coordinates getCoordinates(Long coordinatesId) {
        return coordinatesDAO.findById(coordinatesId);
    }

    @Transactional
    public void deleteCoordinates(Long coordinatesId, String username) {
        coordinatesDAO.delete(coordinatesId, username);
    }


    @Transactional
    public List<Coordinates> getCoordinatesPage(int pageNumber, int pageSize) {
        return coordinatesDAO.getPaginatedCoordinates(pageNumber, pageSize);
    }
}
