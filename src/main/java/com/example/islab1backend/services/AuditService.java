package com.example.islab1backend.services;

import com.example.islab1backend.dao.AuditDAO;
import com.example.islab1backend.dao.UserDAO;
import com.example.islab1backend.models.AuditEntity;
import com.example.islab1backend.models.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@ApplicationScoped
public class AuditService {
    @Inject
    private AuditDAO auditDAO;
    @Inject
    private UserDAO userDAO;

    @Transactional
    public void saveAudit(String username, String action) {
        Optional<User> user = userDAO.findByUsername(username);
        Long createdBy = user.get().getId();
        AuditEntity audit = new AuditEntity();
        audit.setCreationDate(LocalDateTime.now());
        audit.setCreationBy(createdBy);
        audit.setAction(action);
        auditDAO.save(audit);
    }
}