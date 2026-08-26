package com.crimsonlogic.arilinemanangmentsystem.controller.restcontroller;

import com.crimsonlogic.arilinemanangmentsystem.dto.ApiResponse;
import com.crimsonlogic.arilinemanangmentsystem.dto.RegistrationRequest;
import com.crimsonlogic.arilinemanangmentsystem.exception.InvalidHumanException;
import com.crimsonlogic.arilinemanangmentsystem.model.User;
import com.crimsonlogic.arilinemanangmentsystem.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST/MVC Controller for managing registration rest controller operations.
 * Handles HTTP requests and delegates to the appropriate services.
 */
@RestController
@RequestMapping("/api/v1/register")
public class RegistrationRestController {

    /**
     * The user service.
     */
    private final UserService userService;

    public RegistrationRestController(UserService userService) {
        this.userService = userService;
    }

    /**
     * REST endpoint to register a new user, wallet, and loyalty account.
     *
     * @param registrationRequest RegistrationRequest payload from the HTTP request body
     * @return ResponseEntity containing the created User object and HTTP status 201 Created
     */
    @PostMapping("")
    public ResponseEntity<ApiResponse<User>> registerUser(@RequestBody RegistrationRequest registrationRequest) throws InvalidHumanException {

        User registeredUser = userService.registerUser(registrationRequest);

      return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponse<>(
                        "SUCCESS",
                        "New User is Registered Successfull",
                        registeredUser
                )
        );

    }
}
