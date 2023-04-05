package com.course.kafka.section_8.consumer;

import com.course.kafka.section_8.entity.Invoice;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * Section 8: 44. Dead Letter Topic
 */
@Service
public class InvoiceConsumer {
    private static final Logger LOG = LoggerFactory.getLogger(InvoiceConsumer.class);

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = "t-invoice", concurrency = "2", containerFactory = "invoiceDltContainerFactory")
    public void consume(String message) throws JsonProcessingException {
        var invoice = objectMapper.readValue(message, Invoice.class);

        if(invoice.getAmount() < 1){
            throw new IllegalArgumentException("Invalid amount for " + invoice);
        }

        LOG.info("Processing invoice: {}", invoice);
    }

}
