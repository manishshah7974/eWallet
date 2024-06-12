package com.fintech.kakfaService.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ExperianCibilRequest {
    private String pan;

    private String mobile;

    private String accountHolderName;
}
