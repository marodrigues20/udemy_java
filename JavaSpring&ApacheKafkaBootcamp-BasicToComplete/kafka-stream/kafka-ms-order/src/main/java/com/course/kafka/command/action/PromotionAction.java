package com.course.kafka.command.action;

import com.course.kafka.api.request.PromotionRequest;
import com.course.kafka.broker.message.PromotionMessage;
import com.course.kafka.broker.producer.PromotionProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Section 11: 58. Pattern App - Promotion Publisher
 */
@Component
public class PromotionAction {

    @Autowired
    private PromotionProducer producer;

    public void publishToKafka(PromotionRequest request){
        var message = new PromotionMessage(request.getPromotionCode());
        producer.publish(message);
    }
}
