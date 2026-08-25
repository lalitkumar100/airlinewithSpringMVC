package com.crimsonlogic.arilinemanangmentsystem.controller.restcontroller;

import com.crimsonlogic.arilinemanangmentsystem.dto.ApiResponse;
import com.crimsonlogic.arilinemanangmentsystem.dto.RegistrationRequest;
import com.crimsonlogic.arilinemanangmentsystem.exception.InvalidHumanException;
import com.crimsonlogic.arilinemanangmentsystem.model.User;
import com.crimsonlogic.arilinemanangmentsystem.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RegistrationRestControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private RegistrationRestController registrationRestController;

    @Test
    public void testRegisterUser_Success() throws InvalidHumanException {
        RegistrationRequest request = new RegistrationRequest();
        
        User mockUser = new User();
        mockUser.setId("U1");
        
        when(userService.registerUser(any(RegistrationRequest.class))).thenReturn(mockUser);

        ResponseEntity<ApiResponse<User>> response = registrationRestController.registerUser(request);
        
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("SUCCESS", response.getBody().getStatus());
        assertEquals("U1", response.getBody().getResponseData().getId());
        
        verify(userService, times(1)).registerUser(any(RegistrationRequest.class));
    }
}
