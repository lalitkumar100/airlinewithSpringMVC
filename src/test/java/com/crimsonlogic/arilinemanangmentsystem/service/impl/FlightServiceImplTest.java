package com.crimsonlogic.arilinemanangmentsystem.service.impl;

import com.crimsonlogic.arilinemanangmentsystem.dao.FlightMapper;
import com.crimsonlogic.arilinemanangmentsystem.dto.AddFlightRequest;
import com.crimsonlogic.arilinemanangmentsystem.dto.FlightDTO;
import com.crimsonlogic.arilinemanangmentsystem.enumrator.FlightStatus;
import com.crimsonlogic.arilinemanangmentsystem.exception.RecordNotFoundException;
import com.crimsonlogic.arilinemanangmentsystem.model.Aircraft;
import com.crimsonlogic.arilinemanangmentsystem.model.Airport;
import com.crimsonlogic.arilinemanangmentsystem.model.Flight;
import com.crimsonlogic.arilinemanangmentsystem.service.AircraftService;
import com.crimsonlogic.arilinemanangmentsystem.service.AirportService;
import com.crimsonlogic.arilinemanangmentsystem.dto.AirportDTO;
import com.crimsonlogic.arilinemanangmentsystem.dto.AircraftDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FlightServiceImplTest {

    @Mock
    private FlightMapper flightMapper;
    
    @Mock
    private AirportService airportService;
    
    @Mock
    private AircraftService aircraftService;

    @InjectMocks
    private FlightServiceImpl flightService;

    @Test
    public void testGetAllFlights_Success() {
        Flight f1 = new Flight();
        f1.setFlightId("F1");
        when(flightMapper.findAllFlights()).thenReturn(Arrays.asList(f1));

        List<Flight> result = flightService.getAllFlights();
        
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(flightMapper, times(1)).findAllFlights();
    }

    @Test
    public void testGetAllFlights_NotFound() {
        when(flightMapper.findAllFlights()).thenReturn(Collections.emptyList());
        
        assertThrows(RecordNotFoundException.class, () -> flightService.getAllFlights());
    }

    @Test
    public void testGetFlightById_Success() {
        Flight f1 = new Flight();
        f1.setFlightId("F1");
        when(flightMapper.findById("F1")).thenReturn(f1);

        Flight result = flightService.getFlightById("F1");
        
        assertNotNull(result);
        assertEquals("F1", result.getFlightId());
    }

    @Test
    public void testGetFlightById_NotFound() {
        when(flightMapper.findById("F1")).thenReturn(null);
        
        assertThrows(RecordNotFoundException.class, () -> flightService.getFlightById("F1"));
    }

    @Test
    public void testAddNewFlight_Success() {
        AddFlightRequest req = new AddFlightRequest();
        req.setSourceAirportCode("DEL");
        req.setDestinationAirportCode("BOM");
        req.setAircraftId("A1");
        req.setDepartureDateTime(LocalDateTime.now().plusDays(1));
        req.setArrivalDateTime(LocalDateTime.now().plusDays(1).plusHours(2));
        req.setBaseFare(5000.0);
        
        Airport src = new Airport(); src.setAirportCode("DEL");
        Airport dest = new Airport(); dest.setAirportCode("BOM");
        Aircraft aircraft = new Aircraft(); aircraft.setAircraftId("A1");
        
        when(airportService.getAirportByCode("DEL")).thenReturn(src);
        when(airportService.getAirportByCode("BOM")).thenReturn(dest);
        when(aircraftService.findById("A1")).thenReturn(aircraft);
        
        when(flightMapper.insertFlight(any(Flight.class))).thenReturn(1);
        
        Flight mockInsertedFlight = new Flight();
        mockInsertedFlight.setFlightId("NEW-F1");
        mockInsertedFlight.setSource(src);
        mockInsertedFlight.setDestination(dest);
        mockInsertedFlight.setAircraft(aircraft);
        
        when(flightMapper.findById(anyString())).thenReturn(mockInsertedFlight);
        when(airportService.getAirportByCodeDTO("DEL")).thenReturn(new AirportDTO());
        when(airportService.getAirportByCodeDTO("BOM")).thenReturn(new AirportDTO());
        when(aircraftService.findByIdDTO("A1")).thenReturn(new AircraftDTO());

        FlightDTO result = flightService.addNewFlight(req);
        
        assertNotNull(result);
        verify(flightMapper, times(1)).insertFlight(any(Flight.class));
    }
}
