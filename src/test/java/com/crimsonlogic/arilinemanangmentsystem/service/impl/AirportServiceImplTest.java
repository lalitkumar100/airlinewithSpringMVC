package com.crimsonlogic.arilinemanangmentsystem.service.impl;

import com.crimsonlogic.arilinemanangmentsystem.dao.AirportMapper;
import com.crimsonlogic.arilinemanangmentsystem.dto.AirportDTO;
import com.crimsonlogic.arilinemanangmentsystem.exception.RecordNotFoundException;
import com.crimsonlogic.arilinemanangmentsystem.model.Airport;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AirportServiceImplTest {

    @Mock
    private AirportMapper airportMapper;

    @InjectMocks
    private AirportServiceImpl airportService;

    @Test
    public void testGetAllAirports() {
        Airport a1 = new Airport("DEL", "Delhi Airport", "New Delhi");
        Airport a2 = new Airport("BOM", "Mumbai Airport", "Mumbai");
        when(airportMapper.findAllAirport()).thenReturn(Arrays.asList(a1, a2));

        List<Airport> result = airportService.getAllAirports();
        
        assertEquals(2, result.size());
        verify(airportMapper, times(1)).findAllAirport();
    }

    @Test
    public void testGetAirportByCode_Success() {
        Airport airport = new Airport("DEL", "Delhi Airport", "New Delhi");
        when(airportMapper.findById("DEL")).thenReturn(airport);

        Airport result = airportService.getAirportByCode("DEL");
        
        assertNotNull(result);
        assertEquals("DEL", result.getAirportCode());
    }

    @Test
    public void testGetAirportByCode_NotFound() {
        when(airportMapper.findById("UNKNOWN")).thenReturn(null);

        assertThrows(RecordNotFoundException.class, () -> airportService.getAirportByCode("UNKNOWN"));
    }

    @Test
    public void testGetAllAirportsDTO() {
        Airport a1 = new Airport("DEL", "Delhi Airport", "New Delhi");
        when(airportMapper.findAllAirport()).thenReturn(Arrays.asList(a1));

        List<AirportDTO> result = airportService.getAllAirportsDTO();
        
        assertEquals(1, result.size());
        assertEquals("DEL", result.get(0).getAirportCode());
    }

    @Test
    public void testGetAirportByCodeDTO_Success() {
        Airport airport = new Airport("DEL", "Delhi Airport", "New Delhi");
        when(airportMapper.findById("DEL")).thenReturn(airport);

        AirportDTO result = airportService.getAirportByCodeDTO("DEL");
        
        assertNotNull(result);
        assertEquals("DEL", result.getAirportCode());
        assertEquals("New Delhi", result.getCity());
    }
}
