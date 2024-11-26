package com.example.islab1backend.controllers;

import com.example.islab1backend.dto.responses.TokenResponse;
import com.example.islab1backend.dto.responses.TokenWithRoleResponse;
import com.example.islab1backend.models.User;
import com.example.islab1backend.services.AuthService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@RequestScoped
@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthController {
    @Inject
    private AuthService authService;

    @POST
    @Path("/login")
    public Response login(User user) {
        TokenWithRoleResponse tokenResponse = authService.getJWTToken(user.getUsername(), user.getPassword());
        return tokenResponse == null ?
                Response.status(Response.Status.UNAUTHORIZED).entity("Invalid username or password").build()
                : Response.ok().entity(tokenResponse).build();
    }

    @POST
    @Path("/register")
    public Response register(User user) {
        try {
            String jwtToken = authService.registerUser(user.getUsername(), user.getPassword(), user.getRole());
            return Response.ok().entity(new TokenResponse(jwtToken)).build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid username or password").build();
        }
    }

    @POST
    @Path("admin/approve/{id}")
    public Response approve(@PathParam("id") Long adminRequestId) {
        try {
            authService.approveAdminRequest(adminRequestId);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid admin request id").build();
        }
    }

    @GET
    @Path("/admin/requests")
    public Response getAdminRequests() {
        return Response.ok(authService.getPendingAdminRequests()).build();
    }
}