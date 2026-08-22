package com.crimsonlogic.arilinemanangmentsystem.exception;

import org.springframework.http.HttpStatus;

public class BookingAuthorizationException extends CustomException {

    public BookingAuthorizationException(String message) {
        super(message, HttpStatus.FORBIDDEN);
    }
}