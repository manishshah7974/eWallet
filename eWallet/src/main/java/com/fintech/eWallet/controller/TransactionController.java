package com.fintech.eWallet.controller;
import com.fintech.eWallet.model.FundsTransfer;
import com.fintech.eWallet.model.Transaction;
import com.fintech.eWallet.service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/createTransaction")
    public Transaction createTransaction(@RequestBody Transaction transaction) {
        return transactionService.createTransaction(transaction.getId(), transaction.getAmount(), transaction.getTransactionType());
    }

    @PostMapping("/fundsTransfer")
    public FundsTransfer transferFunds(@RequestBody @Valid FundsTransfer fundsTransfer) {
        return transactionService.transferFunds(fundsTransfer);
    }
    @GetMapping("/wallet/{walletId}")
    public List<Transaction> getTransactionsByWalletId(@PathVariable Long walletId) {
        return transactionService.getTransactionsByWalletId(walletId);
    }

    @GetMapping("/{id}")
    public Transaction getTransactionById(@PathVariable Long id) {
        return transactionService.getTransactionById(id);
    }

    @GetMapping("/allTransaction")
    public List<Transaction> getAllTransaction(){
        return transactionService.getAllTransaction();
    }
    @GetMapping("/allFundsTransfer")
    public List<FundsTransfer> getAllFundsTransfer(){
        return transactionService.getAllFundsTranser();
    }
}
