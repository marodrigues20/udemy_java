package guru.learningjournal.examples.kafka.top3spots.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import guru.learningjournal.examples.kafka.top3spots.bindings.ClicksListenerBinding;
import guru.learningjournal.examples.kafka.top3spots.models.AdClick;
import guru.learningjournal.examples.kafka.top3spots.models.AdInventories;
import guru.learningjournal.examples.kafka.top3spots.models.ClicksByNewsType;
import guru.learningjournal.examples.kafka.top3spots.models.Top3NewsTypes;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueStore;
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
     * - As we learned in the earlier section, the aggregation method takes three mandatory arguments.
     * 1. Initializer
     * 2. Adder
     * 3. Subtractor
     *
     * - The Initializer is as simple as creating an empty data structure.
     * - The adder method takes three arguments, key, new event value, and the previous aggregated values.
     * - We need to add the new Value to the previously aggregated value and return the new aggregated value.
     * - Sorting and maintaining only three records is taken care of by the data structure.
     * - The next one is the subtractor.
     * - This one is also the same, but it removes the given element from the data structure.
     * - The aggregate method will materialize some intermediate data to a local KTable.
     * - So, don't forget to provide the materialization details.
     * - This is not always needed. We are using default Serdes, and my stream pipeline should infer the serialization
     * mechanism.
     * - However, to be on the safer side, I am giving it here.
     * - The "top3-clicks" name here is the name of the intermediate state store table.
     * - The KeyValueStore is the type of state store.
     * - The other two argument are the key and the value serdes.
     *
     *
     */
    @StreamListener
    public void process(@Input("inventories-channel") GlobalKTable<String, AdInventories> inventory,
                        @Input("clicks-channel") KStream<String, AdClick> click) {

        click.foreach((k, v) -> log.info("Click Key: {}, Value: {}", k, v));

        KTable<String, Long> clicksByNewsTypeKTable = click.join(inventory,
                (clickKey, clickValue) -> clickKey,
                (clickValue, inventoryValue) -> inventoryValue)
                .groupBy((joinedKey, joinedValue) -> joinedValue.getNewsType(),
                        Grouped.with(Serdes.String(),
                                new JsonSerde<>(AdInventories.class)))
                .count();

        clicksByNewsTypeKTable.groupBy(
                (k, v) -> {
                    ClicksByNewsType value = new ClicksByNewsType();
                    value.setNewsType(k);
                    value.setClicks(v);
                    return KeyValue.pair("top3NewsTypes", value);
                },
                Grouped.with(Serdes.String(), new JsonSerde<>(ClicksByNewsType.class))
        ).aggregate(
                () -> new Top3NewsTypes(),
                (k, v, aggV) -> {
                    aggV.add(v);
                    return aggV;
                },
                (k, v, aggV) -> {
                    aggV.remove(v);
                    return aggV;
                },
                Materialized.<String, Top3NewsTypes, KeyValueStore<Bytes, byte[]>>
                        as("top3-clicks")
                        .withKeySerde(Serdes.String())
                        .withValueSerde(new JsonSerde<>(Top3NewsTypes.class)))
                .toStream().foreach(
                (k, v) -> {
                    try {
                        log.info("k=" + k + " v= " + v.getTop3Sorted());
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                });

    }
}
