package com.course.kafka.section_7.consumer;

import com.course.kafka.section_7.entity.CarLocation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Section 07: 38. Message Filter
 */
@Service
public class CarLocationConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(CarLocationConsumer.class);

    @Autowired
    private ObjectMapper objectMapper;

    //@KafkaListener(topics="t-location", groupId = "cg-all-location")
    public void listenAll(String message) throws JsonProcessingException {
        var carLocation = objectMapper.readValue(message, CarLocation.class);
        LOG.info("listenAll : {}", carLocation);
    }

    //@KafkaListener(topics="t-location", groupId = "cg-far-location", containerFactory = "farLocationContainerFactory")
    public void listenFar(String message) throws JsonProcessingException {
        var carLocation = objectMapper.readValue(message, CarLocation.class);
        LOG.info("listenFar : {}", carLocation);
    }
}
