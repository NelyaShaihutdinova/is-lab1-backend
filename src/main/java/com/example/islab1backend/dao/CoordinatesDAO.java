package com.example.islab1backend.dao;

import com.example.islab1backend.models.Coordinates;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class CoordinatesDAO {
    @PersistenceContext
    private EntityManager em;

    public void save(Coordinates coordinates) {
        em.persist(coordinates);
    }

    public void update(Long coordinatesId, long x, long y, String username) {
        Coordinates coordinates = em.find(Coordinates.class, coordinatesId);
        if (coordinates != null) {
            if (Objects.equals(coordinates.getCreationBy(), username)) {
                coordinates.setX(x);
                coordinates.setY(y);
            } else {
                throw new RuntimeException("You don't have enough rights");
            }
        } else {
            throw new RuntimeException("Coordinates not found");
        }

    }

    public Coordinates findById(Long coordinatesId) {
        return em.find(Coordinates.class, coordinatesId);
    }

    public void delete(Long coordinatesId, String username) {
        Coordinates coordinates = findById(coordinatesId);
        if (Objects.equals(coordinates.getCreationBy(), username)) {
            em.remove(coordinates);
        } else {
            throw new RuntimeException("You don't have enough rights");
        }
    }

    public List<Coordinates> findCoordinates() {
        return em.createQuery("SELECT c FROM Coordinates c", Coordinates.class)
                .getResultList();
    }
}
