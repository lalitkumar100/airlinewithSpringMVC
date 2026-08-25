package com.crimsonlogic.arilinemanangmentsystem.controller.restcontroller;

import com.crimsonlogic.arilinemanangmentsystem.dto.AirportDTO;
import com.crimsonlogic.arilinemanangmentsystem.dto.ApiResponse;
import com.crimsonlogic.arilinemanangmentsystem.service.AirportService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AirportRestControllerTest {

    @Mock
    private AirportService airportService;

    @InjectMocks
    private AirportRestController airportRestController;

    @Test
    public void testGetAllAirportsRest_Success() {
        AirportDTO a1 = new AirportDTO("DEL", "Delhi", "New Delhi");
        AirportDTO a2 = new AirportDTO("BOM", "Mumbai", "Mumbai");
        
        when(airportService.getAllAirportsDTO()).thenReturn(Arrays.asList(a1, a2));

        ResponseEntity<ApiResponse<List<AirportDTO>>> response = airportRestController.getAllAirportsRest();
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("SUCCESS", response.getBody().getStatus());
        assertEquals(2, response.getBody().getResponseData().size());
        
        verify(airportService, times(1)).getAllAirportsDTO();
    }

    @Test
    public void testGetAllAirportsRest_EmptyList() {
        when(airportService.getAllAirportsDTO()).thenReturn(Collections.emptyList());

        ResponseEntity<ApiResponse<List<AirportDTO>>> response = airportRestController.getAllAirportsRest();
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().getResponseData().size());
        
        verify(airportService, times(1)).getAllAirportsDTO();
    }

    @Test
    public void testGetAirportByCodeRest_Success() {
        AirportDTO airport = new AirportDTO("DEL", "Delhi", "New Delhi");
        when(airportService.getAirportByCodeDTO("DEL")).thenReturn(airport);

        ResponseEntity<ApiResponse<AirportDTO>> response = airportRestController.getAirportByCodeRest("DEL");
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("SUCCESS", response.getBody().getStatus());
        assertEquals("DEL", response.getBody().getResponseData().getAirportCode());
        
        verify(airportService, times(1)).getAirportByCodeDTO("DEL");
    }
}
