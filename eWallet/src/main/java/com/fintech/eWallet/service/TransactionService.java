package com.fintech.eWallet.service;

import com.fintech.eWallet.model.FundsTransfer;
import com.fintech.eWallet.model.Transaction;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService {
    Transaction createTransaction(Long walletId, BigDecimal amount, String type);

    FundsTransfer transferFunds(FundsTransfer fundsTransfer);

    List<Transaction> getTransactionsByWalletId(Long walletId);

    Transaction getTransactionById(Long transactionId);
    List<Transaction> getAllTransaction();

    List<FundsTransfer> getAllFundsTranser();
}
