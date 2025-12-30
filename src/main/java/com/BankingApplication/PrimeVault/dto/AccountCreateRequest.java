package com.BankingApplication.PrimeVault.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Request to create a new account")
public class AccountCreateRequest {

    @Schema(description = "Name of the account holder",
            example = "Khan",
            required = true)
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
