package com.BankingApplication.PrimeVault.repo;

import com.BankingApplication.PrimeVault.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepo extends JpaRepository<Account,Long> {

}
