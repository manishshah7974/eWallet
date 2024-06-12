package com.fintech.kakfaService.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AadhaarValidateRequest {
    private String aadhaarNumber;
    private String mobile;

}
