package com.crimsonlogic.arilinemanangmentsystem.exception;

import com.crimsonlogic.arilinemanangmentsystem.model.ErrorResponse;
import com.crimsonlogic.arilinemanangmentsystem.utility.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalRestExceptionHandler {

    private final boolean developmentMode = true; // Set to false in production

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingParams(MissingServletRequestParameterException ex) {
        ErrorResponse error;
        String message = "Missing required parameter: " + ex.getParameterName();
        if (developmentMode) {
            String stackTrace = ExceptionUtils.getStackTraceAsString(ex);
            error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), message, stackTrace);
        } else {
            error = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), message);
        }
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException ex) {
        ErrorResponse error;

        if (developmentMode) {
            String stackTrace = ExceptionUtils.getStackTraceAsString(ex);
            error = new ErrorResponse(ex.getStatus().value(), ex.getMessage(), stackTrace);
        } else {
            error = new ErrorResponse(ex.getStatus().value(), ex.getMessage());
        }

        return new ResponseEntity<>(error, ex.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex) {
        ErrorResponse error;

        if (developmentMode) {
            String stackTrace = ExceptionUtils.getStackTraceAsString(ex);
            error = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "An unexpected error occurred: " + ex.getMessage(),
                    stackTrace);
        } else {
            error = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    "An unexpected internal server error occurred.");
        }

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NullValueException.class)
    public ResponseEntity<ErrorResponse> handleNullValueException(
            NullValueException ex) {

        ErrorResponse error;

        HttpStatus status = HttpStatus.BAD_REQUEST;

        if (developmentMode) {

            String stackTrace =
                    ExceptionUtils.getStackTraceAsString(ex);

            error = new ErrorResponse(
                    status.value(),
                    ex.getMessage(),
                    stackTrace
            );

        } else {

            error = new ErrorResponse(
                    status.value(),
                    ex.getMessage()
            );
        }

        return new ResponseEntity<>(error, status);
    }

    /**
     * Handles validation errors from @Valid request bodies.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex) {

        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error ->
                        error.getField()
                                + ": "
                                + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        ErrorResponse error;

        if (developmentMode) {

            String stackTrace =
                    ExceptionUtils.getStackTraceAsString(ex);

            error = new ErrorResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    message,
                    stackTrace
            );

        } else {

            error = new ErrorResponse(
                    HttpStatus.BAD_REQUEST.value(),
                    message
            );
        }

        return ResponseEntity
                .badRequest()
                .body(error);
    }
}