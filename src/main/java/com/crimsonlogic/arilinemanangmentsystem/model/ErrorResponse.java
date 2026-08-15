package com.crimsonlogic.arilinemanangmentsystem.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL) // Hides stackTrace field if it's null (e.g., in production)
public class ErrorResponse {
    private int statusCode;
    private String message;
    private String stackTrace; // New field for development mode
    private long timestamp;

    // Constructor for normal / production use
    public ErrorResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
        this.timestamp = System.currentTimeMillis();
    }

    // Constructor when stack trace needs to be included
    public ErrorResponse(int statusCode, String message, String stackTrace) {
        this.statusCode = statusCode;
        this.message = message;
        this.stackTrace = stackTrace;
        this.timestamp = System.currentTimeMillis();
    }

    // Getters and Setters
    public int getStatusCode() { return statusCode; }
    public void setStatusCode(int statusCode) { this.statusCode = statusCode; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getStackTrace() { return stackTrace; }
    public void setStackTrace(String stackTrace) { this.stackTrace = stackTrace; }

    public long getTimestamp() { return timestamp; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
}