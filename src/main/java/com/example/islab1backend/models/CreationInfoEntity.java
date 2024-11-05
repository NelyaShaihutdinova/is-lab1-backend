package com.example.islab1backend.models;


import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(CreationInfoListener.class)
public abstract class CreationInfoEntity extends IdEntity {
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
}
