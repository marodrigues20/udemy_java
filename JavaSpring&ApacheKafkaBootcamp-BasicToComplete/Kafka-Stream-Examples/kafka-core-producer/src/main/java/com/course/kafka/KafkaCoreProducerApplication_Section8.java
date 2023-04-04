package com.course.kafka;

import com.course.kafka.section_8.entity.FoodOrder;
import com.course.kafka.section_8.entity.SimpleNumber;
import com.course.kafka.section_8.producer.FoodOrderProducer;
import com.course.kafka.section_8.producer.SimpleNumberProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class KafkaCoreProducerApplication_Section8 implements CommandLineRunner {

	@Autowired
	private FoodOrderProducer foodOrderProducer;
	@Autowired
	private SimpleNumberProducer simpleNumberProducer;

	public static void main(String[] args) {
		SpringApplication.run(KafkaCoreProducerApplication_Section8.class, args);
	}



	@Override
	public void run(String... args) throws Exception {
		var chickenOrder = new FoodOrder(3, "Chicken");
		var fishOrder = new FoodOrder(10, "Fish");
		var pizzaOrder = new FoodOrder(5, "Pizza");

		foodOrderProducer.send(chickenOrder);
		foodOrderProducer.send(fishOrder);
		foodOrderProducer.send(pizzaOrder);

		for (int i = 100; i < 103; i++){
			var simpleNumber = new SimpleNumber(i);
			simpleNumberProducer.send(simpleNumber);
		}
	}
}
