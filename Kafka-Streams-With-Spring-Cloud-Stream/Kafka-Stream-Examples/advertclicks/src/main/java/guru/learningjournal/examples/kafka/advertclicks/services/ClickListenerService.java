package guru.learningjournal.examples.kafka.advertclicks.services;

import guru.learningjournal.examples.kafka.advertclicks.bindings.ClicksListenerBinding;
import guru.learningjournal.examples.kafka.advertclicks.models.AdClick;
import guru.learningjournal.examples.kafka.advertclicks.models.AdInventories;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.kstream.GlobalKTable;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@EnableBinding(ClicksListenerBinding.class)
public class ClickListenerService {

    /**
     * - We are reading all advert inventories into a GlobalKTable.
     * - So, all the inventories would be available to all the stream threads.
     * - Why? Because we are reading it into a GlobalKTable.
     * - We are also reading all the advert clicks into a standard KStream. Right?
     * - If you are running multiple threads of the same application, you will have a full list of inventories
     * at all threads and a partial list of click events.
     * - However, you can join them easily at each stream thread.
     * - Why? Because all the records for the other side of join are available with each stream thread.
     * - In the listener body, I am joining them.
     * - We start with the clickstream and join it with the global table of inventories, the purpose of the join is to
     * enrich the click event with news-type information.
     * - The clickstream has got the inventory id, but we want to pull the news type information based on the identifier.
     * - So, we join the clicks with the inventories, which is the join method's first argument.
     * - In all other case, it would be the Value Joiner lambda.
     * - However, GlobalKTable has got one additional capability. A join with GlobalKTable takes a Key-Value mapper
     * lambda and returns a new join key, which could be a different key than the current KStream Key.
     * - This feature is to join GlobalKTable with a foreing key. However, we do not need this feature for our requirements.
     * - So, I am return the same key.
     * - Finally, the next argument is value-joiner lambda. The first argument for the value joiner lambda is the
     * ad-click event.
     * - The second argument is the inventory record. The value joiner is triggered if and only if both the records keys
     * are matching. So, we know that the inventory is clicked.
     * - All we do here is to return the inventory record.
     * - The oucome of a join is a new KStream. The key is an inventory-id string, and the value is an inventory record.
     * - And this new joined KStream represents the clicked inventories.
     * - Great! So now we have a stream of inventories that are clicked.
     * - All we do is to group them on the news type and count.
     * - I am doing the same in the next step.
     * - The group-by method takes a key-value mapper lambda and returns a grouping key. In our case, we wanted to group
     * it one the news type. In this example, the grouping isn't correctly inferring the serdes.
     * - I defined the default serdes, but I got a deserialization error when I tried running the example.
     * - So, I am explicitly setting the serde here.
     * - Finally, we count the grouped stream and print the final stream so we can see the output.
     */
    @StreamListener
    public void process(@Input("inventories-channel") GlobalKTable<String, AdInventories> inventory,
                        @Input("clicks-channel") KStream<String, AdClick> click) {

        click.foreach((k, v) -> log.info("Click Key: {}, Value: {}",k, v));

        click.join(inventory,
                (clickKey, clickValue) -> clickKey,
                (clickValue, inventoryValue) -> inventoryValue)
                .groupBy((joinedKey, joinedValue) -> joinedValue.getNewsType(),
                        Grouped.with(Serdes.String(),
                                new JsonSerde<>(AdInventories.class)))
                .count()
                .toStream()
                .foreach((k, v) -> log.info("Click Key: {}, Value: {}",k, v));
    }
}
