package com.example.islab1backend.dao;

import com.example.islab1backend.models.AuditEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@ApplicationScoped
public class AuditDAO {
    @PersistenceContext
    private EntityManager em;

    public void save(AuditEntity audit) {
        em.persist(audit);
    }
}