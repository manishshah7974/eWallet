package com.fintech.kakfaService.utils;

import com.fintech.kakfaService.logger.LoggerSingleton;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class SmsNotificationUtility {
    private final Logger logger = LoggerSingleton.getInstance().getLogger();

    public void sendSmsNotification(String mobile, String message) {

        logger.info("Sending Mobile notification to: " + mobile);
        logger.info(" SMS text: " + message);
        // Add SMS Configs & APIs as per different SMS SERVICE PROVIDER
    }
}
