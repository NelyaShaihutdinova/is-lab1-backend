package com.example.islab1backend.dao;

import com.example.islab1backend.models.Coordinates;
import com.example.islab1backend.models.Location;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class LocationDAO {
    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserDAO userDAO;

    public void save(Location location) {
        em.persist(location);
    }

    public void update(Long locationId, int x, long y, long z, String username) {
        Location location = em.find(Location.class, locationId);
        if (location != null) {
            if (Objects.equals(location.getCreationBy(), username) || Objects.equals(userDAO.findByUsername(username).get().getRole().toString(), "ADMIN")) {
                location.setX(x);
                location.setY(y);
                location.setZ(z);
            } else {
                throw new IllegalArgumentException("You don't have enough rights");
            }
        } else {
            throw new IllegalArgumentException("Location with this id not found");
        }
    }

    public Location findById(Long locationId) {
        return em.find(Location.class, locationId);
    }

    public void delete(Long locationId, String username, Long replaceId) {
        Location location = findById(locationId);
        if (location != null) {
            Location replace = em.find(Location.class, replaceId);
            if (replace != null) {
                if (locationId != replaceId) {
                    if (Objects.equals(location.getCreationBy(), username) || Objects.equals(userDAO.findByUsername(username).get().getRole().toString(), "ADMIN")) {
                        em.createQuery("UPDATE Person p SET p.location.id = :replaceId WHERE p.location.id = :locationId")
                                .setParameter("replaceId", replaceId).setParameter("locationId", locationId)
                                .executeUpdate();
                        em.remove(location);
                    } else {
                        throw new IllegalArgumentException("You don't have enough rights");
                    }
                } else {
                    throw new IllegalArgumentException("You delete this location. Change location id to replace");
                }
            } else {
                throw new IllegalArgumentException("Location to replace with this id not found");
            }
        } else {
            throw new IllegalArgumentException("Location with this id not found");
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
