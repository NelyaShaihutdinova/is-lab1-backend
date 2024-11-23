package com.example.islab1backend.dao;

import com.example.islab1backend.models.Coordinates;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@ApplicationScoped
public class CoordinatesDAO {
    @PersistenceContext
    private EntityManager em;

    public void save(Coordinates coordinates){
        em.persist(coordinates);
    }
}
