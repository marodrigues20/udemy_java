package com.course.kafka.section_8.producer;

import com.course.kafka.section_8.entity.Image;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Section 08. 43. Retrying Consumer
 */
@Service
public class ImageProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    @Autowired
    private ObjectMapper objectMapper;

    public void send(Image image, int partition) throws JsonProcessingException {
        var json = objectMapper.writeValueAsString(image);
        kafkaTemplate.send("t-image", partition, image.getType(), json);
    }


}
