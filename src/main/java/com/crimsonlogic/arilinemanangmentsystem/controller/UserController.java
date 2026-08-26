package com.crimsonlogic.arilinemanangmentsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


import org.springframework.web.bind.annotation.RequestMapping;

/**
 * REST/MVC Controller for managing user controller operations.
 * Handles HTTP requests and delegates to the appropriate services.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    /**
     * Executes the profile page operation.
     * @return String the result of the operation
     */
    @GetMapping("/profile")
    public String profilePage() {
        return "user/profile";
    }

    /**
     * Executes the wallet page operation.
     * @return String the result of the operation
     */
    @GetMapping("/wallet")
    public String walletPage() {
        return "user/wallet";
    }
}
