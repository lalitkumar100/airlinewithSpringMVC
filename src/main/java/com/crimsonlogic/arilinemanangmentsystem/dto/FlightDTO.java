package com.crimsonlogic.arilinemanangmentsystem.dto;

import com.crimsonlogic.arilinemanangmentsystem.enumrator.FlightStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Data Transfer Object for flight dto.
 * Used to transfer data between the client and the server.
 */
public class FlightDTO {

    private String flightId;

    /**
     * The flight code.
     */
    private String flightCode;

    /**
     * The source.
     */
    @NotNull(message = "Source airport is required")
    private AirportDTO source;

    /**
     * The destination.
     */
    @NotNull(message = "Destination airport is required")
    private AirportDTO destination;

    @NotNull(message = "Departure date and time is required")
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd'T'HH:mm:ss"
    )
    /**
     * The departure date time.
     */
    private LocalDateTime departureDateTime;

    @NotNull(message = "Arrival date and time is required")
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd'T'HH:mm:ss"
    )
    /**
     * The arrival date time.
     */
    private LocalDateTime arrivalDateTime;

    /**
     * The aircraft.
     */
    @NotNull(message = "Aircraft is required")
    private AircraftDTO aircraft;

    @DecimalMin(
            value = "0.0",
            inclusive = false,
            message = "Base fare must be greater than 0"
    )
    /**
     * The base fare.
     */
    private double baseFare;

    /**
     * The status.
     */
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

    /**
     * Retrieves the flight id.
     * @return String the result of the operation
     */
    public String getFlightId() {
        return flightId;
    }

    /**
     * Sets the flight id.
     * @param flightId the flight id
     */
    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    /**
     * Retrieves the flight code.
     * @return String the result of the operation
     */
    public String getFlightCode() {
        return flightCode;
    }

    /**
     * Sets the flight code.
     * @param flightCode the flight code
     */
    public void setFlightCode(String flightCode) {
        this.flightCode = flightCode;
    }

    /**
     * Retrieves the source.
     * @return AirportDTO the result of the operation
     */
    public AirportDTO getSource() {
        return source;
    }

    /**
     * Sets the source.
     * @param source the source
     */
    public void setSource(AirportDTO source) {
        this.source = source;
    }

    /**
     * Retrieves the destination.
     * @return AirportDTO the result of the operation
     */
    public AirportDTO getDestination() {
        return destination;
    }

    /**
     * Sets the destination.
     * @param destination the destination
     */
    public void setDestination(AirportDTO destination) {
        this.destination = destination;
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
     * Retrieves the aircraft.
     * @return AircraftDTO the result of the operation
     */
    public AircraftDTO getAircraft() {
        return aircraft;
    }

    /**
     * Sets the aircraft.
     * @param aircraft the aircraft
     */
    public void setAircraft(AircraftDTO aircraft) {
        this.aircraft = aircraft;
    }

    /**
     * Retrieves the base fare.
     * @return double the result of the operation
     */
    public double getBaseFare() {
        return baseFare;
    }

    /**
     * Sets the base fare.
     * @param baseFare the base fare
     */
    public void setBaseFare(double baseFare) {
        this.baseFare = baseFare;
    }

    /**
     * Retrieves the status.
     * @return FlightStatus the result of the operation
     */
    public FlightStatus getStatus() {
        return status;
    }

    /**
     * Sets the status.
     * @param status the status
     */
    public void setStatus(FlightStatus status) {
        this.status = status;
    }
}