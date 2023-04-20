package com.course.kafka.broker.serde;

import com.course.kafka.broker.message.PromotionMessage;

/**
 * Section 12. 72. Custom JSON Serde
 */
public class PromotionSerde extends CustomJsonSerde<PromotionMessage>{

    public PromotionSerde( ) {
        super(new CustomJsonSerializer<PromotionMessage>(), new CustomJsonDeserializer<PromotionMessage>(PromotionMessage.class));
    }
}
