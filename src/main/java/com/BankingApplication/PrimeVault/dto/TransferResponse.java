package com.BankingApplication.PrimeVault.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferResponse {
    private Long fromAccountId;
    private Long toAccountId;
    private double amount;
    private double fromBalance;
    private double toBalance;

}
