package com.crimsonlogic.arilinemanangmentsystem.dto;

public class ApiResponse<T> {

    private String status;
    private String message;
    private T responseData;

    // =========================================================
    // CONSTRUCTORS
    // =========================================================

    public ApiResponse() {
    }


    /**
     * Response with data.
     */
    public ApiResponse(
            String status,
            String message,
            T responseData) {

        this.status = status;
        this.message = message;
        this.responseData = responseData;
    }

    /**
     * Response without data.
     */
    public ApiResponse(
            String status,
            String message) {

        this.status = status;
        this.message = message;
        this.responseData = null;
    }

    // =========================================================
    // GETTERS AND SETTERS
    // =========================================================

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResponseData() {
        return responseData;
    }

    public void setResponseData(T responseData) {
        this.responseData = responseData;
    }
}