package com.crimsonlogic.arilinemanangmentsystem.service;

import com.crimsonlogic.arilinemanangmentsystem.dto.UpdateFlightScheduleRequest;
import com.crimsonlogic.arilinemanangmentsystem.dto.UpdateFlightStatusRequest;

public interface FlightOrchestratorService {
    boolean updateFlightSchedule(String flightId, UpdateFlightScheduleRequest request);
    boolean updateFlightStatus(String flightId, UpdateFlightStatusRequest request);
}
