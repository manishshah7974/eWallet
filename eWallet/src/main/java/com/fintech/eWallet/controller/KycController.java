package com.fintech.eWallet.controller;

import com.fintech.eWallet.dto.KycUpdateRequest;
import com.fintech.eWallet.model.Kyc;
import com.fintech.eWallet.service.KycService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("kyc")
public class KycController {

    @Autowired
    private KycService kycService;

    @PostMapping("/submit")
    public ResponseEntity<Kyc> submitKyc(@Valid @RequestBody Kyc kyc) {
        Kyc savedKyc = kycService.submitKyc(kyc);
        return ResponseEntity.ok(savedKyc);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Kyc> getKycByUserId(@PathVariable Long userId) {

        Kyc userKycData = kycService.getKycByUserId(userId);
        return ResponseEntity.ok(userKycData);
    }

    @PutMapping("updateKycStatus")
    public ResponseEntity<Kyc> updateKyc(@RequestBody KycUpdateRequest updateRequest) {
        Kyc updatedKyc = kycService.updateKycStatus(updateRequest);
        return ResponseEntity.ok(updatedKyc);
    }
}
