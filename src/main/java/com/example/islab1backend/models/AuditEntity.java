package com.example.islab1backend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "audit")
@Getter
@Setter
public class AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(
            name = "creation_date",
            nullable = false,
            updatable = false
    )
    private LocalDateTime creationDate;

    @Column(
            name = "creation_by",
            nullable = false,
            updatable = false
    )
    private Long creationBy;

    @Column(
            nullable = false,
            updatable = false
    )
    private String action;
}
