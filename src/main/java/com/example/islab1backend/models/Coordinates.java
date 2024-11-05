package com.example.islab1backend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="coordinates")
@Getter @Setter
public class Coordinates extends IdEntity{
    @Max(value = 703)
    private long x;

    @NotNull
    private long y;
}
