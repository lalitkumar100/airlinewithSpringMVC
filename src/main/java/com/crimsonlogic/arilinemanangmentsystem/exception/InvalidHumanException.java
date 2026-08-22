package com.crimsonlogic.arilinemanangmentsystem.exception;

import org.springframework.http.HttpStatus;

public class InvalidHumanException extends  CustomException{

    public InvalidHumanException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
