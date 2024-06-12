package com.fintech.eWallet.kakfa;

import com.fintech.eWallet.dto.NotificationRequest;
import com.fintech.eWallet.model.FundsTransfer;
import com.fintech.eWallet.utils.Logger.LoggerSingleton;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    private final KafkaProducerService producerService;

    public NotificationService(KafkaProducerService producerService) {
        this.producerService = producerService;
    }

    private final Logger logger = LoggerSingleton.getInstance().getLogger();

    public void sendNotification(NotificationRequest fundsTransfer) {
        producerService.sendFundsTransfer(fundsTransfer);
        logger.info("Notification sent for funds transfer: " + fundsTransfer);
    }
}
