package com.BankingApplication.PrimeVault.service;

import com.BankingApplication.PrimeVault.dto.AccountDto;
import com.BankingApplication.PrimeVault.entity.Account;
import com.BankingApplication.PrimeVault.mapper.AccountMapper;
import com.BankingApplication.PrimeVault.repo.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepo accountRepo;

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account = AccountMapper.mapToAccount(accountDto);
        Account saved = accountRepo.save(account);
        return AccountMapper.mapToAccountDto(saved);
    }

    @Override
    public AccountDto getAccountById(Long id) {
        Account account = accountRepo.findById(id).orElseThrow(() -> new RuntimeException("Account does not exist"));
        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public AccountDto deposit(Long id, double amount) {
        Account account = accountRepo.findById(id).orElseThrow(() -> new RuntimeException("Account does not exist"));
        double totalBalance = account.getBalance() + amount;
        account.setBalance(totalBalance);
        Account save = accountRepo.save(account);
        return AccountMapper.mapToAccountDto(save);

    }

    @Override
    public AccountDto withDraw(Long id, double amount) {
        Account account = accountRepo.findById(id).orElseThrow(() -> new RuntimeException("Account does not exist"));
        if (account.getBalance() > amount) {
            double newBalance = account.getBalance() - amount;
            account.setBalance(newBalance);
        } else {
            throw new RuntimeException("Insufficient Balance");
        }
        Account save = accountRepo.save(account);
        return AccountMapper.mapToAccountDto(save);
    }

    @Override
    public List<AccountDto> getAllAccount() {
        List<AccountDto> list = accountRepo.findAll().stream().map((account) -> AccountMapper.mapToAccountDto(account)).toList();
        return list;
    }

    @Override
    public void deleteById(Long id) {
        Account account = accountRepo.findById(id).orElseThrow(() -> new RuntimeException("account does not exist"));
        accountRepo.deleteById(account.getId());
    }

}
