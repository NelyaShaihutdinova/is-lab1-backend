package com.example.islab1backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "import_history")
@Getter
@Setter
public class ImportHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private LocalDateTime importTime;

    @Column(nullable = false)
    private int numberOfImportedRecords;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ImportStatus status;
}
