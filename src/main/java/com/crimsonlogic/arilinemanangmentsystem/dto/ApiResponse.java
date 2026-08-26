package com.crimsonlogic.arilinemanangmentsystem.dto;

/**
 * Data Transfer Object for api response.
 * Used to transfer data between the client and the server.
 */
public class ApiResponse<T> {

    private String status;
    /**
     * The message.
     */
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

    /**
     * Retrieves the status.
     * @return String the result of the operation
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets the status.
     * @param status the status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Retrieves the message.
     * @return String the result of the operation
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the message.
     * @param message the message
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Retrieves the response data.
     * @return T the result of the operation
     */
    public T getResponseData() {
        return responseData;
    }

    /**
     * Sets the response data.
     * @param responseData the response data
     */
    public void setResponseData(T responseData) {
        this.responseData = responseData;
    }
}