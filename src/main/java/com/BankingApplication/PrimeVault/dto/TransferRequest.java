package com.BankingApplication.PrimeVault.dto;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransferRequest {

    @NonNull
    private Long fromAccountId;
    @NonNull
    private Long toAccountId;
    @Positive
    private double amount;

}
