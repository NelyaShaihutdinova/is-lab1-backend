package com.example.islab1backend.services.interfaces;

import com.example.islab1backend.models.AdminRequest;
import com.example.islab1backend.models.Role;

import java.util.List;

public interface AuthServiceInterface {
    void registerUser(String username, String password, Role role);

    boolean authenticateUser(String username, String password);

    void approveAdminRequest(Long adminRequestId);

    List<AdminRequest> getPendingAdminRequests();
}
