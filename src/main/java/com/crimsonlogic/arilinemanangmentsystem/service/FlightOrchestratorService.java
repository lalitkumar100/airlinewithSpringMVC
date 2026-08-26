package com.crimsonlogic.arilinemanangmentsystem.service;

import com.crimsonlogic.arilinemanangmentsystem.dto.UpdateFlightScheduleRequest;
import com.crimsonlogic.arilinemanangmentsystem.dto.UpdateFlightStatusRequest;

/**
 * Service responsible for flight orchestrator service business logic.
 * Encapsulates core application rules and data manipulation.
 */
public interface FlightOrchestratorService {
    boolean updateFlightSchedule(String flightId, UpdateFlightScheduleRequest request);
    boolean updateFlightStatus(String flightId, UpdateFlightStatusRequest request);
}
