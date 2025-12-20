package com.BankingApplication.PrimeVault.dto;

import jakarta.validation.constraints.Positive;

public class AmountRequest {

    @Positive
    private double amount;

    public AmountRequest() {

    }

    public AmountRequest(double amount) {
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
