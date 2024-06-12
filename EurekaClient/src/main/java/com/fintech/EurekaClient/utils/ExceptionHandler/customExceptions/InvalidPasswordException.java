package com.fintech.EurekaClient.utils.ExceptionHandler.customExceptions;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException(String message) {
        super(message);
    }
}
