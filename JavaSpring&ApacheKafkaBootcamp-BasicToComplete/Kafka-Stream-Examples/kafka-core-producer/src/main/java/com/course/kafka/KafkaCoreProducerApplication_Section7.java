package com.course.kafka;

import com.course.kafka.section_7.entity.PaymentRequest;
import com.course.kafka.section_7.producer.PaymentRequestProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class KafkaCoreProducerApplication_Section7 implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(KafkaCoreProducerApplication_Section7.class, args);
	}

	//@Autowired //Section 07: 39. Idempotency - Handle Duplicate Message
	//private PurchaseRequestProducer producer;

	@Autowired
	private PaymentRequestProducer producer;

	// Section 07: 40. Idempotency Alternative
	@Override
	public void run(String... args) throws Exception {
		var paymentRequestAlpha_Transaction1 = new PaymentRequest("Pay-Alpha", 551, "USD", "Notes alpha", "Budget reserve");
		var paymentRequestAlpha_Transaction2 = new PaymentRequest("Pay-Alpha", 551, "USD", "Notes alpha", "Approval workflow");
		var paymentRequestAlpha_Transaction3 = new PaymentRequest("Pay-Alpha", 551, "USD", "Notes alpha", "Push notification");

		var paymentRequestBeta_Transaction1 = new PaymentRequest("Pay-Beta", 552, "USD", "Notes Beta", "Budget reserve");
		var paymentRequestBeta_Transaction2 = new PaymentRequest("Pay-Beta", 552, "USD", "Notes Beta", "Approval workflow");
		var paymentRequestBeta_Transaction3 = new PaymentRequest("Pay-Beta", 552, "USD", "Notes Beta", "Push notification");

		producer.send(paymentRequestAlpha_Transaction1);
		producer.send(paymentRequestAlpha_Transaction2);
		producer.send(paymentRequestAlpha_Transaction3);

		producer.send(paymentRequestBeta_Transaction1);
		producer.send(paymentRequestBeta_Transaction2);
		producer.send(paymentRequestBeta_Transaction3);

		producer.send(paymentRequestAlpha_Transaction2);
		producer.send(paymentRequestBeta_Transaction3);
	}

	/*
	//Section 07: 39. Idempotency - Handle Duplicate Message
	@Override
	public void run(String... args) throws Exception {
		var pr1 = new PurchaseRequest(5551, "PR-First", 991, "USD");
		var pr2 = new PurchaseRequest(5552, "PR-Second", 992, "USD");
		var pr3 = new PurchaseRequest(5553, "PR-Third", 993, "USD");

		producer.send(pr1);
		producer.send(pr2);
		producer.send(pr3);

		producer.send(pr1);

	}*/

}
