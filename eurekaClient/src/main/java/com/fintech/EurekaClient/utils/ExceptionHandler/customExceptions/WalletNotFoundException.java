package com.fintech.EurekaClient.utils.ExceptionHandler.customExceptions;


public class WalletNotFoundException extends RuntimeException {
    public WalletNotFoundException(String message) {
        super(message);
    }
}