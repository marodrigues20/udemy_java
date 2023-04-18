package com.course.kafka.broker.consumer;

import com.course.kafka.broker.message.OrderMessage;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * Section 11: 62. Reward App - Kafka Consumer
 */
@Service
public class OrderConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(OrderConsumer.class);

    public void listen(ConsumerRecord<String, OrderMessage> consumerRecord){

        var headers = consumerRecord.headers();
        var orderMessage = consumerRecord.value();

        LOG.info("Processing order {}, item {}, credit card number{}", orderMessage.getOrderNumber(),
                orderMessage.getItemName(), orderMessage.getCreditCardNumber());

        LOG.info(" Headers : ");
        headers.forEach(h -> LOG.info(" key : {}, value: {}", h.key(), new String(h.value())));

        var headerValues = ObjectUtils.isEmpty(headers.lastHeader("surpriseBonus").value()) ? "0" :
                new String(headers.lastHeader("surpriseBonus").value());

        var bonusPercentage = Integer.parseInt(headerValues);
        var bonusAmount = ((bonusPercentage / 100) * orderMessage.getPrice() * orderMessage.getQuantity());

        LOG.info("Bonus amount is {}", bonusAmount);
    }


}
