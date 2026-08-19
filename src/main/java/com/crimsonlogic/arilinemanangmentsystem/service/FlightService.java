package com.crimsonlogic.arilinemanangmentsystem.service;

import com.crimsonlogic.arilinemanangmentsystem.model.Booking;
import com.crimsonlogic.arilinemanangmentsystem.model.Flight;
import com.crimsonlogic.arilinemanangmentsystem.enumrator.FlightStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import com.crimsonlogic.arilinemanangmentsystem.enumrator.SeatClass;
import com.crimsonlogic.arilinemanangmentsystem.model.RevenueReport;

public interface FlightService {
    List<Flight> getAllFlights();
    Flight getFlightById(String flightId);
    boolean updateFlightTime(String flightId, LocalDateTime departureTime, LocalDateTime arrivalTime);
    boolean updateFlightStatus(String flightId, FlightStatus status);
    public Flight addNewFlight(Flight flight);

    List<Flight> searchFlights(String sourceAirport, String destinationAirport, LocalDate departureDate);

    int getAvailableSeats(String flightId, SeatClass seatClass);
    double calculateFare(String flightId, SeatClass seatClass);
    List<Booking> getFlightBookings(String flightId);
    RevenueReport getFlightRevenueReport(String flightId);



}