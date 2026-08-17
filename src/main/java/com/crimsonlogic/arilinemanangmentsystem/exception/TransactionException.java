package com.crimsonlogic.arilinemanangmentsystem.exception;

import org.springframework.http.HttpStatus;

public class TransactionException extends CustomException{
    public TransactionException(String message, HttpStatus status) {
        super(message, status);
    }
}
