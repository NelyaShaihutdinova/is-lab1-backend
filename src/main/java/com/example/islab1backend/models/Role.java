package com.example.islab1backend.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "role")
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role extends IdEntity{
    @Column(
            name = "role_name",
            nullable = false,
            unique = true
    )
    private String roleName;
}
