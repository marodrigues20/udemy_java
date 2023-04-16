package com.course.kafka.broker.producer;


import com.course.kafka.broker.message.PromotionMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

/**
 * Section 11: 58. Pattern App - Promotion Publisher
 */
@Service
public class PromotionProducer {

    private static final Logger LOG = LoggerFactory.getLogger(PromotionProducer.class);

    private KafkaTemplate<String, PromotionMessage> kafkaTemplate;

    /**
     * - Previously, we learn that the kafka template send method is asynchronous.
     * - What if we want to do synchronous send?
     * - It is possible, although I’m not recommend it.
     * - But just for your knowledge, we can do synchronous send and block until send result is received by using method get after send. Like this.
     * @param message
     */
    public void publish(PromotionMessage message){
        try {
            var sendResult = kafkaTemplate.send("t-commodity-promotion", message).get();
            LOG.info("Send result success for message {}", sendResult.getProducerRecord().value());
        } catch (InterruptedException | ExecutionException e){
            LOG.error("Error publishing {}, because {}", message, e.getMessage());
        }
    }

}
