package com.course.kafka.section_7.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Section 7: 36. Rebalance
 */
@Service
public class RebalanceProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private AtomicInteger counter = new AtomicInteger();

    @Scheduled(fixedDelay = 1000)
    public void sendMessage(){
        kafkaTemplate.send("t-rebalance", "Counter" + counter.incrementAndGet());
    }
}
