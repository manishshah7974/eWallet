package com.fintech.eWallet.utils.ExceptionHandler.customExceptions;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException(String message) {
        super(message);
    }
}
