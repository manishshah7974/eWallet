package com.fintech.EurekaClient.utils.ExceptionHandler.customExceptions;

public class WalletServiceException extends RuntimeException {
    public WalletServiceException(String message) {
        super(message);
    }
}