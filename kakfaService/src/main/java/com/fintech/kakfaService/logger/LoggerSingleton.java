package com.fintech.kakfaService.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerSingleton {

    private static LoggerSingleton instance;
    private final Logger logger;

    private LoggerSingleton() {
        // Initialize the logger
        this.logger = LoggerFactory.getLogger(LoggerSingleton.class);
    }

    public static LoggerSingleton getInstance() {
        if (instance == null) {
            synchronized (LoggerSingleton.class) {
                if (instance == null) {
                    instance = new LoggerSingleton();
                }
            }
        }
        return instance;
    }

    public Logger getLogger() {
        return logger;
    }
}

