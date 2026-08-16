package com.crimsonlogic.arilinemanangmentsystem.service.impl;

import com.crimsonlogic.arilinemanangmentsystem.enumrator.Role;
import com.crimsonlogic.arilinemanangmentsystem.enumrator.WalletStatus;
import com.crimsonlogic.arilinemanangmentsystem.dao.LoyaltyAccountMapper;
import com.crimsonlogic.arilinemanangmentsystem.dao.UserMapper;
import com.crimsonlogic.arilinemanangmentsystem.dao.WalletMapper;
import com.crimsonlogic.arilinemanangmentsystem.exception.InvalidHumanException;
import com.crimsonlogic.arilinemanangmentsystem.model.LoyaltyAccount;
import com.crimsonlogic.arilinemanangmentsystem.model.User;
import com.crimsonlogic.arilinemanangmentsystem.model.Wallet;
import com.crimsonlogic.arilinemanangmentsystem.service.UserService;
import com.crimsonlogic.arilinemanangmentsystem.utility.IdGenerator;
import com.crimsonlogic.arilinemanangmentsystem.utility.ValidatorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final WalletMapper walletMapper;
    private final LoyaltyAccountMapper loyaltyAccountMapper;

    @Autowired
    public UserServiceImpl(UserMapper userMapper,
                           WalletMapper walletMapper,
                           LoyaltyAccountMapper loyaltyAccountMapper) {
        this.userMapper = userMapper;
        this.walletMapper = walletMapper;
        this.loyaltyAccountMapper = loyaltyAccountMapper;
    }

    @Override
    @Transactional
    public User registerUser(User user) throws InvalidHumanException {
        LocalDateTime now = LocalDateTime.now();

        ValidatorUtil.validateEmail(user.getEmail());
        ValidatorUtil.validatePhone(user.getPhoneNumber());
        ValidatorUtil.validateAge(user.getDateOfBirth());
        ValidatorUtil.validatePassword(user.getPassword());


       //id gentation
      user.setId(IdGenerator.generateUserId());

      if (user.getRole() == null) {
          user.setRole(Role.USER);
        }
        user.setCreatedAt(now);
        user.setUpdatedAt(now);
        user.setDeleted(false);

        // Hash raw password
         user.setPassword(User.hashPassword(user.getPassword()));


        // Insert User entity into DB
        userMapper.insertUser(user);

        // 2. Create and initialize Wallet entity
        Wallet wallet = new Wallet();
        wallet.setWalletId(IdGenerator.generateWalletId());
        wallet.setUser(user);
        wallet.setBalance(0.00);
        wallet.setCurrency("INR");
        wallet.setStatus(WalletStatus.ACTIVE);
        wallet.setCreatedAt(now);
        wallet.setUpdatedAt(now);
        wallet.setDeleted(false);

        // Insert Wallet into DB & link to User
        walletMapper.insertWallet(wallet);
        user.setWallet(wallet);

        // 3. Create and initialize LoyaltyAccount entity
        LoyaltyAccount loyaltyAccount = new LoyaltyAccount(); // Defaults: points=0, tier=SILVER
        loyaltyAccount.setCreatedAt(now);
        loyaltyAccount.setUpdatedAt(now);
        loyaltyAccount.setDeleted(false);

        String loyaltyAccountId = IdGenerator.generateLoyaltyId();

        // Insert LoyaltyAccount into DB & link to User
        loyaltyAccountMapper.insertLoyaltyAccount(loyaltyAccountId, user.getId(), loyaltyAccount);
        user.setLoyaltyAccount(loyaltyAccount);

        return user;
    }
}