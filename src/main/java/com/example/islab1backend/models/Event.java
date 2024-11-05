package com.example.islab1backend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "events")
@Getter
@Setter
public class Event extends IdEntity{
    @NotNull
    @NotEmpty
    private String name;

    @Min(value = 0)
    private int ticketsCount;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "event_type"
    )
    private EventType eventType;
}
