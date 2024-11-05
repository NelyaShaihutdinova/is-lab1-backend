package com.example.islab1backend.dto.responses;

import com.example.islab1backend.models.TicketType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketResponse {
    private Long id;
    private String name;
    private CoordinatesResponse coordinates;
    private PersonResponse person;
    private EventResponse event;
    private int price;
    private TicketType type;
    private Long discount;
    private float number;
    private String comment;
    private boolean refundable;
    private VenueResponse venue;
}
