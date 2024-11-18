package com.example.islab1backend.dao;

import com.example.islab1backend.models.AdminRequest;
import com.example.islab1backend.models.AdminRequestStatus;
import jakarta.ejb.Singleton;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@ApplicationScoped
public class AdminRequestDAO{
    @PersistenceContext
    private EntityManager em;

    public void save(AdminRequest adminRequest) {
        em.persist(adminRequest);
    }

    public List<AdminRequest> findPendingAdminRequests() {
        return em.createQuery("SELECT r FROM AdminRequest r WHERE r.status = :status", AdminRequest.class)
                .setParameter("status", AdminRequestStatus.PENDING)
                .getResultList();
    }

    public void update(AdminRequest request) {
        em.merge(request);
    }
}
