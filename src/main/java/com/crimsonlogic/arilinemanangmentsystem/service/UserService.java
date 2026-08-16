package com.crimsonlogic.arilinemanangmentsystem.service;

import com.crimsonlogic.arilinemanangmentsystem.exception.InvalidHumanException;
import com.crimsonlogic.arilinemanangmentsystem.model.User;

public interface UserService {

    /**
     * Registers a new user along with their initial Wallet and Loyalty Account.
     *
     * @param user User object containing registration details
     * @return Registered User object populated with IDs and relations
     */
    User registerUser(User user) throws InvalidHumanException;
}