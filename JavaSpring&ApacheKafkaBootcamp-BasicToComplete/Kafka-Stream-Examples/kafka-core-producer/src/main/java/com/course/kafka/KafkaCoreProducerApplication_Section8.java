package com.course.kafka;

import com.course.kafka.section_8.entity.FoodOrder;
import com.course.kafka.section_8.producer.FoodOrderProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class KafkaCoreProducerApplication_Section8 implements CommandLineRunner {

	@Autowired
	private FoodOrderProducer producer;

	public static void main(String[] args) {
		SpringApplication.run(KafkaCoreProducerApplication_Section8.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		var chickenOrder = new FoodOrder(3, "Chicken");
		var fishOrder = new FoodOrder(3, "Fish");
		var pizzaOrder = new FoodOrder(3, "Pizza");

		producer.send(chickenOrder);
		producer.send(fishOrder);
		producer.send(pizzaOrder);
	}
}
