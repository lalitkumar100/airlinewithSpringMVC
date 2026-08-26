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

/**
 * Service responsible for flight service business logic.
 * Encapsulates core application rules and data manipulation.
 */
public interface FlightService {
    List<Flight> getAllFlights();
    Flight getFlightById(String flightId);
    /**
     * Updates flight schedule.
     * @param flightId the flight id
     * @param request the request
     * @return boolean the result of the operation
     */
    boolean updateFlightSchedule(String flightId, UpdateFlightScheduleRequest request);
    boolean updateFlightStatus(String flightId, UpdateFlightStatusRequest request);
    boolean updateStatusOnly(String flightId, FlightStatus status);
    /**
     * Updates schedule only.
     * @param flightId the flight id
     * @param request the request
     * @return boolean the result of the operation
     */
    boolean updateScheduleOnly(String flightId, UpdateFlightScheduleRequest request);
    public FlightDTO addNewFlight(AddFlightRequest addnewFlightRequest);

    /**
     * Executes the search flights operation.
     * @param sourceAirport the source airport
     * @param destinationAirport the destination airport
     * @param departureDate the departure date
     * @return List<Flight> the result of the operation
     */
    List<Flight> searchFlights(String sourceAirport, String destinationAirport, LocalDate departureDate);

    double calculateFare(String flightId, SeatClass seatClass);

    /**
     * Retrieves the all flights dto.
     * @return List<FlightDTO> the result of the operation
     */
    List<FlightDTO> getAllFlightsDTO();

    FlightDTO getFlightByIdDTO(String flightId);

    /**
     * Executes the search flights dto operation.
     * @param sourceAirport the source airport
     * @param destinationAirport the destination airport
     * @param departureDate the departure date
     * @return List<FlightDTO> the result of the operation
     */
    public List<FlightDTO> searchFlightsDTO(String sourceAirport, String destinationAirport, LocalDate departureDate);




}