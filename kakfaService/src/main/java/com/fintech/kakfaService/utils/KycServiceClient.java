package com.fintech.kakfaService.utils;

import com.fintech.eWallet.dto.KycData;
import com.fintech.kakfaService.dto.KycUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class KycServiceClient {

    @Autowired
    private RestTemplate restTemplate;

    public ResponseEntity<KycData> updateKycStatus(KycUpdateRequest updateRequest) {

        String url = "http://localhost:8080/kyc/updateKycStatus";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");


        HttpEntity<KycUpdateRequest> requestEntity = new HttpEntity<>(updateRequest, headers);

        // Make the HTTP PUT request
        ResponseEntity<KycData> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.PUT,
                requestEntity,
                KycData.class);

        return responseEntity;
    }
}
