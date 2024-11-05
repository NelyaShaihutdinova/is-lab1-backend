package com.example.islab1backend.dto.requests;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationRequest {
    @NotNull
    private Integer x;

    @NotNull
    private Long y;

    @NotNull
    private Long z;
}
