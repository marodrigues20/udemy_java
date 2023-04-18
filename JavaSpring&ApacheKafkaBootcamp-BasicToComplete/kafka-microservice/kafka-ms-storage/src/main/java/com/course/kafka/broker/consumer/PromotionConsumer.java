package com.course.kafka.broker.consumer;


import com.course.kafka.broker.messages.PromotionMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * Section 11: 60. Storage App - Kafka Consumer
 */
@Service
@KafkaListener(topics = "t-commodity-promotion")
public class PromotionConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(PromotionConsumer.class);

    @KafkaHandler
    public void listenPromotion(PromotionMessage message){
        LOG.info("Processing Promotion: {}", message);
    }

    @KafkaHandler
    public void listenDiscount(PromotionMessage message){
        LOG.info("Processing Discount: {}", message);
    }


}
