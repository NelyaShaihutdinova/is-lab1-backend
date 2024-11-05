package com.example.islab1backend.dto.requests;

import com.example.islab1backend.models.VenueType;
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
public class VenueRequest {
    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @Min(value = 0)
    private Long capacity;

    private VenueType venueType;
}
