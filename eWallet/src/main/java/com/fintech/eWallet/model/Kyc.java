package com.fintech.eWallet.model;

import com.fintech.eWallet.validator.ValidPAN;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;

@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "kyc")
public class Kyc implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long userId;

    @NotBlank
    private String mobile;

    @NotBlank
    private String email;

    @NotBlank
    private String address;

    @ValidPAN
    private String pan;

    @NotBlank
    private String aadhaarNumber;

    @NotBlank(message = "Bank name is required")
    private String bankName;

    @NotBlank(message = "Branch name is required")
    private String branchName;

    @NotBlank(message = "Account holder name is required")
    private String accountHolderName;

    @NotBlank(message = "Account number is required")
    @Size(max = 18)
    @Pattern(regexp = "\\d{9,18}", message = "Account number must be between 9 and 18 digits")
    private String accountNumber;


    @NotBlank(message = "IFSC code is required")
    @Pattern(regexp = "^[A-Z]{4}0[A-Z0-9]{6}$", message = "Invalid IFSC code format")
    private String ifscCode;

    // To be updated by Kafka WebHOOK
    private int cibilScore;
    private int experianScore;
    private boolean isPanValid;
    private boolean isAadhaarValid;
    private boolean isBankAccountValid;
    private boolean notInvolvedInAntiMoneyLaunderingCheck;

}
