package com.crimsonlogic.arilinemanangmentsystem.exception;

import org.springframework.http.HttpStatus;

public class UserException extends  CustomException{
    public UserException(String message, HttpStatus status) {
        super(message, status);
    }
}
