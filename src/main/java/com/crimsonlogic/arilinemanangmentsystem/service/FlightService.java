package com.crimsonlogic.arilinemanangmentsystem.service;

import com.crimsonlogic.arilinemanangmentsystem.model.Flight;
import com.crimsonlogic.arilinemanangmentsystem.enumrator.FlightStatus;

import java.time.LocalDateTime;
import java.util.List;

public interface FlightService {
    List<Flight> getAllFlights();
    Flight getFlightById(String flightId);
    boolean updateFlightTime(String flightId, LocalDateTime departureTime, LocalDateTime arrivalTime);
    boolean updateFlightStatus(String flightId, FlightStatus status);
    public Flight addNewFlight(Flight flight);
}