package com.example.islab1backend.services;

import com.example.islab1backend.dao.AdminRequestDAO;
import com.example.islab1backend.dao.UserDAO;
import com.example.islab1backend.models.AdminRequest;
import com.example.islab1backend.models.AdminRequestStatus;
import com.example.islab1backend.models.Role;
import com.example.islab1backend.models.User;
import com.example.islab1backend.services.interfaces.AuthServiceInterface;
import jakarta.enterprise.context.ApplicationScoped;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import javax.inject.Named;
import java.util.List;

@ApplicationScoped
public class AuthService implements AuthServiceInterface {
    @Inject
    private UserDAO userDAO;

    @Inject
    private AdminRequestDAO adminRequestDAO;

    @Transactional
    public void registerUser(String username, String password, Role role) {
        if (password.length() < 6) {
            System.out.println("Password must be at least 6 characters");
        }
        if (userDAO.findByUsername(username).isPresent()) {
            System.out.println("Username already exists");
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(userDAO.hashPassword(password));
        user.setRole(role);

        if (role == Role.ADMIN && userDAO.getCountOfAdmins() > 0) {
            user.setRole(Role.USER);
            userDAO.save(user);
            AdminRequest adminRequest = new AdminRequest();
            adminRequest.setUser(user);
            adminRequest.setStatus(AdminRequestStatus.PENDING);
            adminRequestDAO.save(adminRequest);
        } else {
            userDAO.save(user);
        }
    }

    @Transactional
    public boolean authenticateUser(String username, String password) {
        return userDAO.verifyPassword(username, password);
    }

    @Transactional
    public void approveAdminRequest(Long adminRequestId) {
        AdminRequest adminRequest = adminRequestDAO.findPendingAdminRequests().stream().filter(a -> a.getId().equals(adminRequestId)).findFirst().orElseThrow(() -> new IllegalArgumentException("Request not found"));
        adminRequest.setStatus(AdminRequestStatus.APPROVED);
        adminRequestDAO.update(adminRequest);
        adminRequest.getUser().setRole(Role.ADMIN);
        userDAO.save(adminRequest.getUser());
    }

    @Transactional
    public List<AdminRequest> getPendingAdminRequests() {
        return adminRequestDAO.findPendingAdminRequests();
    }
}
