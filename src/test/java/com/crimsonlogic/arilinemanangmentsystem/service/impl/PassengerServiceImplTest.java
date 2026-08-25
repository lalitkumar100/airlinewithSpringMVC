package com.crimsonlogic.arilinemanangmentsystem.service.impl;

import com.crimsonlogic.arilinemanangmentsystem.dao.PassengerMapper;
import com.crimsonlogic.arilinemanangmentsystem.exception.CustomException;
import com.crimsonlogic.arilinemanangmentsystem.exception.DBException;
import com.crimsonlogic.arilinemanangmentsystem.exception.NullValueException;
import com.crimsonlogic.arilinemanangmentsystem.model.Booking;
import com.crimsonlogic.arilinemanangmentsystem.model.Passenger;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PassengerServiceImplTest {

    @Mock
    private PassengerMapper passengerMapper;

    @InjectMocks
    private PassengerServiceImpl passengerService;

    @Test
    public void testSavePassengersForBooking_Success() {
        Booking booking = new Booking();
        booking.setBookingId("B1");

        Passenger p1 = new Passenger();
        p1.setFirstName("John");
        p1.setLastName("Doe");
        p1.setDateOfBirth(LocalDate.now().minusYears(30));
        
        List<Passenger> passengers = new ArrayList<>();
        passengers.add(p1);

        when(passengerMapper.insertPassenger(any(Passenger.class))).thenReturn(1);

        List<Passenger> result = passengerService.savePassengersForBooking(booking, passengers);
        
        assertNotNull(result);
        assertEquals(1, result.size());
        assertNotNull(result.get(0).getPassengerId());
        assertEquals("B1", result.get(0).getBooking().getBookingId());
        
        verify(passengerMapper, times(1)).insertPassenger(any(Passenger.class));
    }

    @Test
    public void testSavePassengersForBooking_EmptyList() {
        Booking booking = new Booking();
        
        assertThrows(CustomException.class, () -> passengerService.savePassengersForBooking(booking, Collections.emptyList()));
        assertThrows(CustomException.class, () -> passengerService.savePassengersForBooking(booking, null));
    }

    @Test
    public void testGetPassengerById_Success() {
        Passenger p = new Passenger();
        p.setPassengerId("P1");
        
        when(passengerMapper.getPassengerById("P1")).thenReturn(p);
        
        Passenger result = passengerService.getPassengerById("P1");
        assertEquals("P1", result.getPassengerId());
    }

    @Test
    public void testCancelPassenger_NullId() {
        assertThrows(NullValueException.class, () -> passengerService.cancelPassenger(null));
    }
}
