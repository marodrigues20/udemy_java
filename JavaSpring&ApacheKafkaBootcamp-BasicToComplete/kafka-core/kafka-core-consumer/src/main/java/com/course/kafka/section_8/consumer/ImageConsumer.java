package com.course.kafka.section_8.consumer;

import com.course.kafka.section_8.entity.Image;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * Section 08: 43. Retrying Consumer
 */
@Service
public class ImageConsumer {

    private static Logger LOG = LoggerFactory.getLogger(ImageConsumer.class);

    @Autowired
    private ObjectMapper objectMapper;

    @KafkaListener(topics = "t-image", containerFactory = "imageRetryContainerFactory", concurrency = "2")
    public void consumer(ConsumerRecord<String, String> consumerRecord) throws JsonProcessingException {
        var image = objectMapper.readValue(consumerRecord.value(), Image.class);

        if(image.getType().equalsIgnoreCase("svg")){
            LOG.warn("Throwing exception on partition {} for image {}", consumerRecord.partition(), image);
            throw new IllegalArgumentException("Simulate API call failed");
        }

        LOG.info("Processing on partition {} for image {}", consumerRecord.partition(), image);
    }
}
