package com.course.kafka.broker.producer;

import com.course.kafka.broker.message.OrderMessage;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.ArrayList;

/**
 * Section 11: 53. Order App - Kafka Producer
 * Section 11: 61. Order App - Add Header to Message
 */
@Service
public class OrderProducer {

    private static final Logger LOG = LoggerFactory.getLogger(OrderProducer.class);

    @Autowired
    private KafkaTemplate<String, OrderMessage> kafkaTemplate;

    public void publish(OrderMessage message) {

        //Section 11: 61. Order App - Add Header to Message
        var producerRecord = buildProducerRecord(message);

        // This method will run asynchronously
        //Section 11: 53. Order App - Kafka Producer
        //kafkaTemplate.send("t-commodity-order", message.getOrderNumber(), message)

        //Section 11: 61. Order App - Add Header to Message
        kafkaTemplate.send(producerRecord)
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

    /**
     * Section 11: 61. Order App - Add Header to Message
     * @param message
     * @return
     */
    private ProducerRecord<String, OrderMessage> buildProducerRecord(OrderMessage message) {
        var supriseBonus = StringUtils.startsWithIgnoreCase(message.getOrderLocation(), "A") ? 25 : 15;
        var headers = new ArrayList<Header>();
        var surpriseBonusHeader = new RecordHeader("surpriseBonus", Integer.toString(supriseBonus).getBytes());

        headers.add(surpriseBonusHeader);

        return new ProducerRecord<String, OrderMessage>("t-commodity-order", null,
                message.getOrderNumber(), message, headers);


    }
}
