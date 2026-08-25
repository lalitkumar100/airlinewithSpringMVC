package com.crimsonlogic.arilinemanangmentsystem.service.impl;

import com.crimsonlogic.arilinemanangmentsystem.dto.UpdateFlightScheduleRequest;
import com.crimsonlogic.arilinemanangmentsystem.dto.UpdateFlightStatusRequest;
import com.crimsonlogic.arilinemanangmentsystem.enumrator.FlightStatus;
import com.crimsonlogic.arilinemanangmentsystem.exception.NullValueException;
import com.crimsonlogic.arilinemanangmentsystem.model.Flight;
import com.crimsonlogic.arilinemanangmentsystem.service.AircraftService;
import com.crimsonlogic.arilinemanangmentsystem.service.FlightService;
import com.crimsonlogic.arilinemanangmentsystem.service.TicketService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FlightOrchestratorServiceImplTest {

    @Mock
    private FlightService flightService;
    
    @Mock
    private TicketService ticketService;
    
    @Mock
    private AircraftService aircraftService;

    @InjectMocks
    private FlightOrchestratorServiceImpl orchestratorService;

    @Test
    public void testUpdateFlightSchedule_InvalidTimes() {
        Flight mockFlight = new Flight();
        when(flightService.getFlightById("F1")).thenReturn(mockFlight);
        
        UpdateFlightScheduleRequest req = new UpdateFlightScheduleRequest();
        req.setDepartureTime(LocalDateTime.now().plusDays(2));
        req.setArrivalTime(LocalDateTime.now().plusDays(1)); // Invalid: arrival before departure
        
        assertThrows(NullValueException.class, () -> orchestratorService.updateFlightSchedule("F1", req));
    }

    @Test
    public void testUpdateFlightStatus_ValidTransition() {
        Flight mockFlight = new Flight();
        mockFlight.setStatus(FlightStatus.CHECK_IN_STARTED);
        when(flightService.getFlightById("F1")).thenReturn(mockFlight);
        
        UpdateFlightStatusRequest req = new UpdateFlightStatusRequest();
        req.setStatus(FlightStatus.CHECK_IN_CLOSED);
        
        when(flightService.updateStatusOnly("F1", FlightStatus.CHECK_IN_CLOSED)).thenReturn(true);
        doNothing().when(ticketService).generateTickets(mockFlight);
        
        boolean result = orchestratorService.updateFlightStatus("F1", req);
        
        assertTrue(result);
        verify(ticketService, times(1)).generateTickets(mockFlight);
        verify(flightService, times(1)).updateStatusOnly("F1", FlightStatus.CHECK_IN_CLOSED);
    }

    @Test
    public void testUpdateFlightStatus_InvalidTransition() {
        Flight mockFlight = new Flight();
        mockFlight.setStatus(FlightStatus.CHECK_IN_CLOSED); // Cannot go from CLOSED to STARTED
        when(flightService.getFlightById("F1")).thenReturn(mockFlight);
        
        UpdateFlightStatusRequest req = new UpdateFlightStatusRequest();
        req.setStatus(FlightStatus.CHECK_IN_STARTED);
        
        assertThrows(NullValueException.class, () -> orchestratorService.updateFlightStatus("F1", req));
    }
}
