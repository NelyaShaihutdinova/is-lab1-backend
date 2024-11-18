package com.example.islab1backend.dao;

import com.example.islab1backend.models.Event;
import com.example.islab1backend.models.Location;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@ApplicationScoped
public class EventDAO {
    @PersistenceContext
    private EntityManager em;

    public void save(Event event) {
        em.persist(event);
    }
}
