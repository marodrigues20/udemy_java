package com.course.kafka.section_7.config;

import com.course.kafka.section_7.entity.CarLocation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.adapter.RecordFilterStrategy;

/**
 * Section 07: 37. Kafka Configuration
 * Section 07: 38. Message Filter
 */
@Configuration
public class KafkaConfig {

    @Autowired
    private KafkaProperties kafkaProperties;

    @Autowired
    private ObjectMapper objectMapper;

    public ConsumerFactory<Object, Object> consumerFactory(){
        // We can add or modify variable properties for kafka configuration
        var properties = kafkaProperties.buildConsumerProperties();

        //For rebalancing. Indicates ms refresh rate. So If I want to adjust refresh rate to shorter interval,
        //says 2 minutes, which is 120 seconds.
        properties.put(ProducerConfig.METADATA_MAX_AGE_CONFIG, "120000");

        return new DefaultKafkaConsumerFactory<Object,Object>(properties);
    }

    @Bean(name = "farLocationContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<Object, Object> farLocationContainerFactory(
            ConcurrentKafkaListenerContainerFactoryConfigurer configurer ){
        var factory = new ConcurrentKafkaListenerContainerFactory<Object, Object>();
        configurer.configure(factory, consumerFactory());
        factory.setRecordFilterStrategy(new RecordFilterStrategy<Object, Object>() {
            @Override
            public boolean filter(ConsumerRecord<Object, Object> consumerRecord) {
                try {
                    CarLocation carLocation = objectMapper.readValue(consumerRecord.value().toString(), CarLocation.class);
                    return carLocation.getDistance() <= 100;
                } catch (JsonProcessingException e) {
                    return false;
                }
            }
        });

        return factory;
    }
}
