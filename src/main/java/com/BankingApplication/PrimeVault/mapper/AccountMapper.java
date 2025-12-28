package com.BankingApplication.PrimeVault.mapper;

import com.BankingApplication.PrimeVault.dto.AccountCreateRequest;
import com.BankingApplication.PrimeVault.dto.AccountResponse;
import com.BankingApplication.PrimeVault.dto.TransactionHistoryResponse;
import com.BankingApplication.PrimeVault.entity.Account;
import com.BankingApplication.PrimeVault.entity.Transaction;

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

    public static TransactionHistoryResponse toHistoryResponse(Transaction tx) {
        return new TransactionHistoryResponse(
                tx.getId(),
                tx.getType().name(),
                tx.getFromAccountId(),
                tx.getToAccountId(),
                tx.getAmount(),
                tx.getBalanceBefore(),
                tx.getBalanceAfter(),
                tx.getCreatedAt()
        );
    }

}
