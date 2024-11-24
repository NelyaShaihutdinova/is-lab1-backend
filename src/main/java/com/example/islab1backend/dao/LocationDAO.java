package com.example.islab1backend.dao;

import com.example.islab1backend.models.Coordinates;
import com.example.islab1backend.models.Location;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class LocationDAO {
    @PersistenceContext
    private EntityManager em;

    public void save(Location location){
        em.persist(location);
    }

    public void update(Long locationId, int x, long y, long z, String username) {
        Location location = em.find(Location.class, locationId);
        if (location != null) {
            if (Objects.equals(location.getCreationBy(), username)) {
                location.setX(x);
                location.setY(y);
                location.setZ(z);
            } else {
                throw new RuntimeException("You don't have enough rights");
            }
        } else {
            throw new RuntimeException("Location not found");
        }

    }

    public Location findById(Long locationId) {
        return em.find(Location.class, locationId);
    }

    public void delete(Long locationId, String username) {
        Location location = findById(locationId);
        if (Objects.equals(location.getCreationBy(), username)) {
            em.remove(location);
        } else {
            throw new RuntimeException("You don't have enough rights");
        }
    }

    public List<Location> getPaginatedLocation(int pageNumber, int pageSize) {
        int offset = (pageNumber - 1) * pageSize;
        return em.createQuery("SELECT c FROM Location c", Location.class)
                .setFirstResult(offset)
                .setMaxResults(pageSize)
                .getResultList();
    }
}
