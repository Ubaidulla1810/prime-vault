package com.BankingApplication.PrimeVault.dto;

import java.time.LocalDateTime;


public class TransactionHistoryResponse {

    private Long id;
    private String type;
    private Long fromAccountId;
    private Long toAccountId;
    private double amount;
    private double balanceBefore;
    private double balanceAfter;
    private LocalDateTime createdAt;


    public TransactionHistoryResponse(Long id, String type, Long fromAccountId, Long toAccountId, double amount, double balanceBefore, double balanceAfter, LocalDateTime createdAt) {
        this.id = id;
        this.type = type;
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.amount = amount;
        this.balanceBefore = balanceBefore;
        this.balanceAfter = balanceAfter;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public Long getFromAccountId() {
        return fromAccountId;
    }

    public Long getToAccountId() {
        return toAccountId;
    }

    public double getAmount() {
        return amount;
    }

    public double getBalanceBefore() {
        return balanceBefore;
    }

    public double getBalanceAfter() {
        return balanceAfter;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
