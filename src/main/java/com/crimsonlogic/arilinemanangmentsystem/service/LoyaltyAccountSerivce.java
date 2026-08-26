package com.crimsonlogic.arilinemanangmentsystem.service;

import com.crimsonlogic.arilinemanangmentsystem.model.LoyaltyAccount;
import com.crimsonlogic.arilinemanangmentsystem.model.User;

import java.time.LocalDateTime;

/**
 * Service responsible for loyalty account serivce business logic.
 * Encapsulates core application rules and data manipulation.
 */
public interface LoyaltyAccountSerivce {

    public LoyaltyAccount createLoyaltyAccount(User user, LocalDateTime now);

}
