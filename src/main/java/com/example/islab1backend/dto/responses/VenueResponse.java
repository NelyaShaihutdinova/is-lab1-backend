package com.example.islab1backend.dto.responses;

import com.example.islab1backend.models.VenueType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VenueResponse {
    private Long id;
    private String name;
    private Long capacity;
    private VenueType type;
}
