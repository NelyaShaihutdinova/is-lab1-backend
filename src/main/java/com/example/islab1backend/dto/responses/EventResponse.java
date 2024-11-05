package com.example.islab1backend.dto.responses;

import com.example.islab1backend.models.EventType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventResponse {
    private Long id;
    private String name;
    private int ticketsCount;
    private EventType eventType;
}
