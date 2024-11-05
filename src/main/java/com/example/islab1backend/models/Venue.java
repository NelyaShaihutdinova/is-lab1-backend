package com.example.islab1backend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "venue")
@Getter
@Setter
public class Venue extends IdEntity{
    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @Min(value = 0)
    private Long capacity;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "venue_type"
    )
    private VenueType venueType;
}
