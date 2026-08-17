package com.crimsonlogic.arilinemanangmentsystem.exception;

import org.springframework.http.HttpStatus;

public class WalletException extends  CustomException{
    public WalletException(String message, HttpStatus status) {
        super(message, status);
    }
}
