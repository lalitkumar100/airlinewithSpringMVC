package com.crimsonlogic.arilinemanangmentsystem.service.impl;

import com.crimsonlogic.arilinemanangmentsystem.dao.LoyaltyAccountMapper;
import com.crimsonlogic.arilinemanangmentsystem.model.LoyaltyAccount;
import com.crimsonlogic.arilinemanangmentsystem.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoyaltyAccountSerivceImplTest {

    @Mock
    private LoyaltyAccountMapper loyaltyAccountMapper;

    @InjectMocks
    private LoyaltyAccountSerivceImpl loyaltyAccountService;

    @Test
    public void testCreateLoyaltyAccount() {
        User user = new User();
        user.setId("U123");
        LocalDateTime now = LocalDateTime.now();
        
        when(loyaltyAccountMapper.insertLoyaltyAccount(anyString(), anyString(), any(LoyaltyAccount.class))).thenReturn(1);

        LoyaltyAccount result = loyaltyAccountService.createLoyaltyAccount(user, now);
        
        assertNotNull(result);
        assertEquals(now, result.getCreatedAt());
        assertEquals(now, result.getUpdatedAt());
        assertFalse(result.isDeleted());
        
        verify(loyaltyAccountMapper, times(1)).insertLoyaltyAccount(anyString(), eq("U123"), any(LoyaltyAccount.class));
    }
}
