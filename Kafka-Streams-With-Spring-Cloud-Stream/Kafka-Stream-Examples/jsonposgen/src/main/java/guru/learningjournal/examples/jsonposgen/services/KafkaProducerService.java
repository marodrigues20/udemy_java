package guru.learningjournal.examples.jsonposgen.services;


import guru.learningjournal.examples.jsonposgen.model.PosInvoice;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class KafkaProducerService {

    @Value("${application.configs.topic.name}")
    private String TOPIC_NAME;

    @Autowired
    private KafkaTemplate<String, PosInvoice> kafkaTemplate;

    public void sendMessage(PosInvoice invoice){
        log.info(String.format("Producing Invoice No: %s", invoice.getInvoiceNumber()));
        // kafkaTemplate will automatically serialize this invoice object to a JSON String and send it to Kafka topic.
        // The KafkaTemplate will do it because we configure the template to use the JSON serialization.
        kafkaTemplate.send(TOPIC_NAME, invoice.getStoreID(), invoice);
    }
}
