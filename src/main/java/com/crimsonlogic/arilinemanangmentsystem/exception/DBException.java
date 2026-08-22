package com.crimsonlogic.arilinemanangmentsystem.exception;

import org.springframework.http.HttpStatus;

public class DBException extends  CustomException{
    public DBException(String message) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
