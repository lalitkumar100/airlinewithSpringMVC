package com.crimsonlogic.arilinemanangmentsystem.controller.restcontroller;

import com.crimsonlogic.arilinemanangmentsystem.dto.AircraftDTO;
import com.crimsonlogic.arilinemanangmentsystem.dto.ApiResponse;
import com.crimsonlogic.arilinemanangmentsystem.service.AircraftService;
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
public class AircraftRestControllerTest {

    @Mock
    private AircraftService aircraftService;

    @InjectMocks
    private AircraftRestController aircraftRestController;

    @Test
    public void testGetAllAircraft_Success() {
        AircraftDTO a1 = new AircraftDTO("A1", "Model1", 100);
        AircraftDTO a2 = new AircraftDTO("A2", "Model2", 150);
        
        when(aircraftService.findAllAircraftDTO()).thenReturn(Arrays.asList(a1, a2));

        ResponseEntity<ApiResponse<List<AircraftDTO>>> response = aircraftRestController.getAllAircraft();
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("SUCCESS", response.getBody().getStatus());
        assertEquals(2, response.getBody().getResponseData().size());
        
        verify(aircraftService, times(1)).findAllAircraftDTO();
    }

    @Test
    public void testGetAllAircraft_EmptyList() {
        when(aircraftService.findAllAircraftDTO()).thenReturn(Collections.emptyList());

        ResponseEntity<ApiResponse<List<AircraftDTO>>> response = aircraftRestController.getAllAircraft();
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(0, response.getBody().getResponseData().size());
        
        verify(aircraftService, times(1)).findAllAircraftDTO();
    }

    @Test
    public void testGetAircraftById_Success() {
        AircraftDTO aircraft = new AircraftDTO("A1", "Model1", 100);
        when(aircraftService.findByIdDTO("A1")).thenReturn(aircraft);

        ResponseEntity<ApiResponse<AircraftDTO>> response = aircraftRestController.getAircraftById("A1");
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("SUCCESS", response.getBody().getStatus());
        assertEquals("A1", response.getBody().getResponseData().getAircraftId());
        
        verify(aircraftService, times(1)).findByIdDTO("A1");
    }
}
