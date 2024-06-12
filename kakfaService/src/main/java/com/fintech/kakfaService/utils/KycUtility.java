package com.fintech.kakfaService.utils;

import com.fintech.kakfaService.dto.*;
import com.fintech.kakfaService.logger.LoggerSingleton;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class KycUtility {

    private final Logger logger = LoggerSingleton.getInstance().getLogger();

    public boolean validateAadhaar(AadhaarValidateRequest aadhaarValidateRequest) {
        logger.info("Call UIDAI Service");
        return true;
    }

    public boolean panValidation(PanValidateRequest panValidateRequest) {
        logger.info("Call NSDL Service");
        return true;
    }

    public boolean bankAccountValidation(PennyDropRequest pennyDropRequest) {
        logger.info("Call PennyDrop Service");
        return true;
    }
    public boolean notInvolvedInAMLCheck(PanValidateRequest panValidateRequest) {
        logger.info("Call Hunter Service");
        return true;
    }

    public CreditReport getCreditReport(ExperianCibilRequest experianCibilRequest) {
         logger.info("Call Experian & CIBIL Service");

        return new CreditReport(782, 800);
    }

}