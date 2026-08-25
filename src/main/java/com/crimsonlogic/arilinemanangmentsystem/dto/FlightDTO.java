package com.crimsonlogic.arilinemanangmentsystem.dto;

import com.crimsonlogic.arilinemanangmentsystem.enumrator.FlightStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class FlightDTO {

    private String flightId;

    private String flightCode;

    @NotNull(message = "Source airport is required")
    private AirportDTO source;

    @NotNull(message = "Destination airport is required")
    private AirportDTO destination;

    @NotNull(message = "Departure date and time is required")
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd'T'HH:mm:ss"
    )
    private LocalDateTime departureDateTime;

    @NotNull(message = "Arrival date and time is required")
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd'T'HH:mm:ss"
    )
    private LocalDateTime arrivalDateTime;

    @NotNull(message = "Aircraft is required")
    private AircraftDTO aircraft;

    @DecimalMin(
            value = "0.0",
            inclusive = false,
            message = "Base fare must be greater than 0"
    )
    private double baseFare;

    @NotNull(message = "Flight status is required")
    private FlightStatus status;

    public FlightDTO() {
    }

    public FlightDTO(
            String flightId,
            String flightCode,
            AirportDTO source,
            AirportDTO destination,
            LocalDateTime departureDateTime,
            LocalDateTime arrivalDateTime,
            AircraftDTO aircraft,
            double baseFare,
            FlightStatus status
    ) {
        this.flightId = flightId;
        this.flightCode = flightCode;
        this.source = source;
        this.destination = destination;
        this.departureDateTime = departureDateTime;
        this.arrivalDateTime = arrivalDateTime;
        this.aircraft = aircraft;
        this.baseFare = baseFare;
        this.status = status;
    }

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public String getFlightCode() {
        return flightCode;
    }

    public void setFlightCode(String flightCode) {
        this.flightCode = flightCode;
    }

    public AirportDTO getSource() {
        return source;
    }

    public void setSource(AirportDTO source) {
        this.source = source;
    }

    public AirportDTO getDestination() {
        return destination;
    }

    public void setDestination(AirportDTO destination) {
        this.destination = destination;
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

    public AircraftDTO getAircraft() {
        return aircraft;
    }

    public void setAircraft(AircraftDTO aircraft) {
        this.aircraft = aircraft;
    }

    public double getBaseFare() {
        return baseFare;
    }

    public void setBaseFare(double baseFare) {
        this.baseFare = baseFare;
    }

    public FlightStatus getStatus() {
        return status;
    }

    public void setStatus(FlightStatus status) {
        this.status = status;
    }
}