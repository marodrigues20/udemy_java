package com.course.kafka.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

@Configuration
public class KafkaConfig {

    @Autowired
    private KafkaProperties kafkaProperties;

    public ConsumerFactory<Object, Object> consumerFactory(){
        // We can add or modify variable properties for kafka configuration
        var properties = kafkaProperties.buildConsumerProperties();

        //For rebalancing. Indicates ms refresh rate. So If I want to adjust refresh rate to shorter interval,
        //says 2 minutes, which is 120 seconds.
        properties.put(ProducerConfig.METADATA_MAX_AGE_CONFIG, "120000");

        return new DefaultKafkaConsumerFactory<Object,Object>(properties);
    }
}
