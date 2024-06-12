package com.fintech.EurekaClient.utils.ExceptionHandler.customExceptions;

public class WalletCreationException extends RuntimeException {
    public WalletCreationException(String message) {
        super(message);
    }
}