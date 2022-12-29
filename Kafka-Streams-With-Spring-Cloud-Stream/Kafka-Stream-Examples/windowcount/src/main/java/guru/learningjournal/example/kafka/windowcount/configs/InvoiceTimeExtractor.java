package guru.learningjournal.example.kafka.windowcount.configs;

import guru.learningjournal.example.kafka.windowcount.model.SimpleInvoice;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.processor.TimestampExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Every time you receive a message record from the Kafka topic, the framework will call this extract() method and give
 * you the message record. Now, we can extract the timestamp from this message record and return it.
 * The extract() method also provides us the time of the previous record.
 * So, if this record's created time is not valid, I'll return the time of the previous record.
 * The propose of the timestamp extractor is to provide the time for each record.
 */
@Configuration
public class InvoiceTimeExtractor implements TimestampExtractor{

    @Override
    public long extract(ConsumerRecord<Object, Object> consumerRecord, long prevTime) {
        SimpleInvoice invoice = (SimpleInvoice) consumerRecord.value();
        return ((invoice.getCreatedTime() > 0) ? invoice.getCreatedTime() : prevTime);
    }

    /**
     * I'll copy this bean method name and paste it to my application YAML file.
     * Line 22: timestampExtractorBeanName: invoiceTimesExtractor
     */
    @Bean
    public TimestampExtractor invoiceTimesExtractor() {
        return new InvoiceTimeExtractor();
    }
}
