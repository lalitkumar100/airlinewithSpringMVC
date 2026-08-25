package com.crimsonlogic.arilinemanangmentsystem.dto;

import com.crimsonlogic.arilinemanangmentsystem.enumrator.BookingStatus;
import com.crimsonlogic.arilinemanangmentsystem.enumrator.SeatClass;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BookingDTO {

    private String bookingId;

    private List<PassengerDTO> passengers = new ArrayList<>();

    @NotNull(message = "Flight is required")
    private FlightDTO flightBooked;

    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy-MM-dd'T'HH:mm:ss"
    )
    private LocalDateTime bookingDateTime;

    @NotNull(message = "Booking status is required")
    private BookingStatus bookingStatus;



    @NotNull(message = "Seat class is required")
    private SeatClass seatClass;

    private String userId;

    @PositiveOrZero(message = "Amount cannot be negative")
    private double amount;

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

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public List<PassengerDTO> getPassengers() {
        return passengers;
    }

    public void setPassengers(List<PassengerDTO> passengers) {
        this.passengers = passengers;
    }

    public FlightDTO getFlightBooked() {
        return flightBooked;
    }

    public void setFlightBooked(FlightDTO flightBooked) {
        this.flightBooked = flightBooked;
    }

    public LocalDateTime getBookingDateTime() {
        return bookingDateTime;
    }

    public void setBookingDateTime(LocalDateTime bookingDateTime) {
        this.bookingDateTime = bookingDateTime;
    }

    public BookingStatus getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(BookingStatus bookingStatus) {
        this.bookingStatus = bookingStatus;
    }



    public SeatClass getSeatClass() {
        return seatClass;
    }

    public void setSeatClass(SeatClass seatClass) {
        this.seatClass = seatClass;
    }



    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }
}