package com.crimsonlogic.arilinemanangmentsystem.service;

import com.crimsonlogic.arilinemanangmentsystem.model.LoyaltyAccount;
import com.crimsonlogic.arilinemanangmentsystem.model.User;

import java.time.LocalDateTime;

public interface LoyaltyAccountSerivce {

    public LoyaltyAccount createLoyaltyAccount(User user, LocalDateTime now);

}
