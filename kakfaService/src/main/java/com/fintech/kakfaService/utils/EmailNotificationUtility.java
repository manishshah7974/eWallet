package com.fintech.kakfaService.utils;

import com.fintech.kakfaService.logger.LoggerSingleton;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class EmailNotificationUtility {


    private final Logger logger = LoggerSingleton.getInstance().getLogger();

    public void sendEmailNotification(String email, String message) {

        logger.info("Sending email notification to: " + email);
        logger.info("Email message: " + message);
        // Add Email Configs & APIs as per different EMAIL SERVICE PROVIDER
    }
}
