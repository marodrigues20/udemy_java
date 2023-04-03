package com.course.kafka.section_7.consumer;

import com.course.kafka.section_7.entity.Commodity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Section 7: 35. Consuming with Consumer Groups - Create Consumer
 */
@Service
public class CommodityDashboardConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(CommodityDashboardConsumer.class);
    @Autowired
    private ObjectMapper objectMapper;

    /**
     * If you define group-id in @KafkaListener, the group id will override default value on application.yml file.
     * @param message
     * @throws JsonProcessingException
     */
    //@KafkaListener(topics= "t-commodity", groupId = "cg-dashboard")
    public void consume(String message) throws JsonProcessingException {
        var commodity = objectMapper.readValue(message, Commodity.class);
        LOG.info("Dashboard logic for: {}", commodity);
    }
}
