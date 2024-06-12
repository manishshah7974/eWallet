package com.fintech.EurekaClient.utils.ExceptionHandler.customExceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
