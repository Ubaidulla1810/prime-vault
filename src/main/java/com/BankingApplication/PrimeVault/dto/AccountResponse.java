package com.BankingApplication.PrimeVault.dto;

import java.util.List;

public class AccountResponse {

    private Long id;
    private String accountHolderName;
    private double balance;

    public AccountResponse() {
    }

    public AccountResponse(Long id,String accountHolderName, double balance) {
        this.id=id;
        this.accountHolderName=accountHolderName;
        this.balance= balance;
    }

    public Long getId() {
        return id;
    }

    public String getAccountHolderName() {
        return accountHolderName;
    }

    public double getBalance() {
        return balance;
    }

}
