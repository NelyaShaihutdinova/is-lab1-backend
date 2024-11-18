package com.example.islab1backend.services;

import com.example.islab1backend.dao.LocationDAO;
import com.example.islab1backend.dao.PersonDAO;
import com.example.islab1backend.models.Location;
import com.example.islab1backend.models.Person;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class PersonService {
    @Inject
    private PersonDAO personDAO;

   public void createPerson(Person person) {
       personDAO.save(person);
   }

}
