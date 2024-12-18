package com.example.islab1backend.services;

import com.example.islab1backend.dao.*;
import com.example.islab1backend.filters.TicketValidator;
import com.example.islab1backend.models.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ApplicationScoped
public class ImportService {
    @PersistenceContext
    private EntityManager em;

    @Inject
    private AuditService auditService;

    @Inject
    private UserDAO userDAO;

    @Inject
    private EventDAO eventDAO;

    @Inject
    private LocationDAO locationDAO;

    @Inject
    private PersonDAO personDAO;

    @Inject
    private VenueDAO venueDAO;

    @Inject
    private CoordinatesDAO coordinatesDAO;

    private final TicketValidator ticketValidator = new TicketValidator();

    @Transactional
    public List<ImportHistory> getHistory(String username, int pageNumber, int pageSize, String filterValue, String filterField, String sorted) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<ImportHistory> query = cb.createQuery(ImportHistory.class);
        Root<ImportHistory> root = query.from(ImportHistory.class);

        List<Predicate> predicates = new ArrayList<>();

        if (filterField != null && filterValue != null) {
            Predicate filterPredicate = cb.like(root.get(filterField).as(String.class), "%" + filterValue + "%");
            predicates.add(filterPredicate);
        }
        if (!(Objects.equals(userDAO.findByUsername(username).get().getRole().toString(), "ADMIN"))) {
            Predicate userPredicate = cb.equal(root.get("username"), username);
            predicates.add(userPredicate);
        }

        if (!predicates.isEmpty()) {
            query.where(cb.and(predicates.toArray(new Predicate[0])));
        }

        if (sorted != null && !sorted.isEmpty()) {
            query.orderBy(cb.asc(root.get(sorted)));
        } else {
            query.orderBy(cb.asc(root.get("id")));
        }

        TypedQuery<ImportHistory> typedQuery = em.createQuery(query);
        typedQuery.setFirstResult((pageNumber - 1) * pageSize);
        typedQuery.setMaxResults(pageSize);

        return typedQuery.getResultList();
    }

    @Transactional
    public ImportHistory importObjects(String username, List<Ticket> tickets, String fileName) {
        ImportHistory history = new ImportHistory();
        history.setFileName(fileName);
        history.setUsername(username);
        history.setImportTime(LocalDateTime.now());
        String action = "create";
        try {
            for (Ticket ticket : tickets) {
                Event event = ticket.getEvent();
                if (event.getId() != null) {
                    ticket.setEvent(eventDAO.findById(event.getId()));
                } else {
                    event.setCreationBy(username);
                    em.persist(event);
                }

                Location location = ticket.getPerson().getLocation();
                if (location.getId() != null) {
                    ticket.getPerson().setLocation(locationDAO.findById(location.getId()));
                } else {
                    location.setCreationBy(username);
                    em.persist(location);
                }

                Person person = ticket.getPerson();
                if (person.getId() != null) {
                    ticket.setPerson(personDAO.findById(person.getId()));
                } else {
                    person.setCreationBy(username);
                    em.persist(person);
                }

                Coordinates coordinates = ticket.getCoordinates();
                if (coordinates.getId() != null) {
                    ticket.setCoordinates(coordinatesDAO.findById(coordinates.getId()));
                } else {
                    coordinates.setCreationBy(username);
                    em.persist(coordinates);
                }

                Venue venue = ticket.getVenue();
                if (venue.getId() != null) {
                    ticket.setVenue(venueDAO.findById(venue.getId()));
                } else {
                    venue.setCreationBy(username);
                    em.persist(venue);
                }

                ticket.setCreationBy(username);
                ticketValidator.validateUniqueTicketNameInEvent(em, ticket);
                ticketValidator.validateDiscountLimit(em, ticket);
                ticketValidator.validateVenueCapacity(em, ticket);
                em.persist(ticket);
                auditService.saveAudit(username, action);
            }
            history.setNumberOfImportedRecords(tickets.size());
            history.setStatus(ImportStatus.SUCCESS);
            save(history);
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
        return history;
    }

    @Transactional
    public void save(ImportHistory history) {
        em.persist(history);
    }
}
