package com.fintech.kakfaService.service;

import com.fintech.eWallet.dto.KycData;
import com.fintech.eWallet.dto.NotificationRequest;
import com.fintech.kakfaService.logger.LoggerSingleton;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

    private final Logger logger = LoggerSingleton.getInstance().getLogger();

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private KycVerification kycVerification;
    @KafkaListener(topics = "funds_transfer", groupId = "notification-group" ,  containerFactory = "kafkaListenerContainerFactory")
    public void consume(@Payload NotificationRequest notificationRequest) {
        logger.info("Consumed NotificationRequest: " + notificationRequest);
        notificationService.processTransaction(notificationRequest);
    }

    @KafkaListener(topics = "kyc", groupId = "notification-group", containerFactory = "kafkaListenerContainerFactory")
    public void consume(@Payload KycData kyc) {
        logger.info("Consumed kyc Data: " + kyc);
        kycVerification.processKyc(kyc);
    }
}
