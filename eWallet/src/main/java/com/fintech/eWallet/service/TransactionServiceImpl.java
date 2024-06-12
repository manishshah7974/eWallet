package com.fintech.eWallet.service;

import com.fintech.eWallet.dto.NotificationRequest;
import com.fintech.eWallet.kakfa.NotificationService;
import com.fintech.eWallet.model.*;
import com.fintech.eWallet.repository.FundsTransferRepository;
import com.fintech.eWallet.repository.TransactionRepository;
import com.fintech.eWallet.repository.WalletRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private WalletRepository walletRepository;

    @Autowired
    private FundsTransferRepository fundsTransferRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private WalletService walletService;


    @Transactional
    public Transaction createTransaction(Long walletId, BigDecimal amount, String type) {

        Optional<Wallet> walletOptional = walletRepository.findById(walletId);

        if (walletOptional.isEmpty()) {
            throw new IllegalArgumentException("Wallet Not found");
        }

        Wallet wallet = walletOptional.get();
        if (type.equalsIgnoreCase(Constants.DEBIT) && wallet.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient Balance In Sender Wallet");
        }

        Transaction transaction = new Transaction();
        transaction.setWalletId(walletId);
        transaction.setAmount(amount);
        transaction.setTransactionType(type);
        transaction.setDate(new Date());

        if (type.equalsIgnoreCase(Constants.DEBIT)) {
            wallet.setBalance(wallet.getBalance().subtract(amount));
        } else if (type.equalsIgnoreCase(Constants.CREDIT)) {
            wallet.setBalance(wallet.getBalance().add(amount));
        } else {
            throw new IllegalArgumentException("Invalid transaction type");
        }

        walletRepository.save(wallet);
        return transactionRepository.save(transaction);
    }

    @Transactional
    public FundsTransfer transferFunds(FundsTransfer fundsTransfer) {
        Optional<Wallet> senderWalletOpt = walletRepository.findById(fundsTransfer.getSenderWalletId());
        Optional<Wallet> receiverWalletOpt = walletRepository.findById(fundsTransfer.getReceiverWalletId());
        if (senderWalletOpt.isEmpty() || receiverWalletOpt.isEmpty()) {
            throw new IllegalArgumentException("Wallet not found");
        }
        Wallet senderWallet = senderWalletOpt.get();
        Wallet receiverWallet = receiverWalletOpt.get();

        BigDecimal amount = fundsTransfer.getAmount();

        if (senderWallet.getBalance().compareTo(amount) < 0) {
            throw new IllegalArgumentException("Insufficient balance in Sender Wallet");
        }

        createTransaction(receiverWallet.getId(), amount, Constants.CREDIT);
        createTransaction(senderWallet.getId(), amount, Constants.DEBIT);
        fundsTransfer.setDate(new Date());

        FundsTransfer savedFundsTransfer = fundsTransferRepository.save(fundsTransfer);


        // Sending Notification
        sendNotification(savedFundsTransfer, senderWallet, receiverWallet);


        return savedFundsTransfer;

    }

    public void sendNotification(FundsTransfer savedFundsTransfer, Wallet senderWallet, Wallet receiverWallet) {

        User sender = walletService.findUserByWalletId(senderWallet.getId());
        User receiver = walletService.findUserByWalletId(receiverWallet.getId());
        NotificationRequest notificationRequest =
                NotificationRequest.builder()
                        .id(savedFundsTransfer.getId())
                        .amount(savedFundsTransfer.getAmount())
                        .date(savedFundsTransfer.getDate())
                        .senderName(sender.getName())
                        .senderMobile(sender.getMobile())
                        .senderEmail(sender.getEmail())
                        .senderWalletId(senderWallet.getId())
                        .senderUpdatedWalletBalance(senderWallet.getBalance())
                        .receiverName(receiver.getName())
                        .receiverMobile(receiver.getMobile())
                        .receiverEmail(receiver.getEmail())
                        .receiverWalletId(receiverWallet.getId())
                        .receiverUpdatedWalletBalance(receiverWallet.getBalance())
                        .build();

        notificationService.sendNotification(notificationRequest);
    }

    public List<Transaction> getTransactionsByWalletId(Long walletId) {
        return transactionRepository.findByWalletId(walletId);
    }

    public Transaction getTransactionById(Long transactionId) {
        return transactionRepository.findById(transactionId).orElseThrow(() -> new IllegalArgumentException("Transaction not found"));
    }

    @Override
    public List<Transaction> getAllTransaction() {
        return transactionRepository.findAll();
    }

    @Override
    public List<FundsTransfer> getAllFundsTranser() {
        return fundsTransferRepository.findAll();
    }
}