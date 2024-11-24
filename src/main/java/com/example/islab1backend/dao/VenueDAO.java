package com.example.islab1backend.dao;

import com.example.islab1backend.models.Coordinates;
import com.example.islab1backend.models.Venue;
import com.example.islab1backend.models.VenueType;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class VenueDAO {
    @PersistenceContext
    private EntityManager em;

    public void save(Venue venue){
        em.persist(venue);
    }

    public void update(Long venueId, String name, Long capacity, VenueType venueType, String username) {
        Venue venue = em.find(Venue.class, venueId);
        if (venue != null) {
            if (Objects.equals(venue.getCreationBy(), username)) {
                venue.setName(name);
                venue.setCapacity(capacity);
                venue.setVenueType(venueType);
            } else {
                throw new RuntimeException("You don't have enough rights");
            }
        } else {
            throw new RuntimeException("Venue not found");
        }

    }

    public Venue findById(Long venueId) {
        return em.find(Venue.class, venueId);
    }

    public void delete(Long venueId, String username) {
        Venue venue = findById(venueId);
        if (Objects.equals(venue.getCreationBy(), username)) {
            em.remove(venue);
        } else {
            throw new RuntimeException("You don't have enough rights");
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
