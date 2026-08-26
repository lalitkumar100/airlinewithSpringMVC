package com.crimsonlogic.arilinemanangmentsystem.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for add flight request.
 * Used to transfer data between the client and the server.
 */
public class AddFlightRequest {

    /**
     * The source airport code.
     */
    @NotBlank(message = "Source airport code is required")
    private String sourceAirportCode;

    /**
     * The destination airport code.
     */
    @NotBlank(message = "Destination airport code is required")
    private String destinationAirportCode;

    /**
     * The aircraft id.
     */
    @NotBlank(message = "Aircraft ID is required")
    private String aircraftId;

    /**
     * The departure date time.
     */
    @NotNull(message = "Departure date and time is required")
    private LocalDateTime departureDateTime;

    /**
     * The arrival date time.
     */
    @NotNull(message = "Arrival date and time is required")
    private LocalDateTime arrivalDateTime;

    /**
     * The base fare.
     */
    @NotNull(message = "Base fare is required")
    @Positive(message = "Base fare must be greater than zero")
    private Double baseFare;

    // =========================================================
    // GETTERS AND SETTERS
    // =========================================================

    /**
     * Retrieves the source airport code.
     * @return String the result of the operation
     */
    public String getSourceAirportCode() {
        return sourceAirportCode;
    }

    /**
     * Sets the source airport code.
     * @param sourceAirportCode the source airport code
     */
    public void setSourceAirportCode(String sourceAirportCode) {
        this.sourceAirportCode = sourceAirportCode;
    }

    /**
     * Retrieves the destination airport code.
     * @return String the result of the operation
     */
    public String getDestinationAirportCode() {
        return destinationAirportCode;
    }

    /**
     * Sets the destination airport code.
     * @param destinationAirportCode the destination airport code
     */
    public void setDestinationAirportCode(String destinationAirportCode) {
        this.destinationAirportCode = destinationAirportCode;
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
     * Retrieves the departure date time.
     * @return LocalDateTime the result of the operation
     */
    public LocalDateTime getDepartureDateTime() {
        return departureDateTime;
    }

    /**
     * Sets the departure date time.
     * @param departureDateTime the departure date time
     */
    public void setDepartureDateTime(LocalDateTime departureDateTime) {
        this.departureDateTime = departureDateTime;
    }

    /**
     * Retrieves the arrival date time.
     * @return LocalDateTime the result of the operation
     */
    public LocalDateTime getArrivalDateTime() {
        return arrivalDateTime;
    }

    /**
     * Sets the arrival date time.
     * @param arrivalDateTime the arrival date time
     */
    public void setArrivalDateTime(LocalDateTime arrivalDateTime) {
        this.arrivalDateTime = arrivalDateTime;
    }

    /**
     * Retrieves the base fare.
     * @return Double the result of the operation
     */
    public Double getBaseFare() {
        return baseFare;
    }

    /**
     * Sets the base fare.
     * @param baseFare the base fare
     */
    public void setBaseFare(Double baseFare) {
        this.baseFare = baseFare;
    }
}