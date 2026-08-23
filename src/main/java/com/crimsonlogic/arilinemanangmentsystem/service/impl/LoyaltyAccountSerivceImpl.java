package com.crimsonlogic.arilinemanangmentsystem.service.impl;

import com.crimsonlogic.arilinemanangmentsystem.model.LoyaltyAccount;

import com.crimsonlogic.arilinemanangmentsystem.model.User;
import com.crimsonlogic.arilinemanangmentsystem.service.LoyaltyAccountSerivce;
import com.crimsonlogic.arilinemanangmentsystem.utility.IdGenerator;
import com.crimsonlogic.arilinemanangmentsystem.dao.LoyaltyAccountMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class LoyaltyAccountSerivceImpl implements LoyaltyAccountSerivce {

    private final LoyaltyAccountMapper loyaltyAccountMapper;

    public LoyaltyAccountSerivceImpl(LoyaltyAccountMapper loyaltyAccountMapper) {
        this.loyaltyAccountMapper = loyaltyAccountMapper;
    }

    public LoyaltyAccount createLoyaltyAccount(User user, LocalDateTime now) {

        LoyaltyAccount loyaltyAccount = new LoyaltyAccount();

        loyaltyAccount.setCreatedAt(now);
        loyaltyAccount.setUpdatedAt(now);
        loyaltyAccount.setDeleted(false);

        String loyaltyAccountId = IdGenerator.generateLoyaltyId();

        loyaltyAccountMapper.insertLoyaltyAccount(
                loyaltyAccountId,
                user.getId(),
                loyaltyAccount
        );

        return loyaltyAccount;
    }
}
