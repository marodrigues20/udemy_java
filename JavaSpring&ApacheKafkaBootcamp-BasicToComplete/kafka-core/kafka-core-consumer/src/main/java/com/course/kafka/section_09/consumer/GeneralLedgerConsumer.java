package com.course.kafka.section_09.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * Section 09. 46. Scheduling Consumer
 */
@Service
public class GeneralLedgerConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(GeneralLedgerConsumer.class);

    @KafkaListener(id = "general-ledger.one", topics = "t-general-ledger")
    public void consumeOne(String message){
        LOG.info("From consumeOne: {}", message);
    }

    @KafkaListener(topics = "t-general-ledger")
    public void consumeTwo(String message){
        LOG.info("From consumeTwo: {}", message);
    }

}
