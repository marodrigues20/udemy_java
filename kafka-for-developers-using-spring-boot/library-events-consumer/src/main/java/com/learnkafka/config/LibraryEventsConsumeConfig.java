package com.learnkafka.config;


import com.learnkafka.service.FailureService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.ConsumerRecordRecoverer;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.ExponentialBackOffWithMaxRetries;
import org.springframework.util.backoff.FixedBackOff;

import java.util.List;

@Configuration
@EnableKafka
@Slf4j
public class LibraryEventsConsumeConfig {

    public static final String RETRY = "RETRY";
    public static final String DEAD = "DEAD";
    public static final String SUCCESS = "SUCCESS";

    @Autowired
    KafkaTemplate kafkaTemplate;

    @Autowired
    FailureService failureService;

    @Value("${topics.retry}")
    private String retryTopic;

    @Value("${topics.dlt}")
    private String deadLetterTopic;

    //This class add a lot of header. See more in the documentation.
    public DeadLetterPublishingRecoverer publishingRecoverer() {
        DeadLetterPublishingRecoverer recoverer = new DeadLetterPublishingRecoverer(kafkaTemplate,
                (r, e) -> {
                    log.error("Exception in publishingRecoverer: {} ", e.getMessage(), e);
                    if (e.getCause() instanceof RecoverableDataAccessException) {
                        return new TopicPartition(retryTopic, r.partition());
                    } else {
                        return new TopicPartition(deadLetterTopic, r.partition());
                    }
                });
        return recoverer;
    }

    ConsumerRecordRecoverer consumerRecordRecoverer = (consumerRecord, e) -> {
        log.error("Exception in consumerRecordRecoverer : {} ", e.getMessage(), e);
        var record = (ConsumerRecord<Integer, String>)consumerRecord;
        if (e.getCause() instanceof RecoverableDataAccessException) {
            //recovery logic
            log.info("Inside Recovery");
            failureService.saveFailedRecord(record, e, RETRY);
        }
        else {
            // non-recovery logic
            log.info("Inside Non-Recovery");
            failureService.saveFailedRecord(record, e, DEAD);
        }
    };


    public DefaultErrorHandler errorHandler() {

        // List of Exceptions that I want to trigger retry.
        var exceptionsToIgnoreList = List.of(
                IllegalArgumentException.class
        );

        // List of Exceptions that I want to trigger retry.
        /*var exceptionsToRetryList = List.of(
                RecoverableDataAccessException.class
        );*/

        var fixedBackOff = new FixedBackOff(1000L, 2);

        var expBackOff = new ExponentialBackOffWithMaxRetries(2);
        expBackOff.setMaxInterval(1_000L); // First delay will be 1 second.
        expBackOff.setMultiplier(2.0); // Next time will be 2 times the value above.
        expBackOff.setMaxInterval(2); // The roof of interval will be 2 seconds. Just to match the FixedBackOff class.

        var errorHandler = new DefaultErrorHandler(
                //publishingRecoverer()
                consumerRecordRecoverer
                ,
                // fixedBackOff
                expBackOff
        );

        // Add exceptions that retry will ignore. Retry won't be invoked.
        exceptionsToIgnoreList.forEach(errorHandler::addNotRetryableExceptions);
        //exceptionsToRetryList.forEach(errorHandler::addRetryableExceptions);

        //Provide additional information when you are debugging. Not use in production on regular basis.
        errorHandler
                .setRetryListeners(((record, ex, deliveryAttempt) -> {
                    log.info("Failed Record in Retry Listener, Exception : {} , deliveryAttempt: {} ",
                            ex.getMessage(), deliveryAttempt);
                }));

        return errorHandler;
    }


    // Method added initially to implement Manual mode to commit offset.
    // However, we are using in our application Bath mode where we are using this method to set Common Error Handler
    @Bean
    ConcurrentKafkaListenerContainerFactory<?, ?> kafkaListenerContainerFactory(
            ConcurrentKafkaListenerContainerFactoryConfigurer configurer,
            ConsumerFactory<Object, Object> kafkaConsumerFactory) {
        ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        configurer.configure(factory, kafkaConsumerFactory);
        // Just used when we are not using cloud. It's going to spin up 3 threads of the kafka listener.
        // This property create 3 threads to scale our application. Each thread will be run over app instance.
        factory.setConcurrency(3);
        //This code will retry 2 times given 1s intervals between retry
        factory.setCommonErrorHandler(errorHandler());
        //factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL);
        return factory;
    }


}

