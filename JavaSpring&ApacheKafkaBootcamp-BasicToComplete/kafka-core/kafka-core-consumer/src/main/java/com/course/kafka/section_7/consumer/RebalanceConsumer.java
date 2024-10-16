package com.course.kafka.section_7.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Section 7: 36. Rebalance
 */
@Service
public class RebalanceConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(RebalanceConsumer.class);

    /**
     * If we have just one partition. 2 concurrent listener will be idle since we only have one partition.
     * @param consumerRecord
     */
    //@KafkaListener(topics = "t-rebalance", concurrency = "3")
    public void consume(ConsumerRecord<String, String> consumerRecord) {
        LOG.info("Partition : {}, Offset : {}, Message : {}", consumerRecord.partition(), consumerRecord.offset(), consumerRecord.value());
    }
}
