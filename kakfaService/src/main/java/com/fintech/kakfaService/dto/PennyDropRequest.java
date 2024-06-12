package com.fintech.kakfaService.dto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PennyDropRequest {
    private String bankName;
    private String branchName;
    private String accountHolderName;
    private String accountNumber;
    private String ifscCode;

}
