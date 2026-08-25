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
public class AircraftTest {

    @Test
    public void testAircraftGettersAndSetters() {
        Aircraft aircraft = new Aircraft();
        aircraft.setAircraftId("A320-101");
        aircraft.setModel("Airbus A320");
        aircraft.setCapacity(180);
        aircraft.setCreatedAt(LocalDateTime.now());
        aircraft.setUpdatedAt(LocalDateTime.now());
        aircraft.setDeleted(false);

        assertEquals("A320-101", aircraft.getAircraftId());
        assertEquals("Airbus A320", aircraft.getModel());
        assertEquals(180, aircraft.getCapacity());
        assertNotNull(aircraft.getCreatedAt());
        assertNotNull(aircraft.getUpdatedAt());
        assertFalse(aircraft.isDeleted());
    }

    @Test
    public void testAircraftRelationshipsWithMocks() {
        Aircraft aircraft = new Aircraft("B737-200", "Boeing 737", 200);
        
        Flight mockFlight1 = mock(Flight.class);
        Flight mockFlight2 = mock(Flight.class);
        
        ArrayList<Flight> flights = new ArrayList<>();
        flights.add(mockFlight1);
        flights.add(mockFlight2);
        
        aircraft.setFlights(flights);
        
        assertEquals(2, aircraft.getFlights().size());
        assertTrue(aircraft.getFlights().contains(mockFlight1));
        assertTrue(aircraft.getFlights().contains(mockFlight2));
    }

    @Test
    public void testAircraftDomainLogic() {
        Aircraft aircraft = new Aircraft("B777-300", "Boeing 777", 350);
        
        String rowString = aircraft.toRow();
        String toStringOutput = aircraft.toString();
        
        assertTrue(rowString.contains("B777-300"));
        assertTrue(rowString.contains("Boeing 777"));
        assertTrue(toStringOutput.contains("350"));
    }
}
