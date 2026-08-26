package com.crimsonlogic.arilinemanangmentsystem.service.impl;

import com.crimsonlogic.arilinemanangmentsystem.model.LoyaltyAccount;

import com.crimsonlogic.arilinemanangmentsystem.model.User;
import com.crimsonlogic.arilinemanangmentsystem.service.LoyaltyAccountSerivce;
import com.crimsonlogic.arilinemanangmentsystem.utility.IdGenerator;
import com.crimsonlogic.arilinemanangmentsystem.dao.LoyaltyAccountMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


/**
 * Service responsible for loyalty account serivce impl business logic.
 * Encapsulates core application rules and data manipulation.
 */
@Service
public class LoyaltyAccountSerivceImpl implements LoyaltyAccountSerivce {

    /**
     * The loyalty account mapper.
     */
    private final LoyaltyAccountMapper loyaltyAccountMapper;

    public LoyaltyAccountSerivceImpl(LoyaltyAccountMapper loyaltyAccountMapper) {
        this.loyaltyAccountMapper = loyaltyAccountMapper;
    }

    /**
     * Creates or saves create loyalty account.
     * @param user the user
     * @param now the now
     * @return LoyaltyAccount the result of the operation
     */
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
