package com.fintech.eWallet.kakfa;

import com.fintech.eWallet.dto.NotificationRequest;
import com.fintech.eWallet.dto.KycData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String fundsTransferTopic;
    private final String kycTopic;

    public KafkaProducerService(KafkaTemplate<String, Object> kafkaTemplate,
                                @Value("${topics.fundsTransfer}") String fundsTransferTopic,
                                @Value("${topics.kyc}") String kycTopic) {
        this.kafkaTemplate = kafkaTemplate;
        this.fundsTransferTopic = fundsTransferTopic;
        this.kycTopic = kycTopic;
    }

    public void sendFundsTransfer(NotificationRequest fundsTransfer) {
        kafkaTemplate.send(fundsTransferTopic, fundsTransfer);
    }

    public void sendAsyncTask(KycData kyc) {
        kafkaTemplate.send(kycTopic, kyc);
    }
}