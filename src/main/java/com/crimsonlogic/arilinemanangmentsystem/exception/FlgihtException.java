package com.crimsonlogic.arilinemanangmentsystem.exception;

import org.springframework.http.HttpStatus;

public class FlgihtException extends CustomException{
    public FlgihtException(String message,HttpStatus status) {

        super(message,status);
    }
}
