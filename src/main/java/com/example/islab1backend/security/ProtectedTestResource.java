package com.example.islab1backend.security;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.SecurityContext;

import java.security.Principal;

@Path("/protected")
public class ProtectedTestResource {

    @GET
    public String getSecureData(@Context SecurityContext securityContext) {
        Principal userPrincipal = securityContext.getUserPrincipal();
        if (userPrincipal == null) {
            return "Ошибка: пользователь не аутентифицирован";
        }

        String username = userPrincipal.getName();
        return "Привет, " + username + "! Это защищённый ресурс.";
    }
}
