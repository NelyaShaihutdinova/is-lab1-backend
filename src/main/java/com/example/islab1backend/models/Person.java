package com.example.islab1backend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "person")
@Getter
@Setter
public class Person extends IdEntity {
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(
           name = "eye_color"
    )
    private Color eyeColor;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(
            name = "hair_color"
    )
    private Color hairColor;

    @ManyToOne
    @JoinColumn(
            name = "location_id",
            referencedColumnName = "id",
            nullable = false
    )
    private Location location;

    @Min(value = 0)
    private long weight;
}
