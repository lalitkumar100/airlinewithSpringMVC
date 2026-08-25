package com.crimsonlogic.arilinemanangmentsystem.service.impl;

import com.crimsonlogic.arilinemanangmentsystem.dao.UserMapper;
import com.crimsonlogic.arilinemanangmentsystem.dto.RegistrationRequest;
import com.crimsonlogic.arilinemanangmentsystem.enumrator.Gender;
import com.crimsonlogic.arilinemanangmentsystem.enumrator.Role;
import com.crimsonlogic.arilinemanangmentsystem.exception.InvalidHumanException;
import com.crimsonlogic.arilinemanangmentsystem.exception.UserException;
import com.crimsonlogic.arilinemanangmentsystem.model.LoyaltyAccount;
import com.crimsonlogic.arilinemanangmentsystem.model.User;
import com.crimsonlogic.arilinemanangmentsystem.model.Wallet;
import com.crimsonlogic.arilinemanangmentsystem.service.LoyaltyAccountSerivce;
import com.crimsonlogic.arilinemanangmentsystem.service.WalletService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserMapper userMapper;
    
    @Mock
    private WalletService walletService;
    
    @Mock
    private LoyaltyAccountSerivce loyaltyAccountSerivce;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void testRegisterUser_Success() throws InvalidHumanException {
        RegistrationRequest req = new RegistrationRequest();
        req.setEmail("newuser@test.com");
        req.setFirstName("John");
        req.setLastName("Doe");
        req.setPhoneNumber("1234567890");
        req.setDateOfBirth(LocalDate.now().minusYears(25));
        req.setPassword("Password123!");
        req.setGender(Gender.MALE);
        
        when(userMapper.findByEmail("newuser@test.com")).thenReturn(null);
        when(userMapper.insertUser(any(User.class))).thenReturn(1);
        
        Wallet mockWallet = new Wallet();
        when(walletService.createWallet(any(User.class), any(LocalDateTime.class))).thenReturn(mockWallet);
        
        LoyaltyAccount mockLoyalty = new LoyaltyAccount();
        when(loyaltyAccountSerivce.createLoyaltyAccount(any(User.class), any(LocalDateTime.class))).thenReturn(mockLoyalty);

        User result = userService.registerUser(req);
        
        assertNotNull(result);
        assertEquals("newuser@test.com", result.getEmail());
        assertEquals(Role.USER, result.getRole());
        assertEquals("*******", result.getPassword()); // Because it's masked at the end of registerUser
        
        verify(userMapper, times(1)).insertUser(any(User.class));
        verify(walletService, times(1)).createWallet(any(User.class), any(LocalDateTime.class));
        verify(loyaltyAccountSerivce, times(1)).createLoyaltyAccount(any(User.class), any(LocalDateTime.class));
    }

    @Test
    public void testRegisterUser_EmailExists() {
        RegistrationRequest req = new RegistrationRequest();
        req.setEmail("existing@test.com");
        
        when(userMapper.findByEmail("existing@test.com")).thenReturn(new User());

        assertThrows(UserException.class, () -> userService.registerUser(req));
    }
}
