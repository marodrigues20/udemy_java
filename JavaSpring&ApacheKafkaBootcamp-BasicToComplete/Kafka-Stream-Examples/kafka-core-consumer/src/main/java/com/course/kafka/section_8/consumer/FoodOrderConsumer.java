package com.course.kafka.section_8.consumer;

import com.course.kafka.section_8.entity.FoodOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class FoodOrderConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(FoodOrderConsumer.class);

    private static final int MAX_ORDER_AMOUNT = 7;

    private ObjectMapper objectMapper;

    @KafkaListener(topics = "t-food-order", errorHandler = "myFoodOrderErrorHandler")
    public void consumer(String message) throws JsonProcessingException {
        var foodOrder = objectMapper.readValue(message, FoodOrder.class);

        if(foodOrder.getAmount() > MAX_ORDER_AMOUNT){
            throw new IllegalArgumentException("Order amount is too many : " + foodOrder.getAmount());
        }

        LOG.info("Processing food order: {}", foodOrder);
    }

}
