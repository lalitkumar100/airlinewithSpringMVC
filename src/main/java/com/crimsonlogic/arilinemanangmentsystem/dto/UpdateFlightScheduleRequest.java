package com.crimsonlogic.arilinemanangmentsystem.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for update flight schedule request.
 * Used to transfer data between the client and the server.
 */
public class UpdateFlightScheduleRequest {
    @NotNull(message = "Departure time cannot be null")
    private LocalDateTime departureTime;
    
    /**
     * The arrival time.
     */
    @NotNull(message = "Arrival time cannot be null")
    private LocalDateTime arrivalTime;
    
    /**
     * The aircraft id.
     */
    @NotBlank(message = "Aircraft ID cannot be blank")
    private String aircraftId;

    /**
     * Retrieves the departure time.
     * @return LocalDateTime the result of the operation
     */
    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    /**
     * Sets the departure time.
     * @param departureTime the departure time
     */
    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    /**
     * Retrieves the arrival time.
     * @return LocalDateTime the result of the operation
     */
    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    /**
     * Sets the arrival time.
     * @param arrivalTime the arrival time
     */
    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
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
}
