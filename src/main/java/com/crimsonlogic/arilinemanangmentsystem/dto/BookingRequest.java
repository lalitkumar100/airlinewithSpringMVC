package com.crimsonlogic.arilinemanangmentsystem.dto;

import com.crimsonlogic.arilinemanangmentsystem.enumrator.SeatClass;
import com.crimsonlogic.arilinemanangmentsystem.model.Passenger;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class BookingRequest {

    // =========================================================
    // FLIGHT
    // =========================================================

    @NotBlank(message = "Flight ID is required")
    private String flightId;


    // =========================================================
    // SEAT CLASS
    // =========================================================

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
    @Valid
    private List<Passenger> passengers;


    // =========================================================
    // WALLET PASSWORD
    // =========================================================

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

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }

    public SeatClass getSeatClass() {
        return seatClass;
    }

    public void setSeatClass(SeatClass seatClass) {
        this.seatClass = seatClass;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}