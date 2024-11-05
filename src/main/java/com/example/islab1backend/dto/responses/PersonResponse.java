package com.example.islab1backend.dto.responses;

import com.example.islab1backend.models.Color;
import com.example.islab1backend.models.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonResponse {
    private Long id;
    private Color eyeColor;
    private Color hairColor;
    private LocationResponse location;
    private long weight;
}
