package com.crimsonlogic.arilinemanangmentsystem.dao;

import com.crimsonlogic.arilinemanangmentsystem.model.LoyaltyAccount;
import com.crimsonlogic.arilinemanangmentsystem.model.User;
@org.springframework.stereotype.Repository
public interface LoyaltyAccountMapper extends org.springframework.data.repository.Repository<LoyaltyAccount, String> {
    LoyaltyAccount save(LoyaltyAccount entity);


    default int insertLoyaltyAccount(String loyaltyAccountId, String userId, LoyaltyAccount account) {
        account.setLoyaltyAccountId(loyaltyAccountId);
        User user = new User();
        user.setId(userId);
        account.setUser(user);
        save(account);
        return 1;
    }
}
