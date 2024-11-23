package com.example.islab1backend.dto.requests;

import com.example.islab1backend.models.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketRequest {
    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    private Long coordinates;

    @NotNull
    private Long person;

    @NotNull
    private Long event;

    @NotNull
    @Min(value = 0)
    private int price;

    private TicketType ticketType;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    private Long discount;

    @Min(value = 0)
    private float number;

    @NotEmpty
    private String comment;

    private boolean refundable;

    @NotNull
    private Long venue;
}
