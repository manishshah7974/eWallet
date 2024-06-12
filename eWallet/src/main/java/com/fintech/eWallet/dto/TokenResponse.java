package com.fintech.eWallet.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class TokenResponse {
    private String accessToken;
    private String refreshToken;
}
