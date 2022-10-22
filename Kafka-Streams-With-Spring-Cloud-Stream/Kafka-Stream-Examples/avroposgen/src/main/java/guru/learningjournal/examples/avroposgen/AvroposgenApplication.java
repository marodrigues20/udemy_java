package guru.learningjournal.examples.avroposgen;

import guru.learningjournal.examples.avroposgen.services.KafkaProducerService;
import guru.learningjournal.examples.avroposgen.services.generator.InvoiceGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AvroposgenApplication implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(AvroposgenApplication.class, args);
	}

	@Autowired
	private KafkaProducerService producerService;
	@Autowired
	private InvoiceGenerator invoiceGenerator;
	@Value("${application.configs.invoice.count}")
	private int INVOICE_COUNT;

	// This replaces the main method
	@Override
	public void run(ApplicationArguments args) throws Exception {

		for(int i = 0; i < INVOICE_COUNT; i++){
			producerService.sendMessage(invoiceGenerator.getNextInvoice());
			Thread.sleep(1000);
		}
	}
}
