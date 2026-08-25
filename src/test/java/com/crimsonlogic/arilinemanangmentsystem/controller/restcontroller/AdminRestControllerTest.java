package com.crimsonlogic.arilinemanangmentsystem.controller.restcontroller;

import com.crimsonlogic.arilinemanangmentsystem.dto.ApiResponse;
import com.crimsonlogic.arilinemanangmentsystem.dto.BookingDTO;
import com.crimsonlogic.arilinemanangmentsystem.dto.FlightDTO;
import com.crimsonlogic.arilinemanangmentsystem.model.RevenueReport;
import com.crimsonlogic.arilinemanangmentsystem.service.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdminRestControllerTest {

    @Mock
    private TicketService ticketService;
    @Mock
    private FlightService flightService;
    @Mock
    private FlightReportService flightReportService;
    @Mock
    private BookingService bookingService;
    @Mock
    private FlightOrchestratorService flightOrchestratorService;

    @InjectMocks
    private AdminRestController adminRestController;

    @Test
    public void testGetAllFlights() {
        FlightDTO f1 = new FlightDTO();
        when(flightService.getAllFlightsDTO()).thenReturn(Arrays.asList(f1));

        ResponseEntity<ApiResponse<List<FlightDTO>>> response = adminRestController.getAllFlights();
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getResponseData().size());
        verify(flightService, times(1)).getAllFlightsDTO();
    }

    @Test
    public void testGetFlightBookings() {
        BookingDTO b1 = new BookingDTO();
        when(bookingService.getFlightBookingsDTO("F1")).thenReturn(Arrays.asList(b1));

        ResponseEntity<ApiResponse<List<BookingDTO>>> response = adminRestController.getFlightBookings("F1");
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getResponseData().size());
        verify(bookingService, times(1)).getFlightBookingsDTO("F1");
    }

    @Test
    public void testGetFlightRevenue() {
        RevenueReport report = new RevenueReport("F1", 1000.0, 500.0);
        when(flightReportService.getFlightRevenueReport("F1")).thenReturn(report);

        ResponseEntity<ApiResponse<RevenueReport>> response = adminRestController.getFlightRevenue("F1");
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getResponseData());
        verify(flightReportService, times(1)).getFlightRevenueReport("F1");
    }
}
