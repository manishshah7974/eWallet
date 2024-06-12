package com.fintech.EurekaClient.utils.ExceptionHandler.customExceptions;

public class WalletBalanceUpdateException extends RuntimeException {
    public WalletBalanceUpdateException(String message) {
        super(message);
    }
}