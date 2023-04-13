package com.course.kafka.section_8.consumer;

import com.course.kafka.section_8.entity.Image;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.TopicSuffixingStrategy;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;

/**
 * Section 08: 45. Non Blocking Retry
 */
@Service
public class Image2Consumer {

    private static Logger LOG = LoggerFactory.getLogger(Image2Consumer.class);

    @Autowired
    private ObjectMapper objectMapper;

    @RetryableTopic(autoCreateTopics = "true", attempts = "4",
    topicSuffixingStrategy = TopicSuffixingStrategy.SUFFIX_WITH_DELAY_VALUE,
    backoff = @Backoff(delay = 3000, maxDelay = 10_000, multiplier = 1.5, random = true),
    dltTopicSuffix = "-dead")
    @KafkaListener(topics = "t-image-2", concurrency = "2")
    public void consumer(ConsumerRecord<String, String> consumerRecord) throws JsonProcessingException {
        var image = objectMapper.readValue(consumerRecord.value(), Image.class);

        if(image.getType().equalsIgnoreCase("svg")){
            LOG.warn("Throwing exception on partition {} for image {}", consumerRecord.partition(), image);
            throw new IllegalArgumentException("Simulate API call failed");
        }

        LOG.info("Processing on partition {} for image {}", consumerRecord.partition(), image);
    }
}
