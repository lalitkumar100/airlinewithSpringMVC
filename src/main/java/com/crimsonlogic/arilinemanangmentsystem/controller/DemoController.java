package com.crimsonlogic.arilinemanangmentsystem.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class DemoController {

    // Accessible by anyone with a valid JWT token (User or Admin) mapped to /api/v1/user/**
    @GetMapping("/user/test")
    public ResponseEntity<String> userEndpoint() {
        return ResponseEntity.ok("Success! You have accessed the USER endpoint.");
    }

    // Accessible ONLY by users with the ADMIN role mapped to /api/v1/admin/**
    @GetMapping("/admin/test")
    public ResponseEntity<String> adminEndpoint() {
        return ResponseEntity.ok("Success! You have accessed the ADMIN-only endpoint.");
    }


}