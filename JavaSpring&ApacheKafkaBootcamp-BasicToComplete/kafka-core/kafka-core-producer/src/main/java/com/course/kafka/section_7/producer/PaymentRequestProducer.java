package com.course.kafka.section_7.producer;


import com.course.kafka.section_7.entity.PaymentRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Section 07: 40. Idempotency Alternative
 */
@Service
public class PaymentRequestProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    public void send(PaymentRequest paymentRequest) throws JsonProcessingException {
        var json = objectMapper.writeValueAsString(paymentRequest);
        kafkaTemplate.send("t-payment-request", paymentRequest.getPaymentNumber(), json);
    }


}
