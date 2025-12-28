package com.BankingApplication.PrimeVault.service;

import com.BankingApplication.PrimeVault.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.transaction.annotation.Transactional;


import java.util.List;

public interface AccountService {

    AccountResponse createAccount(AccountCreateRequest request);

    AccountResponse getAccountById(Long id);

    AccountResponse deposit(Long id, AmountRequest request);

    AccountResponse withdraw(Long id, AmountRequest request);

    List<AccountResponse> getAllAccount();

    void deleteById(Long id);

    TransferResponse transfer(TransferRequest request);

    @Transactional(readOnly = true)
    Page<TransactionHistoryResponse> getTransactionHistory(Long accountId,TransactionHistoryFilter filter, Pageable pageable);


}

