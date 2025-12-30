package com.BankingApplication.PrimeVault.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;

@Schema(description = "Amount-based request payload")
public class AmountRequest {

    @Schema(description = "Transaction amount (must be positive)",
            example = "50000")
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
