package com.fintech.eWallet.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class KycUpdateRequest {

    private Long id;

    @NotNull
    private Long userId;

    @NotNull
    private boolean notInvolvedInAntiMoneyLaunderingCheck;

    @NotNull
    private boolean panValid;

    @NotNull
    private boolean aadhaarValid;

    @NotNull
    private boolean bankAccountValid;

    private int cibilScore;

    private int experianScore;


}