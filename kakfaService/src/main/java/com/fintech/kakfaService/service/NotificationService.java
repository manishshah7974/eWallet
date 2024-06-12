package com.fintech.kakfaService.service;

import com.fintech.eWallet.dto.NotificationRequest;
import com.fintech.kakfaService.utils.EmailNotificationUtility;
import com.fintech.kakfaService.utils.SmsNotificationUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class NotificationService {

    @Autowired
    private EmailNotificationUtility emailNotificationUtility;

    @Autowired
    private SmsNotificationUtility smsNotificationUtility;


    // Handling multiple notification methods
    public void processTransaction(NotificationRequest notificationRequest) {

        // ForkJoinPool.commonPool()  --> Thread Count --> Processor Core -1

        CompletableFuture<Void> senderNotificationFuture = sendNotificationToSender(notificationRequest);
        CompletableFuture<Void> receiverNotificationFuture = sendNotificationToReceiver(notificationRequest);

        // Do not wait for acknowledgement to complete

    }

    private CompletableFuture<Void> sendNotificationToSender(NotificationRequest notificationRequest) {
        // Prepare sender notification
        return CompletableFuture.runAsync(() -> {
        String senderMessage = "Dear " + notificationRequest.getSenderName() + ", your transaction of ₹" +
                notificationRequest.getAmount() + " to " + notificationRequest.getReceiverName() +
                " was successful. Your updated wallet balance is ₹" +
                notificationRequest.getSenderUpdatedWalletBalance();

        // Send email notification to sender
        emailNotificationUtility.sendEmailNotification(notificationRequest.getSenderEmail(), senderMessage);

        // Send SMS notification to sender
        smsNotificationUtility.sendSmsNotification(notificationRequest.getSenderMobile(), senderMessage);
    });
    }

    private CompletableFuture<Void> sendNotificationToReceiver(NotificationRequest notificationRequest) {
        // Prepare receiver notification
        return CompletableFuture.runAsync(() -> {
            String receiverMessage = "Dear " + notificationRequest.getReceiverName() + ", you received a transaction of ₹" +
                    notificationRequest.getAmount() + " from " + notificationRequest.getSenderName() +
                    ". Your updated wallet balance is ₹" +
                    notificationRequest.getReceiverUpdatedWalletBalance();

            // Send email notification to receiver
            emailNotificationUtility.sendEmailNotification(notificationRequest.getReceiverEmail(), receiverMessage);

            // Send SMS notification to receiver
            smsNotificationUtility.sendSmsNotification(notificationRequest.getReceiverMobile(), receiverMessage);
        });
    }
}
