package com.crimsonlogic.arilinemanangmentsystem.exception;

import org.springframework.http.HttpStatus;

public class PassengerException extends  CustomException{
    public PassengerException(String message, HttpStatus status) {
        super(message, status);
    }
}
