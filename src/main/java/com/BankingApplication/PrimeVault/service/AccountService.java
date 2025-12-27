package com.BankingApplication.PrimeVault.service;

import com.BankingApplication.PrimeVault.dto.*;

import java.util.List;

public interface AccountService {

    AccountResponse createAccount(AccountCreateRequest request);

    AccountResponse getAccountById(Long id);

    AccountResponse deposit(Long id, AmountRequest request);

    AccountResponse withdraw(Long id, AmountRequest request);

    List<AccountResponse> getAllAccount();

    void deleteById(Long id);

    TransferResponse transfer(TransferRequest request);
}

