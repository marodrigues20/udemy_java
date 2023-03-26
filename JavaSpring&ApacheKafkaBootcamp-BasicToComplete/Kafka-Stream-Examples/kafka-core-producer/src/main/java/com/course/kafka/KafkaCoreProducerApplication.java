package com.course.kafka;

import com.course.kafka.entity.Employee;
import com.course.kafka.entity.PurchaseRequest;
import com.course.kafka.producer.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

@SpringBootApplication
@EnableScheduling
public class KafkaCoreProducerApplication implements CommandLineRunner {

	//@Autowired
	//private KafkaKeyProducer producer;

	//private EmployeeJsonProducer producer;

	//private Employee2JsonProducer producer;

	public static void main(String[] args) {
		SpringApplication.run(KafkaCoreProducerApplication.class, args);
	}

	@Autowired
	private PurchaseRequestProducer producer;

	@Override
	public void run(String... args) throws Exception {
		var pr1 = new PurchaseRequest(5551, "PR-First", 991, "USD");
		var pr2 = new PurchaseRequest(5552, "PR-Second", 992, "USD");
		var pr3 = new PurchaseRequest(5553, "PR-Third", 993, "USD");

		producer.send((pr1));
		producer.send((pr2));
		producer.send((pr3));

		producer.send((pr1));

	}

	/*@Override
	public void run(String... args) throws Exception {
		/*for( int i = 0; i < 10_000; i++){
			var key = "key-" + (i % 4);
			var value = "value " + i + " with key " + key;
			producer.send(key, value);
		}*/

		/*for(int i = 0; i < 5; i ++){
			var emp = new Employee("emp-" + i, "Employee " + i, LocalDate.now());
			producer.sendMessage(emp);
		}*/
	//}
}
