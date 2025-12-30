package com.BankingApplication.PrimeVault.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request to transfer money between accounts")
public class TransferRequest {

    @Schema(example = "1")
    @NonNull
    private Long fromAccountId;
    @Schema(example = "2")
    @NonNull
    private Long toAccountId;
    @Schema(example = "30000")
    @Positive
    private double amount;

}
