package com.fintech.eWallet.service;

import com.fintech.eWallet.dto.KycUpdateRequest;
import com.fintech.eWallet.model.Kyc;

public interface KycService {
    Kyc submitKyc(Kyc kyc);

    Kyc updateKycStatus(KycUpdateRequest kycUpdateRequest);


    Kyc getKycByUserId(Long userId);
}

