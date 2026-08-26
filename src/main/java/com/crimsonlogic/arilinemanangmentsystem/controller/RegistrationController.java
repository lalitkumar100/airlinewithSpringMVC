package com.crimsonlogic.arilinemanangmentsystem.controller;

import com.crimsonlogic.arilinemanangmentsystem.enumrator.Gender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * REST/MVC Controller for managing registration controller operations.
 * Handles HTTP requests and delegates to the appropriate services.
 */
@Controller
public class RegistrationController {

    /**
     * Executes the show register page operation.
     * @param model the model
     * @return String the result of the operation
     */
    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("genders", Gender.values());
        return "user/register"; // Maps to register.jsp
    }
}