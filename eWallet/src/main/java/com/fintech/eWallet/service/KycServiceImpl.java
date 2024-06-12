package com.fintech.eWallet.service;

import com.fintech.eWallet.dto.KycData;
import com.fintech.eWallet.dto.KycUpdateRequest;
import com.fintech.eWallet.kakfa.AsyncTaskHandler;
import com.fintech.eWallet.model.Kyc;
import com.fintech.eWallet.repository.KycRedisRepository;
import com.fintech.eWallet.repository.KycRepository;
import com.fintech.eWallet.utils.ExceptionHandler.customExceptions.KycNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class KycServiceImpl implements KycService {

    @Autowired
    private KycRepository kycRepository;

    @Autowired
    private AsyncTaskHandler asyncTaskHandler;

    @Autowired
    private KycRedisRepository kycRedisRepository;

    public Kyc submitKyc(Kyc kyc) {
        KycData kycData = KycData.builder()
                .userId(kyc.getUserId())
                .email(kyc.getEmail())
                .address(kyc.getAddress())
                .pan(kyc.getPan())
                .aadhaarNumber(kyc.getAadhaarNumber())
                .bankName(kyc.getBankName())
                .branchName(kyc.getBranchName())
                .accountHolderName(kyc.getAccountHolderName())
                .accountNumber(kyc.getAccountNumber())
                .ifscCode(kyc.getIfscCode())
                .build();
        Kyc savedKyc = kycRepository.save(kyc);
        kycData.setId(savedKyc.getId());

        // Checking in Cache & Updating if already present
        if(kycRedisRepository.exists(kyc.getUserId()))
               kycRedisRepository.save(kyc);

        // Calling Kafka Microservie to Perform Checks like Pan , Aadhaar , AML , Bank Account , CIBIL & EXPERIAN Score
        asyncTaskHandler.sendAsyncTask(kycData);

        return savedKyc;
    }


    public Kyc updateKycStatus(KycUpdateRequest kycUpdateRequest) {
        Optional<Kyc> kycData = kycRepository.findById(kycUpdateRequest.getId());
        if (kycData.isEmpty()) {
            throw new KycNotFoundException("KYC Data Not Found for this User");
        }
        Kyc kyc = kycData.get();
        kyc.setNotInvolvedInAntiMoneyLaunderingCheck(kycUpdateRequest.isNotInvolvedInAntiMoneyLaunderingCheck());
        kyc.setPanValid(kycUpdateRequest.isPanValid());
        kyc.setBankAccountValid(kycUpdateRequest.isBankAccountValid());
        kyc.setCibilScore(kycUpdateRequest.getCibilScore());
        kyc.setAadhaarValid(kycUpdateRequest.isAadhaarValid());
        kyc.setExperianScore(kycUpdateRequest.getExperianScore());

        Kyc savedData = kycRepository.save(kyc);

        // Checking in Cache & Updating if already present
        if(kycRedisRepository.exists(kyc.getUserId()))
            kycRedisRepository.save(kyc);

        return savedData;
    }

    public Kyc getKycByUserId(Long userId) {
        Kyc kyc = kycRedisRepository.findByUserId(userId);
        if (kyc == null) {
            System.out.println("Cache Miss");
            kyc = kycRepository.findByUserId(userId);
            if (kyc != null) {
                kycRedisRepository.save(kyc);
            }
        }else
            System.out.println("Cache Hit");
        return kyc;
    }
}
