package com.example.islab1backend.dto.requests;

import com.example.islab1backend.models.EventType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventRequest {
    @NotNull
    @NotEmpty
    private String name;

    @Min(value = 0)
    private int ticketsCount;

    private EventType eventType;
}
