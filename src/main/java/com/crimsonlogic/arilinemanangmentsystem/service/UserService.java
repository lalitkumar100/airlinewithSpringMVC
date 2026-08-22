package com.crimsonlogic.arilinemanangmentsystem.service;

import com.crimsonlogic.arilinemanangmentsystem.dto.RegistrationRequest;
import com.crimsonlogic.arilinemanangmentsystem.exception.InvalidHumanException;
import com.crimsonlogic.arilinemanangmentsystem.model.User;

import java.time.LocalDateTime;

public interface UserService {

    /**
     * Registers a new user along with their initial Wallet and Loyalty Account.
     *
     * @param user User object containing registration details
     * @return Registered User object populated with IDs and relations
     */
    User registerUser(RegistrationRequest registrationRequest) throws InvalidHumanException;
    User getUserByEmail(String email);
    User getUserById(String id);

    public boolean isEmailExists(String email);

    void UpdateLoginTime(User user, LocalDateTime time);


}