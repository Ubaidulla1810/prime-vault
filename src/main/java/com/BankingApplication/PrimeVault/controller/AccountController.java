package com.BankingApplication.PrimeVault.controller;

import com.BankingApplication.PrimeVault.domain.TransactionType;
import com.BankingApplication.PrimeVault.dto.*;
import com.BankingApplication.PrimeVault.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;


    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@Valid  @RequestBody AccountCreateRequest request) {
        AccountResponse response= accountService.createAccount(request);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getAccount(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getAccountById(id));
    }

    @PutMapping("/{id}/deposit")
    public ResponseEntity<AccountResponse> deposit(@PathVariable Long id, @Valid @RequestBody AmountRequest request) {
           AccountResponse response= accountService.deposit(id,request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/withdraw")
    public ResponseEntity<AccountResponse> withdraw(@PathVariable Long id,@Valid @RequestBody AmountRequest request) {
        AccountResponse response = accountService.withdraw(id, request);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/all")
    public ResponseEntity<List<AccountResponse>> getAllAccounts() {
        List<AccountResponse> allAccount = accountService.getAllAccount();
        return new ResponseEntity<>(allAccount, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable Long id) {
        accountService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/transfer")
    public ResponseEntity<TransferResponse> transfer(@Valid @RequestBody TransferRequest request){
        TransferResponse response = accountService.transfer(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{accountId}/transactions")
    public ResponseEntity<Page<TransactionHistoryResponse>> getTransactionHistory(
            @PathVariable Long accountId,
            @RequestParam(required = false) TransactionType type,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fromDate,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate toDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        TransactionHistoryFilter filter = new TransactionHistoryFilter();
        filter.setType(type);
        filter.setFromDate(fromDate);
        filter.setToDate(toDate);

        Pageable pageable = PageRequest.of(page,size,Sort.by("createdAt").descending());

        return ResponseEntity.ok(accountService.getTransactionHistory(accountId, filter, pageable));
    }


}
