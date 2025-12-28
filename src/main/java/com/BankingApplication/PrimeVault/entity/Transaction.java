package com.BankingApplication.PrimeVault.entity;

import com.BankingApplication.PrimeVault.domain.TransactionStatus;
import com.BankingApplication.PrimeVault.domain.TransactionType;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type;

    @Column(name = "from_account_id")
    private Long fromAccountId;

    @Column(name = "to_account_id")
    private Long toAccountId;

    @Column(nullable = false)
    private double amount;

    @Column(name = "balance_before", nullable = false)
    private double balanceBefore;

    @Column(name = "balance_after", nullable = false)
    private double balanceAfter;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionStatus status;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    protected Transaction() {

    }

    public Transaction(
            TransactionType type,
            Long fromAccountId,
            Long toAccountId,
            double amount,
            double balanceBefore,
            double balanceAfter
    ) {
        this.type = type;
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.amount = amount;
        this.balanceBefore = balanceBefore;
        this.balanceAfter = balanceAfter;
        this.status = TransactionStatus.SUCCESS;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public TransactionType getType() {
        return type;
    }

    public Long getFromAccountId() {
        return fromAccountId;
    }

    public Long getToAccountId() {
        return toAccountId;
    }

    public double getAmount() {
        return amount;
    }

    public double getBalanceBefore() {
        return balanceBefore;
    }

    public double getBalanceAfter() {
        return balanceAfter;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
