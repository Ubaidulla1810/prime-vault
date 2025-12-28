package com.BankingApplication.PrimeVault.repo;

import com.BankingApplication.PrimeVault.domain.TransactionType;
import com.BankingApplication.PrimeVault.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
    public interface TransactionRepo extends JpaRepository<Transaction, Long> {

        Page<Transaction> findByFromAccountIdOrToAccountId(
                Long fromAccountId,
                Long toAccountId,
                Pageable pageable
        );

        @Query(""" 
    SELECT t FROM Transaction t
    WHERE
      (t.fromAccountId = :accountId OR t.toAccountId = :accountId)
      AND (:type IS NULL OR t.type = :type)
      AND (:fromDate IS NULL OR t.createdAt >= :fromDate)
      AND (:toDate IS NULL OR t.createdAt <= :toDate)""")
        Page<Transaction> findTransactionHistory(
                @Param("accountId") Long accountId,
                @Param("type") TransactionType type,
                @Param("fromDate") LocalDateTime fromDate,
                @Param("toDate") LocalDateTime toDate,
                Pageable pageable);
    }


