package com.fintech.kakfaService.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class KycUpdateRequest {

    private Long id;

    private Long userId;


    private boolean notInvolvedInAntiMoneyLaunderingCheck;


    private boolean panValid;

    private boolean isAadhaarValid;


    private boolean bankAccountValid;

    private int cibilScore;

    private int experianScore;

}