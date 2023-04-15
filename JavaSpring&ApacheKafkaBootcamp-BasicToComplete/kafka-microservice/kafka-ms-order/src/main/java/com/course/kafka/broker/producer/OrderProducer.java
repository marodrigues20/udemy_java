package com.course.kafka.broker.producer;

import com.course.kafka.broker.message.OrderMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * Section 11: 53. Order App - Kafka Producer
 */
@Service
public class OrderProducer {

    private static final Logger LOG = LoggerFactory.getLogger(OrderProducer.class);

    @Autowired
    private KafkaTemplate<String, OrderMessage> kafkaTemplate;

    public void publish(OrderMessage message){

        // This method will run asynchronously
        kafkaTemplate.send("t-commodity-order", message.getOrderNumber(), message)
                .addCallback(new ListenableFutureCallback<SendResult<String, OrderMessage>>() {
            @Override
            public void onFailure(Throwable ex) {
                LOG.info("Order {}, item {} failed to publish because {}", message.getOrderNumber(),
                        message.getItemName(), ex.getCause());
            }

            @Override
            public void onSuccess(SendResult<String, OrderMessage> result) {

            }
        });

        LOG.info("Just a dummy message for order {}, item {}", message.getOrderLocation(), message.getItemName());
    }
}
