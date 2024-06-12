package com.fintech.EurekaClient.utils.ExceptionHandler;

import com.fintech.EurekaClient.utils.ExceptionHandler.customExceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), "USER_ALREADY_EXISTS", System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ErrorResponse> handleInvalidPasswordException(InvalidPasswordException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), "INVALID_PASSWORD", System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), "USER_NOT_FOUND", System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(KycNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleKycNotFoundException(KycNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), "KYC_DATA_NOT_FOUND_FOR_THIS_USER", System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> resourceNotFoundException(ResourceNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), "RESOURCE_NOT_FOUND", System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> illegalArgumentException(IllegalArgumentException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), "BAD_REQUEST", System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WalletNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleWalletNotFoundException(WalletNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), "WALLET_NOT_FOUND", System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserWalletsNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserWalletsNotFoundException(UserWalletsNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), "USER_WALLETS_NOT_FOUND", System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(WalletCreationException.class)
    public ResponseEntity<ErrorResponse> handleWalletCreationException(WalletCreationException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), "WALLET_CREATION_ERROR", System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(WalletBalanceUpdateException.class)
    public ResponseEntity<ErrorResponse> handleWalletBalanceUpdateException(WalletBalanceUpdateException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), "WALLET_BALANCE_UPDATE_ERROR", System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(WalletServiceException.class)
    public ResponseEntity<ErrorResponse> handleWalletServiceException(WalletServiceException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), "WALLET_SERVICE_ERROR", System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), "INTERNAL_SERVER_ERROR", System.currentTimeMillis());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
