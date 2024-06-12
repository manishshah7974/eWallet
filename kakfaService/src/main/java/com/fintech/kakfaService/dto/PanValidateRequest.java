package com.fintech.kakfaService.dto;
import lombok.*;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PanValidateRequest {

    private String pan;

    private String accountHolderName;

}
