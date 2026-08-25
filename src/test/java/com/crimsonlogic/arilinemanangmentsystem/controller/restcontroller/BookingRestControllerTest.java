package com.crimsonlogic.arilinemanangmentsystem.controller.restcontroller;

import com.crimsonlogic.arilinemanangmentsystem.dto.*;
import com.crimsonlogic.arilinemanangmentsystem.enumrator.CancelType;
import com.crimsonlogic.arilinemanangmentsystem.model.User;
import com.crimsonlogic.arilinemanangmentsystem.service.AuthService;
import com.crimsonlogic.arilinemanangmentsystem.service.BookingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingRestControllerTest {

    @Mock
    private BookingService bookingService;
    
    @Mock
    private AuthService authService;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private BookingRestController bookingRestController;

    @Test
    public void testProcessBooking_Success() {
        BookingRequest bookingReq = new BookingRequest();
        User mockUser = new User();
        mockUser.setId("U1");
        
        BookingConfirmationResponse confirmation = new BookingConfirmationResponse();
        
        when(authService.getAuthenticatedUser(request)).thenReturn(mockUser);
        when(bookingService.createBooking(bookingReq, mockUser)).thenReturn(confirmation);

        ResponseEntity<ApiResponse<BookingConfirmationResponse>> response = bookingRestController.processBooking(bookingReq, request);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("SUCCESS", response.getBody().getStatus());
        verify(bookingService, times(1)).createBooking(bookingReq, mockUser);
    }

    @Test
    public void testGetMyBookings_Success() {
        User mockUser = new User();
        mockUser.setId("U1");
        BookingDTO b1 = new BookingDTO();
        
        when(authService.getAuthenticatedUser(request)).thenReturn(mockUser);
        when(bookingService.getAllBookingsForUserDTO("U1")).thenReturn(Arrays.asList(b1));

        ResponseEntity<ApiResponse<List<BookingDTO>>> response = bookingRestController.getMyBookings(request);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().getResponseData().size());
        verify(bookingService, times(1)).getAllBookingsForUserDTO("U1");
    }

    @Test
    public void testCancelBooking_FullBooking() {
        User mockUser = new User();
        PasswordRequest pwdReq = new PasswordRequest();
        pwdReq.setPassword("secret");
        
        when(authService.getAuthenticatedUser(request)).thenReturn(mockUser);
        doNothing().when(bookingService).globalBookingCancel("B1", mockUser, "secret", CancelType.FULL_BOOKING);

        ResponseEntity<ApiResponse<String>> response = bookingRestController.cancelBooking("B1", pwdReq, request);
        
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("SUCCESS", response.getBody().getStatus());
        verify(bookingService, times(1)).globalBookingCancel("B1", mockUser, "secret", CancelType.FULL_BOOKING);
    }
}
