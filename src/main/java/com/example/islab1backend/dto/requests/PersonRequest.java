package com.example.islab1backend.dto.requests;

import com.example.islab1backend.models.Color;
import com.example.islab1backend.models.Location;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonRequest {
    @NotNull
    private Color eyeColor;

    @NotNull
    private Color hairColor;

    @NotNull
    private Location location;

    @Min(value = 0)
    private long weight;
}
