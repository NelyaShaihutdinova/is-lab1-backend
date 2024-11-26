package com.example.islab1backend.dao;

import com.example.islab1backend.dto.responses.TokenWithRoleResponse;
import com.example.islab1backend.models.Role;
import com.example.islab1backend.models.User;
import com.example.islab1backend.security.JWTUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.Optional;

@ApplicationScoped
public class UserDAO {
    @PersistenceContext
    private EntityManager em;

    public void save(User user) {
        em.persist(user);
    }

    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-384");
            byte[] hashBytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hashString = new StringBuilder();
            for (byte b : hashBytes) {
                hashString.append(String.format("%02x", b));
            }
            return hashString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error while hashing password", e);
        }
    }

    public Optional<User> findByUsername(String username) {
        return em.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                .setParameter("username", username)
                .getResultStream()
                .findFirst();
    }

    public Long getCountOfAdmins() {
        return em.createQuery("SELECT COUNT(u) FROM User u WHERE u.role = :role", Long.class)
                .setParameter("role", Role.ADMIN)
                .getSingleResult();
    }

    public TokenWithRoleResponse verifyPassword(String username, String password) {
        Optional<User> user = findByUsername(username);
        if (Objects.nonNull(user) && user.map(u -> hashPassword(password)
                .equals(u.getPassword())).orElse(false)) {
            TokenWithRoleResponse tokenResponse = new TokenWithRoleResponse(user.get().getRole(), JWTUtil.generateToken(username));
            return tokenResponse;
        }
        return null;
    }
}
