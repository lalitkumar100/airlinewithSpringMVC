package com.crimsonlogic.arilinemanangmentsystem.dto;

import com.crimsonlogic.arilinemanangmentsystem.enumrator.SeatClass;
import com.crimsonlogic.arilinemanangmentsystem.model.Passenger;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Data Transfer Object for booking request.
 * Used to transfer data between the client and the server.
 */
public class BookingRequest {

    // =========================================================
    // FLIGHT
    // =========================================================

    /**
     * The flight id.
     */
    @NotBlank(message = "Flight ID is required")
    private String flightId;


    // =========================================================
    // SEAT CLASS
    // =========================================================

    /**
     * The seat class.
     */
    @NotNull(message = "Seat class is required")
    private SeatClass seatClass;


    // =========================================================
    // PASSENGERS
    // =========================================================

    @NotNull(message = "Passengers are required")
    @Size(
            min = 1,
            max = 9,
            message = "Booking must contain between 1 and 9 passengers"
    )
    /**
     * The passengers.
     */
    @Valid
    private List<Passenger> passengers;


    // =========================================================
    // WALLET PASSWORD
    // =========================================================

    /**
     * The password.
     */
    @NotBlank(message = "Password is required")
    private String password;


    // =========================================================
    // CONSTRUCTORS
    // =========================================================

    public BookingRequest() {
    }


    // =========================================================
    // GETTERS AND SETTERS
    // =========================================================

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
     * Retrieves the seat class.
     * @return SeatClass the result of the operation
     */
    public SeatClass getSeatClass() {
        return seatClass;
    }

    /**
     * Sets the seat class.
     * @param seatClass the seat class
     */
    public void setSeatClass(SeatClass seatClass) {
        this.seatClass = seatClass;
    }

    /**
     * Retrieves the passengers.
     * @return List<Passenger> the result of the operation
     */
    public List<Passenger> getPassengers() {
        return passengers;
    }

    /**
     * Sets the passengers.
     * @param passengers the passengers
     */
    public void setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
    }

    /**
     * Retrieves the password.
     * @return String the result of the operation
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password.
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}