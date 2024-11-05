package com.example.islab1backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter @Setter
public class User extends IdEntity{
    @Column(
            nullable = false,
            unique = true
    )
    private String username;

    @Column(
            nullable = false
    )
    private String password;

    @ManyToOne
    @JoinColumn(
            name = "role_id",
            nullable = false
    )
    private Role role;

}
