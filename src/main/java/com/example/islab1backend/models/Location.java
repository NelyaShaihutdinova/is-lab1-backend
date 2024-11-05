package com.example.islab1backend.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="locations")
@Getter
@Setter
public class Location extends IdEntity{
    @NotNull
    private Integer x;

    @NotNull
    private Long y;

    @NotNull
    private Long z;
}
