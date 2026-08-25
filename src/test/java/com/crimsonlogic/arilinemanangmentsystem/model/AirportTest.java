package com.crimsonlogic.arilinemanangmentsystem.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AirportTest {

    @Test
    public void testAirportGettersAndSetters() {
        Airport airport = new Airport();
        airport.setAirportCode("DEL");
        airport.setAirportName("Indira Gandhi International");
        airport.setCity("New Delhi");
        airport.setCreatedAt(LocalDateTime.now());
        airport.setUpdatedAt(LocalDateTime.now());
        airport.setDeleted(false);

        assertEquals("DEL", airport.getAirportCode());
        assertEquals("Indira Gandhi International", airport.getAirportName());
        assertEquals("New Delhi", airport.getCity());
        assertNotNull(airport.getCreatedAt());
        assertNotNull(airport.getUpdatedAt());
        assertFalse(airport.isDeleted());
    }

    @Test
    public void testAirportRelationshipsWithMocks() {
        Airport airport = new Airport("BOM", "Chhatrapati Shivaji Maharaj", "Mumbai");
        
        Flight mockDepartingFlight = mock(Flight.class);
        Flight mockArrivingFlight = mock(Flight.class);
        
        ArrayList<Flight> departing = new ArrayList<>();
        departing.add(mockDepartingFlight);
        
        ArrayList<Flight> arriving = new ArrayList<>();
        arriving.add(mockArrivingFlight);
        
        airport.setDepartingFlights(departing);
        airport.setArrivingFlights(arriving);
        
        assertEquals(1, airport.getDepartingFlights().size());
        assertEquals(1, airport.getArrivingFlights().size());
        assertTrue(airport.getDepartingFlights().contains(mockDepartingFlight));
        assertTrue(airport.getArrivingFlights().contains(mockArrivingFlight));
    }

    @Test
    public void testAirportDomainLogic() {
        Airport airport = new Airport("BLR", "Kempegowda International", "Bangalore");
        
        String rowString = airport.toRow();
        String toStringOutput = airport.toString();
        
        assertTrue(rowString.contains("BLR"));
        assertTrue(rowString.contains("Kempegowda International"));
        assertTrue(toStringOutput.contains("Bangalore"));
    }
}
