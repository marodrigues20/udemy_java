package guru.learningjournal.examples.kafka.sessionwindow.configs;

import guru.learningjournal.examples.kafka.sessionwindow.models.UserClick;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.processor.TimestampExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * We are implementing the event time semantics.
 * You might be wondering, how do we implement ingestion time or the processing time?
 * You do not need to create a timestamp extractor for implementing ingestion time or the processing time.
 * Kafka offers you some ready to use use timestamp extractor:
 * - WallclockTimestampExtractor
 * - FailOnInvalidTimestamp
 * - LogAndSkipOnInvalidTimestamp
 * - UserPreviousTimeOnInvalidTimeStamp
 *
 * So, you can configure your input channel with a wallclock timestamp extractor, and your application starts using
 * processing time.
 * You can configure your input channel with one of these three extractor, and your application starts using ingestion
 * time.
 */
@Configuration
@Log4j2
public class ClickTimeExtractor implements TimestampExtractor{

    @Override
    public long extract(ConsumerRecord<Object, Object> consumerRecord, long prevTime) {
        UserClick click = (UserClick) consumerRecord.value();
        log.info("Click Time: {}", click.getCreatedTime());
        return ((click.getCreatedTime() > 0) ? click.getCreatedTime() : prevTime);
    }

    @Bean
    public TimestampExtractor userClickTimeExtractor() {
        return new ClickTimeExtractor();
    }
}
