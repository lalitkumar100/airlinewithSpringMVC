package com.crimsonlogic.arilinemanangmentsystem.dto;

import javax.validation.constraints.Min;

/**
 * Data Transfer Object for add money request.
 * Used to transfer data between the client and the server.
 */
public class AddMoneyRequest {
    
    /**
     * The amount.
     */
    @Min(value = 1, message = "Amount must be greater than zero")
    private double amount;

    public AddMoneyRequest() {
    }

    public AddMoneyRequest(double amount) {
        this.amount = amount;
    }

    /**
     * Retrieves the amount.
     * @return double the result of the operation
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Sets the amount.
     * @param amount the amount
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }
}
