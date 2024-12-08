package com.example.islab1backend.dao;

import com.example.islab1backend.models.Coordinates;
import com.example.islab1backend.models.Venue;
import com.example.islab1backend.models.VenueType;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class VenueDAO {
    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserDAO userDAO;

    public void save(Venue venue) {
        em.persist(venue);
    }

    public void update(Long venueId, String name, Long capacity, VenueType venueType, String username) {
        Venue venue = em.find(Venue.class, venueId);
        if (venue != null) {
            if (Objects.equals(venue.getCreationBy(), username) || Objects.equals(userDAO.findByUsername(username).get().getRole().toString(), "ADMIN")) {
                venue.setName(name);
                venue.setCapacity(capacity);
                venue.setVenueType(venueType);
            } else {
                throw new IllegalArgumentException("You don't have enough rights");
            }
        } else {
            throw new IllegalArgumentException("Venue with this id not found");
        }
    }

    public Venue findById(Long venueId) {
        return em.find(Venue.class, venueId);
    }

    public void delete(Long venueId, String username) {
        Venue venue = findById(venueId);
        if (venue != null) {
            if (Objects.equals(venue.getCreationBy(), username) || Objects.equals(userDAO.findByUsername(username).get().getRole().toString(), "ADMIN")) {
                em.remove(venue);
            } else {
                throw new IllegalArgumentException("You don't have enough rights");
            }
        } else {
            throw new IllegalArgumentException("Venue with this id not found");
        }
    }

    public List<Venue> getPaginatedVenue(int pageNumber, int pageSize) {
        int offset = (pageNumber - 1) * pageSize;
        return em.createQuery("SELECT c FROM Venue c", Venue.class)
                .setFirstResult(offset)
                .setMaxResults(pageSize)
                .getResultList();
    }
}
