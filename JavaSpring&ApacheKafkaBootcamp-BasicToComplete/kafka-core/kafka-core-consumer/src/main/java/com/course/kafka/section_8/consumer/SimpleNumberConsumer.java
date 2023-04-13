package com.course.kafka.section_8.consumer;

import com.course.kafka.section_8.entity.SimpleNumber;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * Section 08: 42. Global Error Handler
 */
@Service
public class SimpleNumberConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(SimpleNumberConsumer.class);

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = "t-simple-number")
    public void consume(String message) throws JsonProcessingException {
        var simpleNumber = objectMapper.readValue(message, SimpleNumber.class);

        if(simpleNumber.getNumber() % 2 != 0){
            throw new IllegalArgumentException("Odd number : " + simpleNumber.getNumber());
        }

        LOG.info("Processing simpleNumber : {}", simpleNumber);
    }
}
