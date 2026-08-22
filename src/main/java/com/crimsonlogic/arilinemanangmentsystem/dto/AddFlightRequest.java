package com.crimsonlogic.arilinemanangmentsystem.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

public class AddFlightRequest {

    @NotBlank(message = "Source airport code is required")
    private String sourceAirportCode;

    @NotBlank(message = "Destination airport code is required")
    private String destinationAirportCode;

    @NotBlank(message = "Aircraft ID is required")
    private String aircraftId;

    @NotNull(message = "Departure date and time is required")
    private LocalDateTime departureDateTime;

    @NotNull(message = "Arrival date and time is required")
    private LocalDateTime arrivalDateTime;

    @NotNull(message = "Base fare is required")
    @Positive(message = "Base fare must be greater than zero")
    private Double baseFare;

    // =========================================================
    // GETTERS AND SETTERS
    // =========================================================

    public String getSourceAirportCode() {
        return sourceAirportCode;
    }

    public void setSourceAirportCode(String sourceAirportCode) {
        this.sourceAirportCode = sourceAirportCode;
    }

    public String getDestinationAirportCode() {
        return destinationAirportCode;
    }

    public void setDestinationAirportCode(String destinationAirportCode) {
        this.destinationAirportCode = destinationAirportCode;
    }

    public String getAircraftId() {
        return aircraftId;
    }

    public void setAircraftId(String aircraftId) {
        this.aircraftId = aircraftId;
    }

    public LocalDateTime getDepartureDateTime() {
        return departureDateTime;
    }

    public void setDepartureDateTime(LocalDateTime departureDateTime) {
        this.departureDateTime = departureDateTime;
    }

    public LocalDateTime getArrivalDateTime() {
        return arrivalDateTime;
    }

    public void setArrivalDateTime(LocalDateTime arrivalDateTime) {
        this.arrivalDateTime = arrivalDateTime;
    }

    public Double getBaseFare() {
        return baseFare;
    }

    public void setBaseFare(Double baseFare) {
        this.baseFare = baseFare;
    }
}