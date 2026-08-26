package com.crimsonlogic.arilinemanangmentsystem.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * Data Transfer Object for aircraft dto.
 * Used to transfer data between the client and the server.
 */
public class AircraftDTO {

    /**
     * The aircraft id.
     */
    @NotBlank(message = "Aircraft ID is required")
    @Size(max = 20, message = "Aircraft ID must not exceed 20 characters")
    private String aircraftId;

    /**
     * The model.
     */
    @NotBlank(message = "Aircraft model is required")
    @Size(max = 100, message = "Aircraft model must not exceed 100 characters")
    private String model;

    /**
     * The capacity.
     */
    @Min(value = 1, message = "Capacity must be greater than 0")
    private int capacity;

    public AircraftDTO() {
    }

    public AircraftDTO(String aircraftId, String model, int capacity) {
        this.aircraftId = aircraftId;
        this.model = model;
        this.capacity = capacity;
    }

    /**
     * Retrieves the aircraft id.
     * @return String the result of the operation
     */
    public String getAircraftId() {
        return aircraftId;
    }

    /**
     * Sets the aircraft id.
     * @param aircraftId the aircraft id
     */
    public void setAircraftId(String aircraftId) {
        this.aircraftId = aircraftId;
    }

    /**
     * Retrieves the model.
     * @return String the result of the operation
     */
    public String getModel() {
        return model;
    }

    /**
     * Sets the model.
     * @param model the model
     */
    public void setModel(String model) {
        this.model = model;
    }

    /**
     * Retrieves the capacity.
     * @return int the result of the operation
     */
    public int getCapacity() {
        return capacity;
    }

    /**
     * Sets the capacity.
     * @param capacity the capacity
     */
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}