package com.BankingApplication.PrimeVault.dto;

import jakarta.validation.constraints.NotBlank;

public class AccountCreateRequest {


    @NotBlank
    private String accountHolderName;

    public AccountCreateRequest(){
    }

    public AccountCreateRequest(String accountHolderName){
        this.accountHolderName=accountHolderName;
    }

    public void setAccountHolderName(String accountHolderName){
        this.accountHolderName=accountHolderName;
    }

    public String getAccountHolderName(){
        return accountHolderName;
    }
}
