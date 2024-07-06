package com.fintech.eWallet.kakfa;

import com.fintech.eWallet.dto.NotificationRequest;
import com.fintech.eWallet.utils.ExceptionHandler.customExceptions.NotificationServiceException;
import com.fintech.eWallet.utils.Logger.LoggerSingleton;
import org.slf4j.Logger;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

@Service
@EnableAsync
public class NotificationService {

    private final KafkaProducerService producerService;
    private final Logger logger = LoggerSingleton.getInstance().getLogger();

    public NotificationService(KafkaProducerService producerService) {
        this.producerService = producerService;
    }

    @Async
    @Retryable(value = {NotificationServiceException.class}, maxAttempts = 5, backoff = @Backoff(delay = 30000))
    public void sendNotification(NotificationRequest fundsTransfer) {
        try {
            producerService.sendFundsTransfer(fundsTransfer);
            logger.info("Notification sent for funds transfer: " + fundsTransfer);
        } catch (Exception e) {
            logger.error("Failed to send notification", e);

        }
    }
}
