package com.crimsonlogic.arilinemanangmentsystem.exception;

import org.springframework.http.HttpStatus;

public class PasswordVerificationException extends CustomException {

    public PasswordVerificationException(String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}