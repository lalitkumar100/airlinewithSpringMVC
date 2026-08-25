package com.crimsonlogic.arilinemanangmentsystem.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class AircraftDTO {

    @NotBlank(message = "Aircraft ID is required")
    @Size(max = 20, message = "Aircraft ID must not exceed 20 characters")
    private String aircraftId;

    @NotBlank(message = "Aircraft model is required")
    @Size(max = 100, message = "Aircraft model must not exceed 100 characters")
    private String model;

    @Min(value = 1, message = "Capacity must be greater than 0")
    private int capacity;

    public AircraftDTO() {
    }

    public AircraftDTO(String aircraftId, String model, int capacity) {
        this.aircraftId = aircraftId;
        this.model = model;
        this.capacity = capacity;
    }

    public String getAircraftId() {
        return aircraftId;
    }

    public void setAircraftId(String aircraftId) {
        this.aircraftId = aircraftId;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}