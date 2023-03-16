package com.course.kafka.consumer;

import com.course.kafka.entity.Commodity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * Section 7: 35. Consuming with Consumer Groups - Create Consumer
 */
@Service
public class CommodityNotificationConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(CommodityNotificationConsumer.class);
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * If you define group-id in @KafkaListener, the group id will override default value on application.yml file.
     * @param message
     * @throws JsonProcessingException
     */
    @KafkaListener(topics= "t-commodity", groupId = "cg-notification")
    public void consume(String message) throws JsonProcessingException, InterruptedException {
        var commodity = objectMapper.readValue(message, Commodity.class);

        var randomDelayMillis = ThreadLocalRandom.current().nextLong(500, 2000);
        TimeUnit.MILLISECONDS.sleep(randomDelayMillis);

        LOG.info("Notification logic for: {}", commodity);
    }
}
