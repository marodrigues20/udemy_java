package guru.learningjournal.example.kafka.rewards.services;

import guru.learningjournal.example.kafka.rewards.bindings.PosListenerBinding;
import guru.learningjournal.examples.kafka.model.Notification;
import guru.learningjournal.examples.kafka.model.PosInvoice;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@EnableBinding(PosListenerBinding.class)
public class LoyaltyService {

    @Autowired
    RecordBuilder recordBuilder;

    /**
     * We use the map() method because we want to change the key. If we used the mapValues() method
     * is a key-preserving equivalent of the map() method.
     * The groupByKey is also a key preserving API. This API will group the incoming records by the existing ones,
     * and we will avoid the expensive repartitioning operation.
     * However, we already used the map() method in this flow. So, this streamflow will anyway apply a repartitioning.
     * We cannot avoid it.
     */
    @StreamListener("invoice-input-channel")
    @SendTo("notification-output-channel")
    public KStream<String, Notification> process(KStream<String, PosInvoice> input) {

        KStream<String, Notification> notificationKStream = input
                .filter((k, v) -> v.getCustomerType().equalsIgnoreCase("PRIME"))
                .map((k, v) -> new KeyValue<>(v.getCustomerCardNo(), recordBuilder.getNotification(v)))
                .groupByKey()
                .reduce((aggValue, newValue) -> {
                    newValue.setTotalLoyaltyPoints(newValue.getEarnedLoyaltyPoints() + aggValue.getTotalLoyaltyPoints());
                    return newValue;
                }).toStream();

        notificationKStream.foreach((k, v) -> log.info(String.format("Notification:- Key: %s, Value: %s", k, v)));

        return notificationKStream;
    }


}
