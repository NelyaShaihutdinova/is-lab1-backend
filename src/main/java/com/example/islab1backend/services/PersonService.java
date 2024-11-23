package com.example.islab1backend.services;

import com.example.islab1backend.dao.PersonDAO;
import com.example.islab1backend.models.Color;
import com.example.islab1backend.models.Location;
import com.example.islab1backend.models.Person;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class PersonService {
    @Inject
    private PersonDAO personDAO;

    @Transactional
    public void createPerson(Person person) {
        personDAO.save(person);
    }

    @Transactional
    public void updatePerson(Long personId, Person person, String username) {
        Color eyeColor = person.getEyeColor();
        Color hairColor = person.getHairColor();
        Location location = person.getLocation();
        long weight = person.getWeight();
        personDAO.update(personId, eyeColor, hairColor, location, weight, username);
    }

    @Transactional
    public Person getPerson(Long personId) {
        return personDAO.findById(personId);
    }

    @Transactional
    public void deletePerson(Long personId, String username) {
        personDAO.delete(personId, username);
    }

    @Transactional
    public List<Person> getAllPerson() {
        return personDAO.findPerson();
    }
}
