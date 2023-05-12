package com.course.kafka.broker.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.course.kafka.broker.message.FlashSaleVoteMessage;

/**
 * Section 16. 94. Most Recent Data Feed
 */
@Service
public class FlashSaleVoteProducer {

	@Autowired
	private KafkaTemplate<String, FlashSaleVoteMessage> kafkaTemplate;

	public void publish(FlashSaleVoteMessage message) {
		kafkaTemplate.send("t-commodity-flashsale-vote", message);
	}

}
