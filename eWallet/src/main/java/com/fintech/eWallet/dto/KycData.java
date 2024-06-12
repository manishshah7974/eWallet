package com.fintech.eWallet.dto;
import lombok.*;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class KycData {


    private Long id;

    private Long userId;

    private String mobile;

    private String email;

    private String address;

    private String pan;

    private String aadhaarNumber;

    private String bankName;

    private String branchName;

    private String accountHolderName;

    private String accountNumber;


    private String ifscCode;

    private int cibilScore;

    private int experianScore;

    private boolean isPanValid;

    private boolean isAadhaarValid;

    private boolean isBankAccountValid;

    private boolean notInvolvedInAntiMoneyLaunderingCheck;

}
