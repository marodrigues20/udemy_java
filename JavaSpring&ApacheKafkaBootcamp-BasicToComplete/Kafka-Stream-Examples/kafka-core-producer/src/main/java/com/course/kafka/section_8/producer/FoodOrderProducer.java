package com.course.kafka.section_8.producer;

import com.course.kafka.section_8.entity.FoodOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class FoodOrderProducer {

    private KafkaTemplate<String, String> kafkaTemplate;

    private ObjectMapper objectMapper;

    public void send(FoodOrder foodOrder) throws JsonProcessingException {
        var json = objectMapper.writeValueAsString(foodOrder);
        kafkaTemplate.send("t-food-order", json);
    }


}
