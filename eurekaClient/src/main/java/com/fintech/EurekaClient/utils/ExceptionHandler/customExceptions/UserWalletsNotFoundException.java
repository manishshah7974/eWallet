package com.fintech.EurekaClient.utils.ExceptionHandler.customExceptions;

public class UserWalletsNotFoundException extends RuntimeException {
    public UserWalletsNotFoundException(String message) {
        super(message);
    }
}