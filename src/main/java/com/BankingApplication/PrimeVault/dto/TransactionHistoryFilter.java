package com.BankingApplication.PrimeVault.dto;

import com.BankingApplication.PrimeVault.domain.TransactionType;

import java.time.LocalDate;

public class TransactionHistoryFilter {
    private TransactionType type;
    private LocalDate fromDate;
    private LocalDate toDate;

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }
}
