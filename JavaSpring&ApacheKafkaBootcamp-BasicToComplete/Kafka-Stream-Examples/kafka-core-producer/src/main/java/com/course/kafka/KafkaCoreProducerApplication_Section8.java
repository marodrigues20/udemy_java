package com.course.kafka;

import com.course.kafka.section_8.entity.FoodOrder;
import com.course.kafka.section_8.entity.SimpleNumber;
import com.course.kafka.section_8.producer.*;
import com.course.kafka.section_8.service.ImageService;
import com.course.kafka.section_8.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class KafkaCoreProducerApplication_Section8 implements CommandLineRunner {

	@Autowired //41. KafkaListener Error Handler
	private FoodOrderProducer foodOrderProducer;
	@Autowired //42. Global Error Handler
	private SimpleNumberProducer simpleNumberProducer;
	@Autowired //43. Retrying Consumer
	private ImageService imageService;
	@Autowired //43. Retrying Consumer
	private ImageProducer imageProducer;
	@Autowired //44. Dead Letter Topic
	private InvoiceService invoiceService;
	@Autowired //44. Dead Letter Topic
	private InvoiceProducer invoiceProducer;
	@Autowired //45. Non Blocking Retry
	private Image2Producer image2Producer;

	public static void main(String[] args) {
		SpringApplication.run(KafkaCoreProducerApplication_Section8.class, args);
	}



	@Override //45. Non Blocking Retry
	public void run(String... args) throws Exception {
		var image1 = imageService.generateImage("jpg");
		var image2 = imageService.generateImage("svg");
		var image3 = imageService.generateImage("gif");
		var image4 = imageService.generateImage("gif");
		var image5 = imageService.generateImage("gif");
		var image6 = imageService.generateImage("gif");

		image2Producer.send(image1, 0);
		image2Producer.send(image2, 0);
		image2Producer.send(image3, 0);
		image2Producer.send(image4, 1);
		image2Producer.send(image5, 1);
		image2Producer.send(image6, 1);

	}

	/*@Override //44. Dead Letter Topic
	public void run(String... args) throws Exception {
		for (int i = 0; i < 10; i++) {
			var invoice = invoiceService.generateInvoice();
			if(i > 5){
				invoice.setAmount(0);
			}
			invoiceProducer.send(invoice);
		}
	}*/


	/*@Override //43. Retrying Consumer
	public void run(String... args) throws Exception {
		var image1 = imageService.generateImage("jpg");
		var image2 = imageService.generateImage("svg");
		var image3 = imageService.generateImage("png");
		var image4 = imageService.generateImage("gif");
		var image5 = imageService.generateImage("bmp");
		var image6 = imageService.generateImage("tiff");

		imageProducer.send(image1,0);
		imageProducer.send(image2,0);
		imageProducer.send(image3,0);
		imageProducer.send(image4,1);
		imageProducer.send(image5,1);
		imageProducer.send(image6,1);
	}*/

	//41. KafkaListener Error Handler
	//42. Global Error Handler
	/*@Override
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
	}*/


}
