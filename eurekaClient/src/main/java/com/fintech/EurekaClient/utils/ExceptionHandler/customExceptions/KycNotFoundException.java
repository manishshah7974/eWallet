package com.fintech.EurekaClient.utils.ExceptionHandler.customExceptions;

public class KycNotFoundException extends RuntimeException {
    public KycNotFoundException(String message) {
        super(message);
    }
}
