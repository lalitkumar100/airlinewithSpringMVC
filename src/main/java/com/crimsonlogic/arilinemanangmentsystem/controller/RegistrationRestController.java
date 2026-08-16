package com.crimsonlogic.arilinemanangmentsystem.controller;

import com.crimsonlogic.arilinemanangmentsystem.exception.InvalidHumanException;
import com.crimsonlogic.arilinemanangmentsystem.model.User;
import com.crimsonlogic.arilinemanangmentsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/register")
public class RegistrationRestController {

    private final UserService userService;

    @Autowired
    public RegistrationRestController(UserService userService) {
        this.userService = userService;
    }

    /**
     * REST endpoint to register a new user, wallet, and loyalty account.
     *
     * @param user User payload from the HTTP request body
     * @return ResponseEntity containing the created User object and HTTP status 201 Created
     */
    @PostMapping("")
    public ResponseEntity<User> registerUser(@RequestBody User user) throws InvalidHumanException {
        User registeredUser = userService.registerUser(user);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }
}
