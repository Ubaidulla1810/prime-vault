package com.BankingApplication.PrimeVault.service;

import com.BankingApplication.PrimeVault.dto.*;
import com.BankingApplication.PrimeVault.entity.Account;
import com.BankingApplication.PrimeVault.exceptions.AccountNotFoundException;
import com.BankingApplication.PrimeVault.exceptions.InsufficientBalanceException;
import com.BankingApplication.PrimeVault.mapper.AccountMapper;
import com.BankingApplication.PrimeVault.repo.AccountRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
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
                .orElseThrow(() -> new AccountNotFoundException("Account does not exist"));
        return AccountMapper.toResponse(account);
    }

    @Transactional
    @Override
    public AccountResponse deposit(Long id, AmountRequest request) {

        log.info("start deposit | accountId={} | thread={}", id, Thread.currentThread().getName());

        Account account = accountRepo.findByIdForUpdate(id).orElseThrow(()->new AccountNotFoundException("Account does not exists"));

        log.info("lock | accountId={} | thread={}", id, Thread.currentThread().getName());

        if (account == null) {
            throw new AccountNotFoundException("Account does not exists");
        }

        if (request.getAmount() <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        account.setBalance(account.getBalance() + request.getAmount());
        Account saved=accountRepo.save(account);
        log.info("end deposit | accountId={} | thread={}", id, Thread.currentThread().getName());
        return AccountMapper.toResponse(saved);
    }

    @Transactional
    @Override
    public AccountResponse withdraw(Long id, AmountRequest request) {

        log.info("start withdraw | accountId={} | thread={}", id, Thread.currentThread().getName());
        Account account = accountRepo.findByIdForUpdate(id).orElseThrow(()->new AccountNotFoundException("Account does not exists"));

        log.info("lock | accountId={} | thread={}", id, Thread.currentThread().getName());
        if (account == null) {
            throw new AccountNotFoundException("Account does not exists");
        }

        if (account.getBalance() < request.getAmount()) {
            throw new InsufficientBalanceException("Insufficient balance");
        }

//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }

        account.setBalance(account.getBalance() - request.getAmount());
        Account saved = accountRepo.save(account);
        log.info("end withdraw | accountId={} | thread={}", id, Thread.currentThread().getName());
        return AccountMapper.toResponse(saved);
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
        if (!accountRepo.existsById(id)) {
            throw new AccountNotFoundException("Account does not exists");
        }
        accountRepo.deleteById(id);
    }


    @Transactional
    public TransferResponse transfer(TransferRequest request){

        if (request.getAmount() <= 0) {
            throw new IllegalArgumentException("Transfer amount must be positive");
        }

        if (request.getFromAccountId().equals(request.getToAccountId())){
            throw new IllegalArgumentException("Cannot transfer to same account");
        }

        Long fromId=request.getFromAccountId();
        Long toId=request.getToAccountId();


//        here the deadlock prevention
        Long firstLocked=Math.min(fromId,toId);
        Long secondLocked=Math.max(fromId,toId);

        Account first=
                accountRepo.findByIdForUpdate(firstLocked).orElseThrow(()-> new AccountNotFoundException("Account does not exists:"+firstLocked));
        Account second=
                accountRepo.findByIdForUpdate(secondLocked).orElseThrow(()-> new AccountNotFoundException("Account does not exists:"+secondLocked));

        Account from;
        Account to;

        if (first.getId().equals(fromId)){
            from=first;
            to=second;
        }else {
            from=second;
            to=first;
        }

        if (from.getBalance() < request.getAmount()){
            throw new InsufficientBalanceException("Insufficient balance");
        }

        from.setBalance(from.getBalance() - request.getAmount());
        to.setBalance(to.getBalance() + request.getAmount());

        accountRepo.save(from);
        accountRepo.save(to);

        return new TransferResponse(
                from.getId(),
                to.getId(),
                request.getAmount(),
                from.getBalance(),
                to.getBalance()
        );
    }
}
