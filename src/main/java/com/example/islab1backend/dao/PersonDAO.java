package com.example.islab1backend.dao;

import com.example.islab1backend.models.Location;
import com.example.islab1backend.models.Person;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@ApplicationScoped
public class PersonDAO {
    @PersistenceContext
    private EntityManager em;

    public void save(Person person){
        em.persist(person);
    }
}
