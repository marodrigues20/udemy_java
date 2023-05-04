package com.course.kafka.broker.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.course.kafka.broker.message.CustomerPurchaseMobileMessage;
import com.course.kafka.broker.message.CustomerPurchaseWebMessage;

/**
 * Section 15: 92. Web & Mobile
 */
@Service
public class CustomerPurchaseProducer {

	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;

	public void publishPurchaseMobile(CustomerPurchaseMobileMessage message) {
		kafkaTemplate.send("t-commodity-customer-purchase-mobile", message.getPurchaseNumber(), message);
	}

	public void publishPurchaseWeb(CustomerPurchaseWebMessage message) {
		kafkaTemplate.send("t-commodity-customer-purchase-web", message.getPurchaseNumber(), message);
	}

}
