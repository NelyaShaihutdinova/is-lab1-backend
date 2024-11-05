package com.example.islab1backend.dto.requests;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoordinatesRequest {
    @Max(value = 703)
    private long x;

    @NotNull
    private long y;
}
