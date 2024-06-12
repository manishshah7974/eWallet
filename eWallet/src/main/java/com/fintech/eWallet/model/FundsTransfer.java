package com.fintech.eWallet.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "funds_transfer")
public class FundsTransfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Long senderWalletId;

    @NotNull
    private Long receiverWalletId;

    @NotNull
    private BigDecimal amount;


    private Date date;

}
