package com.example.islab1backend.dao;

import com.example.islab1backend.models.Color;
import com.example.islab1backend.models.Coordinates;
import com.example.islab1backend.models.Location;
import com.example.islab1backend.models.Person;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class PersonDAO {
    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserDAO userDAO;

    public void save(Person person) {
        em.persist(person);
    }

    public void update(Long personId, Color eyeColor, Color hairColor, Location location, long weight, String username) {
        Person person = em.find(Person.class, personId);
        if (person != null) {
            if (Objects.equals(person.getCreationBy(), username) || Objects.equals(userDAO.findByUsername(username).get().getRole().toString(), "ADMIN")) {
                person.setEyeColor(eyeColor);
                person.setHairColor(hairColor);
                person.setLocation(location);
                person.setWeight(weight);
            } else {
                throw new IllegalArgumentException("You don't have enough rights");
            }
        } else {
            throw new IllegalArgumentException("Person with this id not found");
        }
    }

    public Person findById(Long personId) {
        return em.find(Person.class, personId);
    }

    public void delete(Long personId, String username) {
        Person person = findById(personId);
        if (person != null) {
            if (Objects.equals(person.getCreationBy(), username) || Objects.equals(userDAO.findByUsername(username).get().getRole().toString(), "ADMIN")) {
                em.remove(person);
            } else {
                throw new IllegalArgumentException("You don't have enough rights");
            }
        } else {
            throw new IllegalArgumentException("Person with this id not found");
        }
    }

    public List<Person> getPaginatedPerson(int pageNumber, int pageSize) {
        int offset = (pageNumber - 1) * pageSize;
        return em.createQuery("SELECT c FROM Person c", Person.class)
                .setFirstResult(offset)
                .setMaxResults(pageSize)
                .getResultList();
    }
}
