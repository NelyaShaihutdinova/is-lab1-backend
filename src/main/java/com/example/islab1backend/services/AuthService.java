package com.example.islab1backend.services;

import com.example.islab1backend.dao.AdminRequestDAO;
import com.example.islab1backend.dao.UserDAO;
import com.example.islab1backend.models.AdminRequest;
import com.example.islab1backend.models.AdminRequestStatus;
import com.example.islab1backend.models.Role;
import com.example.islab1backend.models.User;
import com.example.islab1backend.security.JWTUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class AuthService {
    @Inject
    private UserDAO userDAO;

    @Inject
    private AdminRequestDAO adminRequestDAO;

    @Transactional
    public String registerUser(String username, String password, Role role) {
        if (password.length() < 6) {
            return "Password must be at least 6 characters";
        }
        if (userDAO.findByUsername(username).isPresent()) {
            return "Username already exists";
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(UserDAO.hashPassword(password));
        user.setRole(role);

        if (role == Role.ADMIN && userDAO.getCountOfAdmins() > 0) {
            user.setRole(Role.USER);
            userDAO.save(user);
            AdminRequest adminRequest = new AdminRequest();
            adminRequest.setUser(user);
            adminRequest.setStatus(AdminRequestStatus.PENDING);
            adminRequestDAO.save(adminRequest);
            return "Admin request created";
        } else {
            userDAO.save(user);
            return JWTUtil.generateToken(user.getUsername());
        }
    }

    @Transactional
    public String getJWTToken(String username, String password) {
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
