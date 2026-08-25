package com.crimsonlogic.arilinemanangmentsystem.service;

import com.crimsonlogic.arilinemanangmentsystem.dto.AddFlightRequest;
import com.crimsonlogic.arilinemanangmentsystem.dto.FlightDTO;
import com.crimsonlogic.arilinemanangmentsystem.dto.UpdateFlightStatusRequest;
import com.crimsonlogic.arilinemanangmentsystem.dto.UpdateFlightScheduleRequest;
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
    boolean updateFlightSchedule(String flightId, UpdateFlightScheduleRequest request);
    boolean updateFlightStatus(String flightId, UpdateFlightStatusRequest request);
    boolean updateStatusOnly(String flightId, FlightStatus status);
    boolean updateScheduleOnly(String flightId, UpdateFlightScheduleRequest request);
    public Flight addNewFlight(AddFlightRequest addnewFlightRequest);

    List<Flight> searchFlights(String sourceAirport, String destinationAirport, LocalDate departureDate);

    double calculateFare(String flightId, SeatClass seatClass);

    List<FlightDTO> getAllFlightsDTO();

    FlightDTO getFlightByIdDTO(String flightId);

    public List<FlightDTO> searchFlightsDTO(String sourceAirport, String destinationAirport, LocalDate departureDate);




}