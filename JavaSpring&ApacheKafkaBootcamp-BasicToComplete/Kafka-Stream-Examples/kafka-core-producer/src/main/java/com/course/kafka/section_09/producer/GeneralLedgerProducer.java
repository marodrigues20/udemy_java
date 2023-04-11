package com.course.kafka.section_09.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Section 09. 46. Scheduling Consumer
 */
@Service
public class GeneralLedgerProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private AtomicInteger counter = new AtomicInteger();

    public void send(String message){
        kafkaTemplate.send("t-general-ledger", message);
    }

    @Scheduled(fixedRate = 1000) //Send message every second
    public void schedule(){
        var message = "Ledger" + counter.incrementAndGet();
        send(message);
    }
}
