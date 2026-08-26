package com.crimsonlogic.arilinemanangmentsystem.controller.restcontroller;

import com.crimsonlogic.arilinemanangmentsystem.dto.ApiResponse;
import com.crimsonlogic.arilinemanangmentsystem.dto.UserDTO;
import com.crimsonlogic.arilinemanangmentsystem.dto.AddMoneyRequest;
import com.crimsonlogic.arilinemanangmentsystem.model.User;
import com.crimsonlogic.arilinemanangmentsystem.service.AuthService;
import com.crimsonlogic.arilinemanangmentsystem.service.WalletService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * REST/MVC Controller for managing user rest controller operations.
 * Handles HTTP requests and delegates to the appropriate services.
 */
@RestController
@RequestMapping("/api/v1/user")
public class UserRestController {

    /**
     * The auth service.
     */
    private final AuthService authService;
    private final WalletService walletService;

    public UserRestController(AuthService authService, WalletService walletService) {
        this.authService = authService;
        this.walletService = walletService;
    }

    /**
     * Retrieves the user profile.
     * @param request the request
     * @return ResponseEntity<ApiResponse<UserDTO>> the result of the operation
     */
    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<UserDTO>> getUserProfile(HttpServletRequest request) {
        User user = authService.getAuthenticatedUser(request);
        
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setDateOfBirth(user.getDateOfBirth());
        userDTO.setGender(user.getGender());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setRole(user.getRole());
        userDTO.setCreatedAt(user.getCreatedAt());
        userDTO.setLastLoginAt(user.getLastLoginAt());
        
        if (user.getWallet() != null) {
            userDTO.setWalletBalance(user.getWallet().getBalance());
        }
        if (user.getLoyaltyAccount() != null) {
            userDTO.setLoyaltyPoints(user.getLoyaltyAccount().getPoints());
        }

        return ResponseEntity.ok(
                new ApiResponse<>("SUCCESS", "User profile retrieved successfully", userDTO)
        );
    }

    @PostMapping("/wallet/add")
    public ResponseEntity<ApiResponse<Void>> addMoneyToWallet(
            HttpServletRequest request,
            @Valid @RequestBody AddMoneyRequest addMoneyRequest) {
            
        User user = authService.getAuthenticatedUser(request);
        walletService.addMoneyToWallet(user.getId(), addMoneyRequest.getAmount());

        return ResponseEntity.ok(
                new ApiResponse<>("SUCCESS", "Money added to wallet successfully", null)
        );
    }
}
