package com.fintech.eWallet.utils.ExceptionHandler.customExceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
