package com.BankingApplication.PrimeVault.controller;

import com.BankingApplication.PrimeVault.domain.TransactionType;
import com.BankingApplication.PrimeVault.dto.*;
import com.BankingApplication.PrimeVault.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Tag(name = "Accounts",description = "Account creation, balance operations, and transfers")
@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Operation(summary = "Create a new bank account",
            description = "Creates a new account with zero initial balance")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Account created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input")})
    @PostMapping
    public ResponseEntity<AccountResponse> createAccount(@Valid  @RequestBody AccountCreateRequest request) {
        AccountResponse response= accountService.createAccount(request);
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    @Operation(summary = "Get account by ID",
            description = "Fetches account details including current balance")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Account found"),
            @ApiResponse(responseCode = "404", description = "Account not found")})
    @GetMapping("/{id}")
    public ResponseEntity<AccountResponse> getAccount(@PathVariable Long id) {
        return ResponseEntity.ok(accountService.getAccountById(id));
    }

    @Operation(summary = "Deposit money into an account",
            description = "Adds a positive amount to the account balance")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Deposit successful"),
            @ApiResponse(responseCode = "400", description = "Invalid amount"),
            @ApiResponse(responseCode = "404", description = "Account not found")})
    @PutMapping("/{id}/deposit")
    public ResponseEntity<AccountResponse> deposit(@PathVariable Long id, @Valid @RequestBody AmountRequest request) {
           AccountResponse response= accountService.deposit(id,request);
        return ResponseEntity.ok(response);
    }


    @Operation(summary = "Withdraw money from an account",
            description = "Deducts a positive amount from account balance if sufficient funds exist")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Withdrawal successful"),
            @ApiResponse(responseCode = "400", description = "Invalid amount"),
            @ApiResponse(responseCode = "404", description = "Account not found"),
            @ApiResponse(responseCode = "409", description = "Insufficient balance")})
    @PutMapping("/{id}/withdraw")
    public ResponseEntity<AccountResponse> withdraw(@PathVariable Long id,@Valid @RequestBody AmountRequest request) {
        AccountResponse response = accountService.withdraw(id, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get all accounts",
            description = "Returns all existing accounts with balances")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Accounts retrieved successfully")})
    @GetMapping("/all")
    public ResponseEntity<List<AccountResponse>> getAllAccounts() {
        List<AccountResponse> allAccount = accountService.getAllAccount();
        return new ResponseEntity<>(allAccount, HttpStatus.OK);
    }

    @Operation(summary = "Delete an account",
            description = "Deletes an account permanently if it exists")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Account deleted"),
            @ApiResponse(responseCode = "404", description = "Account not found")})
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAccount(@PathVariable Long id) {
        accountService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Transfer money between accounts",
            description = "Atomically transfers money from one account to another")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Transfer successful"),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Account not found"),
            @ApiResponse(responseCode = "409", description = "Insufficient balance")})
    @PostMapping("/transfer")
    public ResponseEntity<TransferResponse> transfer(@Valid @RequestBody TransferRequest request){
        TransferResponse response = accountService.transfer(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get transaction history for an account",
            description = "Returns paginated transaction history with optional filters")
    @GetMapping("/{accountId}/transactions")
    public ResponseEntity<Page<TransactionHistoryResponse>> getTransactionHistory(
            @PathVariable Long accountId,
            @RequestParam(required = false) TransactionType type,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate fromDate,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate toDate,
            @PageableDefault(size = 10,sort = "createdAt",direction = Sort.Direction.DESC)
            Pageable pageable){
        TransactionHistoryFilter filter = new TransactionHistoryFilter();
        filter.setType(type);
        filter.setFromDate(fromDate);
        filter.setToDate(toDate);
        return ResponseEntity.ok(accountService.getTransactionHistory(accountId, filter, pageable));
    }



}
