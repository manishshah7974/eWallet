package com.fintech.eWallet.dto;

import lombok.*;
import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class NotificationRequest {

    private Long id;

    private Long senderWalletId;

    private String senderName;

    private String senderMobile;

    private String senderEmail;

    private BigDecimal senderUpdatedWalletBalance;


    private Long receiverWalletId;

    private String receiverName;

    private String receiverMobile;

    private String receiverEmail;

    private BigDecimal receiverUpdatedWalletBalance;

    private BigDecimal amount;

    private Date date;
}
