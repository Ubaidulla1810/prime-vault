package com.BankingApplication.PrimeVault.mapper;

import com.BankingApplication.PrimeVault.dto.AccountCreateRequest;
import com.BankingApplication.PrimeVault.dto.AccountResponse;
import com.BankingApplication.PrimeVault.entity.Account;

public class AccountMapper {

    // Request --> Entity
    public static Account toEntity(AccountCreateRequest request) {
        Account account = new Account();
        account.setAccountHolderName(request.getAccountHolderName());
        account.setBalance(0.0); // system rule
        return account;
    }

    // Entity --> Response
    public static AccountResponse toResponse(Account account) {
        return new AccountResponse(
                account.getId(),
                account.getAccountHolderName(),
                account.getBalance()
        );
    }
}
