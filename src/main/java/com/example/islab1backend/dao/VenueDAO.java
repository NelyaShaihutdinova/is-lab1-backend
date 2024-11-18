package com.example.islab1backend.dao;

import com.example.islab1backend.models.Location;
import com.example.islab1backend.models.Venue;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@ApplicationScoped
public class VenueDAO {
    @PersistenceContext
    private EntityManager em;

    public void save(Venue venue){
        em.persist(venue);
    }
}
