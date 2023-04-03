package com.course.kafka;

import com.course.kafka.section_6.producer.KafkaKeyProducer;
import com.course.kafka.section_7.entity.Employee;
import com.course.kafka.section_7.entity.PaymentRequest;
import com.course.kafka.section_7.producer.EmployeeJsonProducer;
import com.course.kafka.section_7.producer.PaymentRequestProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDate;

@SpringBootApplication
@EnableScheduling
public class KafkaCoreProducerApplication_Section6 implements CommandLineRunner {

	@Autowired
	private KafkaKeyProducer producer;
	@Autowired
	private EmployeeJsonProducer producer2;

	public static void main(String[] args) {
		SpringApplication.run(KafkaCoreProducerApplication_Section6.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		for( int i = 0; i < 10_000; i++){
			var key = "key-" + (i % 4);
			var value = "value " + i + " with key " + key;
			producer.send(key, value);
		}

		for(int i = 0; i < 5; i ++){
			var emp = new Employee("emp-" + i, "Employee " + i, LocalDate.now());
			producer2.sendMessage(emp);
		}
	}
}
