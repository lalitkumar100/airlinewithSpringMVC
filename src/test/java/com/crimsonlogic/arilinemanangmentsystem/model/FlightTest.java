package com.crimsonlogic.arilinemanangmentsystem.model;

import com.crimsonlogic.arilinemanangmentsystem.enumrator.FlightStatus;
import com.crimsonlogic.arilinemanangmentsystem.enumrator.SeatClass;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FlightTest {

    @Test
    public void testFlightGettersAndSetters() {
        Flight flight = new Flight();
        flight.setFlightId("FL-1001");
        flight.setFlightCode("AI101");
        flight.setBaseFare(5000.0);
        flight.setStatus(FlightStatus.SCHEDULED);
        flight.setDepartureDateTime(LocalDateTime.now().plusDays(1));
        flight.setArrivalDateTime(LocalDateTime.now().plusDays(1).plusHours(2));

        assertEquals("FL-1001", flight.getFlightId());
        assertEquals("AI101", flight.getFlightCode());
        assertEquals(5000.0, flight.getBaseFare());
        assertEquals(FlightStatus.SCHEDULED, flight.getStatus());
        assertNotNull(flight.getDepartureDateTime());
        assertNotNull(flight.getArrivalDateTime());
    }

    @Test
    public void testFlightRelationshipsWithMocks() {
        Flight flight = new Flight();
        
        Airport mockSource = mock(Airport.class);
        Airport mockDestination = mock(Airport.class);
        Aircraft mockAircraft = mock(Aircraft.class);
        
        flight.setSource(mockSource);
        flight.setDestination(mockDestination);
        flight.setAircraft(mockAircraft);
        
        assertEquals(mockSource, flight.getSource());
        assertEquals(mockDestination, flight.getDestination());
        assertEquals(mockAircraft, flight.getAircraft());
    }

    @Test
    public void testFlightDomainLogic() {
        Flight flight = new Flight();
        flight.setStatus(FlightStatus.SCHEDULED);
        
        flight.changeStatus(FlightStatus.DELAYED);
        assertEquals(FlightStatus.DELAYED, flight.getStatus());
    }
}
