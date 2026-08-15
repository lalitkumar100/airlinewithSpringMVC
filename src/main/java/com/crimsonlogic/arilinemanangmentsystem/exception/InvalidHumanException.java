package com.crimsonlogic.arilinemanangmentsystem.exception;

public class InvalidHumanException extends  Exception{
    public InvalidHumanException(String message) {
        super(message);
    }

    public InvalidHumanException(String message, Throwable cause) {
        super(message, cause);
    }
}
