package com.example.islab1backend.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tickets")
@Getter
@Setter
public class Ticket extends IdEntity{
    @NotNull
    @NotEmpty
    @Column(name = "ticket_name")
    private String name;

    @ManyToOne
    @JoinColumn(
            name = "coordinates_id",
            referencedColumnName = "id",
            nullable = false
    )
    private Coordinates coordinates;

    @ManyToOne
    @JoinColumn(
            name = "person_id",
            referencedColumnName = "id",
            nullable = false
    )
    private Person person;

    @ManyToOne
    @JoinColumn(
            name = "events_id",
            referencedColumnName = "id",
            nullable = false
    )
    private Event event;

    @NotNull
    @Min(value = 0)
    @Column(name = "price")
    private int price;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "ticket_type"
    )
    private TicketType ticketType;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "discount")
    private Long discount;

    @Min(value = 0)
    @Column(name = "number")
    private float number;

    @NotEmpty
    @Column(name = "comment")
    private String comment;

    @Column(name = "refundable")
    private boolean refundable;

    @ManyToOne
    @JoinColumn(
            name = "venue_id",
            referencedColumnName = "id",
            nullable = false
    )
    private Venue venue;
}
