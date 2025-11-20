package com.BankingApplication.PrimeVault.service;


import com.BankingApplication.PrimeVault.dto.AccountDto;


public interface AccountService {

    AccountDto createAccount(AccountDto account);

    AccountDto getAccountById(Long id);

    AccountDto deposit(Long id,double amount);

    AccountDto withDraw(Long id,double amount);
}
