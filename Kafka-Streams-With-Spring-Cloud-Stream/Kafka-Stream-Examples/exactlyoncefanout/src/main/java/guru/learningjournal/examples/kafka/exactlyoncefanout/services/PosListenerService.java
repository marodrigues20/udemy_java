package guru.learningjournal.examples.kafka.exactlyoncefanout.services;

import guru.learningjournal.examples.kafka.model.HadoopRecord;
import guru.learningjournal.examples.kafka.model.Notification;
import guru.learningjournal.examples.kafka.model.PosInvoice;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.annotation.StreamRetryTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@EnableBinding
public class PosListenerService {

    @Autowired
    RecordBuilder recordBuilder;

    /**
     * Kafka Streams will rollback the incomplete transaction when you restart your application again.
     * Once rollback is complete, your application will start a new transaction from the point of the last
     * successful commit.
     * This operation is implementing by Kafka for all Kafka operations. Non-Kafka operations are not part
     * of the transactions.
     * For example:
     * Let's assume you added some code in this process() method for inserting HadoopRecord to a database table.
     * Inserting into a database table is not a kafka operation. So the database table is not protected against failure,
     * and Kakfa will not rollback it. Hence, we never write to the database from the listener code. Instead, we send
     * the output to a Kafka topic and use Kafka Connector for database to move records from the Kafka topic to the
     * database. The Kafka connector implements transactions across databases and will take care of failure scenarios.
     * @param input
     */
    @StreamListener("pos-input-channel")
    public void process(KStream<String, PosInvoice> input){

        KStream<String, HadoopRecord> hadoopRecordKStream = input
                .mapValues( v -> recordBuilder.getMaskedInvoice(v))
                .flatMapValues( v -> recordBuilder.getHadoopRecords(v));

        KStream<String, Notification> notificationKStream = input
                .filter( (k, v) -> v.getCustomerType().equalsIgnoreCase("PRIME"))
                .mapValues(v -> recordBuilder.getNotification(v));

        hadoopRecordKStream.foreach((k, v) -> log.info(String.format("Hadoop Record:- Key: %s, Value: %s", k, v)));
        notificationKStream.foreach((k, v) -> log.info(String.format("Notification:- Key: %s, Value: %s", k, v)));

        hadoopRecordKStream.to("hadoop-sink-topic");
        notificationKStream.to("loyalty-topic");
    }
}
