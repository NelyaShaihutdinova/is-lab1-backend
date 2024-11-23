package com.example.islab1backend.dto;

import com.example.islab1backend.dto.requests.PersonRequest;
import com.example.islab1backend.dto.requests.TicketRequest;
import com.example.islab1backend.models.*;
import com.example.islab1backend.services.*;

public class DTOParser {
    public Person parsePersonWithLocation(PersonRequest personRequest, LocationService locationService, String username) {
        Location location = locationService.getLocation(personRequest.getLocation());
        Person person = new Person();
        person.setEyeColor(personRequest.getEyeColor());
        person.setHairColor(personRequest.getHairColor());
        person.setLocation(location);
        person.setCreationBy(username);
        person.setWeight(personRequest.getWeight());
        return person;
    }

    public Ticket parseTicket(TicketRequest ticketRequest, VenueService venueService, PersonService personService, CoordinatesService coordinatesService, EventService eventService, String username) {
        Ticket ticket = new Ticket();
        Coordinates coordinates = coordinatesService.getCoordinates(ticketRequest.getCoordinates());
        Event event = eventService.getEvent(ticketRequest.getEvent());
        Venue venue = venueService.getVenue(ticketRequest.getVenue());
        Person person = personService.getPerson(ticketRequest.getPerson());
        ticket.setName(ticketRequest.getName());
        ticket.setCoordinates(coordinates);
        ticket.setPerson(person);
        ticket.setEvent(event);
        ticket.setPrice(ticketRequest.getPrice());
        ticket.setTicketType(ticketRequest.getTicketType());
        ticket.setDiscount(ticketRequest.getDiscount());
        ticket.setNumber(ticketRequest.getNumber());
        ticket.setComment(ticketRequest.getComment());
        ticket.setRefundable(ticketRequest.isRefundable());
        ticket.setVenue(venue);
        ticket.setCreationBy(username);
        return ticket;
    }
}
