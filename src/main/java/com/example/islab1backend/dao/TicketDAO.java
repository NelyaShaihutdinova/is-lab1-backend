package com.example.islab1backend.dao;

import com.example.islab1backend.models.Ticket;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@ApplicationScoped
public class TicketDAO {
    @PersistenceContext
    private EntityManager em;

    public void save(Ticket ticket){
        em.persist(ticket);
    }


}
