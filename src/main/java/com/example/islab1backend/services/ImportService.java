//package com.example.islab1backend.services;
//
//import com.example.islab1backend.dao.UserDAO;
//import com.example.islab1backend.models.ImportHistory;
//import com.example.islab1backend.models.ImportStatus;
//import com.example.islab1backend.models.Ticket;
//import jakarta.enterprise.context.ApplicationScoped;
//import jakarta.inject.Inject;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//import jakarta.transaction.Transactional;
//
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Objects;
//
//@ApplicationScoped
//public class ImportService {
//    @PersistenceContext
//    private EntityManager em;
//
//    @Inject
//    private AuditService auditService;
//
//    @Inject
//    private UserDAO userDAO;
//
//    @Transactional
//    public List<ImportHistory> getHistory(String username){
//        List<ImportHistory> historyList;
//        if (Objects.equals(userDAO.findByUsername(username).get().getRole().toString(), "ADMIN")) {
//            historyList = em.createQuery("SELECT h FROM ImportHistory h", ImportHistory.class).getResultList();
//        } else {
//            historyList = em.createQuery("SELECT h FROM ImportHistory h WHERE h.username = :username", ImportHistory.class)
//                    .setParameter("username", username)
//                    .getResultList();
//        }
//        return historyList;
//    }
//
//    @Transactional
//    public ImportHistory importObjects(String username, List<Ticket> tickets) {
//        ImportHistory history = new ImportHistory();
//        history.setUsername(username);
//        history.setImportTime(LocalDateTime.now());
//        String action = "create";
//        try {
//            for (Ticket ticket : tickets) {
//                validateObject(ticket);
//                em.persist(ticket);
//                auditService.saveAudit(username, action);
//            }
//            history.setNumberOfImportedRecords(tickets.size());
//            history.setStatus(ImportStatus.SUCCESS);
//        } catch (Exception e) {
//            history.setStatus(ImportStatus.FAILED);
//            history.setNumberOfImportedRecords(0);
//            throw new IllegalArgumentException("Error during import, transaction rolled back");
//        }
//        em.persist(history);
//        return history;
//    }
//
//    private void validateObject(Ticket object) {
//
//    }
//}
