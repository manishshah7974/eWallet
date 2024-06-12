package com.fintech.kakfaService.service;

import com.fintech.eWallet.dto.KycData;
import com.fintech.kakfaService.dto.*;
import com.fintech.kakfaService.logger.LoggerSingleton;
import com.fintech.kakfaService.utils.KycServiceClient;
import com.fintech.kakfaService.utils.KycUtility;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class KycVerification {

    private final Logger logger = LoggerSingleton.getInstance().getLogger();
    @Autowired
    private KycUtility kycUtility;
    @Autowired
    private KycServiceClient kycServiceClient;
    private ExecutorService executorService = Executors.newFixedThreadPool(7);

    public CompletableFuture<KycUpdateRequest> updateKYCAsync(KycUpdateRequest kycUpdateRequest,
                                                              PanValidateRequest panValidateRequest,
                                                              AadhaarValidateRequest aadhaarValidateRequest,
                                                              PennyDropRequest pennyDropRequest,
                                                              ExperianCibilRequest experianCibilRequest) {

        CompletableFuture<Boolean> validatePanFuture = CompletableFuture.supplyAsync(() ->
                kycUtility.panValidation(panValidateRequest), executorService);

        CompletableFuture<Boolean> validateAadhaarFuture = CompletableFuture.supplyAsync(() ->
                kycUtility.validateAadhaar(aadhaarValidateRequest), executorService);

        CompletableFuture<Boolean> validateBankAccountFuture = CompletableFuture.supplyAsync(() ->
                kycUtility.bankAccountValidation(pennyDropRequest), executorService);

        CompletableFuture<Boolean> notInvolvedInAMLCheckFuture = CompletableFuture.supplyAsync(() ->
                kycUtility.notInvolvedInAMLCheck(panValidateRequest), executorService);

        CompletableFuture<CreditReport> creditReportFuture = CompletableFuture.supplyAsync(() ->
                kycUtility.getCreditReport(experianCibilRequest), executorService);

        return CompletableFuture.allOf(validatePanFuture, validateAadhaarFuture, validateBankAccountFuture,
                notInvolvedInAMLCheckFuture, creditReportFuture).thenApply(voidResult -> {

            // Get results from futures
            boolean validatePan = validatePanFuture.join();
            boolean validateAadhaar = validateAadhaarFuture.join();
            boolean validateBankAccount = validateBankAccountFuture.join();
            boolean notInvolvedInAMLCheck = notInvolvedInAMLCheckFuture.join();
            CreditReport creditReport = creditReportFuture.join();

            // Update kycUpdateRequest
            kycUpdateRequest.setId(kycUpdateRequest.getId());
            kycUpdateRequest.setUserId(kycUpdateRequest.getUserId());
            kycUpdateRequest.setPanValid(validatePan);
            kycUpdateRequest.setAadhaarValid(validateAadhaar);
            kycUpdateRequest.setBankAccountValid(validateBankAccount);
            kycUpdateRequest.setNotInvolvedInAntiMoneyLaunderingCheck(notInvolvedInAMLCheck);
            kycUpdateRequest.setCibilScore(creditReport.getCibilScore());
            kycUpdateRequest.setExperianScore(creditReport.getExperianScore());


            return kycUpdateRequest;
        });
    }


    public void processKyc(KycData kycData) {
        KycUpdateRequest kycUpdateRequest = new KycUpdateRequest();
        kycUpdateRequest.setId(kycData.getId());
        kycUpdateRequest.setUserId(kycData.getUserId());

        AadhaarValidateRequest aadhaarValidateRequest = new AadhaarValidateRequest();
        aadhaarValidateRequest.setAadhaarNumber(kycData.getAadhaarNumber());
        aadhaarValidateRequest.setMobile(kycData.getMobile());

        ExperianCibilRequest experianCibilRequest = new ExperianCibilRequest();
        experianCibilRequest.setAccountHolderName(kycData.getAccountHolderName());
        experianCibilRequest.setMobile(kycData.getMobile());
        experianCibilRequest.setPan(kycData.getPan());

        PanValidateRequest panValidateRequest = new PanValidateRequest();
        panValidateRequest.setAccountHolderName(kycData.getAccountHolderName());
        panValidateRequest.setPan(kycData.getPan());

        PennyDropRequest pennyDropRequest = new PennyDropRequest();
        pennyDropRequest.setAccountHolderName(kycData.getAccountHolderName());
        pennyDropRequest.setBankName(kycData.getBankName());
        pennyDropRequest.setAccountNumber(kycData.getAccountNumber());
        pennyDropRequest.setBranchName(kycData.getBranchName());
        pennyDropRequest.setIfscCode(kycData.getIfscCode());


        CompletableFuture<KycUpdateRequest> future = updateKYCAsync(kycUpdateRequest, panValidateRequest,
                aadhaarValidateRequest, pennyDropRequest, experianCibilRequest);

        // Handle completion without blocking the main thread
        future.whenComplete((result, ex) -> {
            if (ex != null) {
                System.err.println("An error occurred: " + ex.getMessage());
                ex.printStackTrace();
            } else {
                kycServiceClient.updateKycStatus(result);
                logger.info("KYC Update Completed: " + result.toString());
            }
        });

        // Continue with other tasks in the main thread...
        System.out.println("Main thread is not blocked and can continue doing other tasks.");

    }
}
