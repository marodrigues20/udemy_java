package com.course.kafka.section_8.producer;

import com.course.kafka.section_8.entity.SimpleNumber;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Section 08: 42. Global Error Handler
 */
@Service
public class SimpleNumberProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    public void send(SimpleNumber simpleNumber) throws JsonProcessingException {
        var json = objectMapper.writeValueAsString(simpleNumber);
        kafkaTemplate.send("t-simple-number", json);
    }
}
