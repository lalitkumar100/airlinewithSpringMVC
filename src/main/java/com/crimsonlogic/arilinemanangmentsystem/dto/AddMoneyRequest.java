package com.crimsonlogic.arilinemanangmentsystem.dto;

import javax.validation.constraints.Min;

public class AddMoneyRequest {
    
    @Min(value = 1, message = "Amount must be greater than zero")
    private double amount;

    public AddMoneyRequest() {
    }

    public AddMoneyRequest(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
