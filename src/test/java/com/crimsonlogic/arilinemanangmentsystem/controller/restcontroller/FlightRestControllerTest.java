package com.crimsonlogic.arilinemanangmentsystem.controller.restcontroller;

import com.crimsonlogic.arilinemanangmentsystem.dto.ApiResponse;
import com.crimsonlogic.arilinemanangmentsystem.dto.FlightDTO;
import com.crimsonlogic.arilinemanangmentsystem.service.FlightOrchestratorService;
import com.crimsonlogic.arilinemanangmentsystem.service.FlightService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FlightRestControllerTest {

    @Mock
    private FlightService flightService;
    
    @Mock
    private FlightOrchestratorService flightOrchestratorService;

    @InjectMocks
    private FlightRestController flightRestController;

    @Test
    public void testGetAllFlights_Success() {
        FlightDTO f1 = new FlightDTO();
        FlightDTO f2 = new FlightDTO();
        
        when(flightService.getAllFlightsDTO()).thenReturn(Arrays.asList(f1, f2));

        ResponseEntity<ApiResponse<List<FlightDTO>>> response = flightRestController.getAllFlights();
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().getResponseData().size());
        verify(flightService, times(1)).getAllFlightsDTO();
    }

    @Test
    public void testGetFlightById_Success() {
        FlightDTO f1 = new FlightDTO();
        
        when(flightService.getFlightByIdDTO("F1")).thenReturn(f1);

        ResponseEntity<ApiResponse<FlightDTO>> response = flightRestController.getFlightById("F1");
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(flightService, times(1)).getFlightByIdDTO("F1");
    }

    @Test
    public void testSearchFlights_Success() {
        FlightDTO f1 = new FlightDTO();
        LocalDate date = LocalDate.now();
        
        when(flightService.searchFlightsDTO("DEL", "BOM", date)).thenReturn(Arrays.asList(f1));

        ResponseEntity<ApiResponse<List<FlightDTO>>> response = flightRestController.searchFlights("DEL", "BOM", date);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getResponseData().size());
        verify(flightService, times(1)).searchFlightsDTO("DEL", "BOM", date);
    }
}
