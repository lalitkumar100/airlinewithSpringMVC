package com.crimsonlogic.arilinemanangmentsystem.dto;

import com.crimsonlogic.arilinemanangmentsystem.enumrator.BookingStatus;
import com.crimsonlogic.arilinemanangmentsystem.enumrator.SeatClass;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Transfer Object for booking dto.
 * Used to transfer data between the client and the server.
 */
public class BookingDTO {

    private String bookingId;

    private List<PassengerDTO> passengers = new ArrayList<>();

    /**
     * The flight booked.
     */
    @NotNull(message = "Flight is required")
    private FlightDTO flightBooked;

    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd'T'HH:mm:ss"
    )
    /**
     * The booking date time.
     */
    private LocalDateTime bookingDateTime;

    /**
     * The booking status.
     */
    @NotNull(message = "Booking status is required")
    private BookingStatus bookingStatus;



    /**
     * The seat class.
     */
    @NotNull(message = "Seat class is required")
    private SeatClass seatClass;

    /**
     * The user id.
     */
    private String userId;

    /**
     * The amount.
     */
    @PositiveOrZero(message = "Amount cannot be negative")
    private double amount;

    /**
     * The payment id.
     */
    private String paymentId;

    public BookingDTO() {
    }

    public BookingDTO(
            String bookingId,
            List<PassengerDTO> passengers,
            FlightDTO flightBooked,
            LocalDateTime bookingDateTime,
            BookingStatus bookingStatus,
            SeatClass seatClass,
            String userId,
            double amount,
            String paymentId
    ) {
        this.bookingId = bookingId;
        this.passengers = passengers;
        this.flightBooked = flightBooked;
        this.bookingDateTime = bookingDateTime;
        this.bookingStatus = bookingStatus;
        this.seatClass = seatClass;
        this.userId = userId;
        this.amount = amount;
        this.paymentId = paymentId;
    }

    /**
     * Retrieves the booking id.
     * @return String the result of the operation
     */
    public String getBookingId() {
        return bookingId;
    }

    /**
     * Sets the booking id.
     * @param bookingId the booking id
     */
    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    /**
     * Retrieves the passengers.
     * @return List<PassengerDTO> the result of the operation
     */
    public List<PassengerDTO> getPassengers() {
        return passengers;
    }

    /**
     * Sets the passengers.
     * @param passengers the passengers
     */
    public void setPassengers(List<PassengerDTO> passengers) {
        this.passengers = passengers;
    }

    /**
     * Retrieves the flight booked.
     * @return FlightDTO the result of the operation
     */
    public FlightDTO getFlightBooked() {
        return flightBooked;
    }

    /**
     * Sets the flight booked.
     * @param flightBooked the flight booked
     */
    public void setFlightBooked(FlightDTO flightBooked) {
        this.flightBooked = flightBooked;
    }

    /**
     * Retrieves the booking date time.
     * @return LocalDateTime the result of the operation
     */
    public LocalDateTime getBookingDateTime() {
        return bookingDateTime;
    }

    /**
     * Sets the booking date time.
     * @param bookingDateTime the booking date time
     */
    public void setBookingDateTime(LocalDateTime bookingDateTime) {
        this.bookingDateTime = bookingDateTime;
    }

    /**
     * Retrieves the booking status.
     * @return BookingStatus the result of the operation
     */
    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    /**
     * Sets the booking status.
     * @param bookingStatus the booking status
     */
    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
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
     * Retrieves the amount.
     * @return double the result of the operation
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Sets the amount.
     * @param amount the amount
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Retrieves the user id.
     * @return String the result of the operation
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Sets the user id.
     * @param userId the user id
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Retrieves the payment id.
     * @return String the result of the operation
     */
    public String getPaymentId() {
        return paymentId;
    }

    /**
     * Sets the payment id.
     * @param paymentId the payment id
     */
    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }
}