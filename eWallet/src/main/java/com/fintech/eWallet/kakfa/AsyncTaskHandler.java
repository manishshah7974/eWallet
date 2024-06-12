package com.fintech.eWallet.kakfa;

import com.fintech.eWallet.dto.KycData;
import com.fintech.eWallet.utils.Logger.LoggerSingleton;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class AsyncTaskHandler {

    private final KafkaProducerService producerService;
    private final Logger logger = LoggerSingleton.getInstance().getLogger();

    public AsyncTaskHandler(KafkaProducerService producerService) {
        this.producerService = producerService;
    }

    public void sendAsyncTask(KycData kyc) {
        producerService.sendAsyncTask(kyc);
        logger.info("KYC details sent for Verification: " + kyc);
    }
}
