package com.example.islab1backend.dao;

import com.example.islab1backend.models.Coordinates;
import com.example.islab1backend.models.Coordinates;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class CoordinatesDAO {
    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserDAO userDAO;

    public void save(Coordinates coordinates) {
        em.persist(coordinates);
    }

    public void update(Long coordinatesId, long x, long y, String username) {
        Coordinates coordinates = em.find(Coordinates.class, coordinatesId);
        if (coordinates != null) {
            if (Objects.equals(coordinates.getCreationBy(), username) || Objects.equals(userDAO.findByUsername(username).get().getRole().toString(), "ADMIN")) {
                coordinates.setX(x);
                coordinates.setY(y);
            } else {
                throw new IllegalArgumentException("You don't have enough rights");
            }
        } else {
            throw new IllegalArgumentException("Coordinates with this id not found");
        }

    }

    public Coordinates findById(Long coordinatesId) {
        return em.find(Coordinates.class, coordinatesId);
    }

    public void delete(Long coordinatesId, String username, Long replaceId) {
        Coordinates coordinates = findById(coordinatesId);
        if (coordinates != null) {
            Coordinates replace = em.find(Coordinates.class, replaceId);
            if (replace != null) {
                if (!Objects.equals(coordinatesId, replaceId)) {
                    if (Objects.equals(coordinates.getCreationBy(), username) || Objects.equals(userDAO.findByUsername(username).get().getRole().toString(), "ADMIN")) {
                        em.createQuery("UPDATE Ticket t SET t.coordinates.id = :replaceId WHERE t.coordinates.id = :coordinatesId")
                                .setParameter("replaceId", replaceId).setParameter("coordinatesId", coordinatesId)
                                .executeUpdate();
                        em.remove(coordinates);
                    } else {
                        throw new IllegalArgumentException("You don't have enough rights");
                    }
                } else {
                    throw new IllegalArgumentException("Change coordinates id to replace");
                }
            } else {
                throw new IllegalArgumentException("Coordinates to replace with this id not found");
            }
        } else {
            throw new IllegalArgumentException("Coordinates with this id not found");
        }
    }


    public List<Coordinates> getPaginatedCoordinates(int pageNumber, int pageSize) {
        int offset = (pageNumber - 1) * pageSize;
        return em.createQuery("SELECT c FROM Coordinates c", Coordinates.class)
                .setFirstResult(offset)
                .setMaxResults(pageSize)
                .getResultList();
    }
}
