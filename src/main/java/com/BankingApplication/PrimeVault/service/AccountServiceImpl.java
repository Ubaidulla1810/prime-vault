package com.BankingApplication.PrimeVault.service;

import com.BankingApplication.PrimeVault.dto.AccountCreateRequest;
import com.BankingApplication.PrimeVault.dto.AccountResponse;
import com.BankingApplication.PrimeVault.dto.AmountRequest;
import com.BankingApplication.PrimeVault.entity.Account;
import com.BankingApplication.PrimeVault.mapper.AccountMapper;
import com.BankingApplication.PrimeVault.repo.AccountRepo;
import com.BankingApplication.PrimeVault.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepo accountRepo;

    @Override
    public AccountResponse createAccount(AccountCreateRequest request) {
        Account account = AccountMapper.toEntity(request);
        Account saved = accountRepo.save(account);
        return AccountMapper.toResponse(saved);
    }

    @Override
    public AccountResponse getAccountById(Long id) {
        Account account = accountRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Account does not exist"));
        return AccountMapper.toResponse(account);
    }

    @Override
    public AccountResponse deposit(Long id, AmountRequest request) {
        Account account = accountRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Account does not exist"));

        account.setBalance(account.getBalance() + request.getAmount());
        return AccountMapper.toResponse(accountRepo.save(account));
    }

    @Override
    public AccountResponse withdraw(Long id, AmountRequest request) {
        Account account = accountRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Account does not exist"));

        if (account.getBalance() < request.getAmount()) {
            throw new RuntimeException("Insufficient balance");
        }

        account.setBalance(account.getBalance() - request.getAmount());
        return AccountMapper.toResponse(accountRepo.save(account));
    }

    @Override
    public List<AccountResponse> getAllAccount() {
        return accountRepo.findAll()
                .stream()
                .map(AccountMapper::toResponse)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        accountRepo.deleteById(id);
    }
}
