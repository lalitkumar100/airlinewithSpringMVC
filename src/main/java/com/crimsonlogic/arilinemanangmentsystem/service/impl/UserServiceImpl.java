package com.crimsonlogic.arilinemanangmentsystem.service.impl;

import com.crimsonlogic.arilinemanangmentsystem.dao.UserMapper;
import com.crimsonlogic.arilinemanangmentsystem.dto.RegistrationRequest;
import com.crimsonlogic.arilinemanangmentsystem.enumrator.Role;
import com.crimsonlogic.arilinemanangmentsystem.exception.InvalidHumanException;
import com.crimsonlogic.arilinemanangmentsystem.exception.RecordNotFoundException;
import com.crimsonlogic.arilinemanangmentsystem.exception.UserException;
import com.crimsonlogic.arilinemanangmentsystem.model.LoyaltyAccount;
import com.crimsonlogic.arilinemanangmentsystem.model.User;
import com.crimsonlogic.arilinemanangmentsystem.model.Wallet;
import com.crimsonlogic.arilinemanangmentsystem.service.LoyaltyAccountSerivce;
import com.crimsonlogic.arilinemanangmentsystem.service.UserService;
import com.crimsonlogic.arilinemanangmentsystem.service.WalletService;
import com.crimsonlogic.arilinemanangmentsystem.utility.IdGenerator;
import com.crimsonlogic.arilinemanangmentsystem.utility.ValidatorUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final WalletService walletService;
    private final LoyaltyAccountSerivce loyaltyAccountSerivce;

    public UserServiceImpl(UserMapper userMapper, WalletService walletService, LoyaltyAccountSerivce loyaltyAccountSerivce) {
        this.userMapper = userMapper;
        this.walletService = walletService;
        this.loyaltyAccountSerivce = loyaltyAccountSerivce;
    }

    @Override
    @Transactional
    public User registerUser(
            RegistrationRequest request)
            throws InvalidHumanException {

        LocalDateTime now = LocalDateTime.now();

        /*
         * Validate request
         */
        ValidatorUtil.validateEmail(request.getEmail());

        if (isEmailExists(request.getEmail())) {
            throw new UserException(
                    "Email is already registered.", HttpStatus.BAD_REQUEST);
        }
        ValidatorUtil.validateName(request.getFirstName());
        ValidatorUtil.validateName(request.getLastName());
        ValidatorUtil.validatePhone(request.getPhoneNumber());
        ValidatorUtil.validateAgeAdult(request.getDateOfBirth());
        ValidatorUtil.validatePassword(request.getPassword());

        /*
         * Create User entity
         */
        User user = new User();

        user.setId(IdGenerator.generateUserId());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setDateOfBirth(request.getDateOfBirth());
        user.setGender(request.getGender());
        user.setEmail(request.getEmail());
        user.setPhoneNumber(request.getPhoneNumber());

        /*
         * Never take role from registration request.
         * Every normal registration creates USER.
         */
        user.setRole(Role.USER);

        user.setCreatedAt(now);
        user.setUpdatedAt(now);
        user.setDeleted(false);

        /*
         * Hash password before storing
         */
        user.setPassword(
                User.hashPassword(request.getPassword())
        );

        /*
         * Insert User
         */
        userMapper.insertUser(user);

        /*
         * Create Wallet
         */
        Wallet wallet = walletService.createWallet(user,now);

        user.setWallet(wallet);

        /*
         * Create Loyalty Account
         */
        LoyaltyAccount loyaltyAccount = loyaltyAccountSerivce.createLoyaltyAccount(user,now);

        user.setLoyaltyAccount(loyaltyAccount);
        user.setPassword("*******");

        /*
         * Convert User entity to Response DTO
         */
        return user;
    }



    @Override
    public User getUserByEmail(String email) {

        if (email == null || email.isBlank()) {
            throw new RecordNotFoundException(
                    "User email cannot be empty."
            );
        }

        User user = userMapper.findByEmail(email);

        if (user == null) {
            throw new RecordNotFoundException(
                    "User not found with email: " + email
            );
        }

        return user;
    }

    @Override
    public User getUserById(String id) {

        if (id == null || id.isBlank()) {
            throw new RecordNotFoundException(
                    "User ID cannot be empty."
            );
        }

        User user = userMapper.findById(id);

        if (user == null) {
            throw new RecordNotFoundException(
                    "User not found with ID: " + id
            );
        }

        return user;
    }


    @Override
    public boolean isEmailExists(String email) {

        if (email == null || email.isBlank()) {
            return false;
        }

        User user = userMapper.findByEmail(email);

        return user != null;
    }

    @Override
    public void UpdateLoginTime(User user, LocalDateTime time) {
        if (user == null) {
            throw new RecordNotFoundException(
                    "User not found."
            );
        }

        if (time == null) {
            time = LocalDateTime.now();
        }

        userMapper.updateLastLogin(user.getId(), time);
    }


}