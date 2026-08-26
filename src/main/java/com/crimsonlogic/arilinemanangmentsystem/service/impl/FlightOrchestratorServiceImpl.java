package com.crimsonlogic.arilinemanangmentsystem.service.impl;

import com.crimsonlogic.arilinemanangmentsystem.dto.UpdateFlightScheduleRequest;
import com.crimsonlogic.arilinemanangmentsystem.dto.UpdateFlightStatusRequest;
import com.crimsonlogic.arilinemanangmentsystem.enumrator.FlightStatus;
import com.crimsonlogic.arilinemanangmentsystem.exception.NullValueException;
import com.crimsonlogic.arilinemanangmentsystem.model.Flight;
import com.crimsonlogic.arilinemanangmentsystem.service.FlightOrchestratorService;
import com.crimsonlogic.arilinemanangmentsystem.service.FlightService;
import com.crimsonlogic.arilinemanangmentsystem.service.TicketService;
import com.crimsonlogic.arilinemanangmentsystem.service.AircraftService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service responsible for flight orchestrator service impl business logic.
 * Encapsulates core application rules and data manipulation.
 */
@Service
public class FlightOrchestratorServiceImpl implements FlightOrchestratorService {

    /**
     * The flight service.
     */
    private final FlightService flightService;
    private final TicketService ticketService;
    private final AircraftService aircraftService;

    public FlightOrchestratorServiceImpl(FlightService flightService, TicketService ticketService, AircraftService aircraftService) {
        this.flightService = flightService;
        this.ticketService = ticketService;
        this.aircraftService = aircraftService;
    }

    /**
     * Updates flight schedule.
     * @param flightId the flight id
     * @param request the request
     * @return boolean the result of the operation
     */
    @Override
    @Transactional
    public boolean updateFlightSchedule(String flightId, UpdateFlightScheduleRequest request) {
        Flight flight = flightService.getFlightById(flightId);
        
        // Validation logic
        if (request.getDepartureTime().isAfter(request.getArrivalTime())) {
             throw new NullValueException("Departure time cannot be after arrival time");
        }
        
        // Verify aircraft exists
        aircraftService.findById(request.getAircraftId());

        // Auto delay check
        if (request.getDepartureTime().isAfter(flight.getDepartureDateTime())) {
            flightService.updateStatusOnly(flightId, FlightStatus.DELAYED);
        }

        return flightService.updateScheduleOnly(flightId, request);
    }

    /**
     * Updates flight status.
     * @param flightId the flight id
     * @param request the request
     * @return boolean the result of the operation
     */
    @Override
    @Transactional
    public boolean updateFlightStatus(String flightId, UpdateFlightStatusRequest request) {
        Flight flight = flightService.getFlightById(flightId);
        
        FlightStatus newStatus = request.getStatus();
        FlightStatus currentStatus = flight.getStatus();

        // Business Logic for status transitions
        if (newStatus == FlightStatus.DELAYED || newStatus == FlightStatus.CANCELLED) {
            // Allowed at any time
        } else if (currentStatus == FlightStatus.SCHEDULED && newStatus == FlightStatus.CHECK_IN_STARTED) {
            // Allowed
        } else if (currentStatus == FlightStatus.CHECK_IN_STARTED && newStatus == FlightStatus.CHECK_IN_CLOSED) {
            // Allowed
            ticketService.generateTickets(flight);
        } else if (currentStatus != newStatus) {
            throw new NullValueException("Invalid status transition from " + currentStatus + " to " + newStatus);
        }

        return flightService.updateStatusOnly(flightId, newStatus);
    }
}
