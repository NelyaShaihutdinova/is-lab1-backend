package com.example.islab1backend.dto.responses;

import com.example.islab1backend.models.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenWithRoleResponse {
    private Role role;
    private String token;
}
