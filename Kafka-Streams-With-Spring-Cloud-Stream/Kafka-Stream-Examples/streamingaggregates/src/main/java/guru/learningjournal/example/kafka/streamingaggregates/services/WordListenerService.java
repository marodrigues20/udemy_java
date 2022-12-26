package guru.learningjournal.example.kafka.streamingaggregates.services;


import guru.learningjournal.example.kafka.streamingaggregates.bindings.WordListenerBinding;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Locale;

@Service
@Log4j2
@EnableBinding(WordListenerBinding.class)
public class WordListenerService {

    @StreamListener("words-input-channel")
    public void process(KStream<String, String> input){

        KStream<String, String> wordStream = input
                .flatMapValues(value -> Arrays.asList(value.toLowerCase().split(" ")));

        // There is one limitation to aggregating in Kafka Stream. It can be grouped only by a key.
        // If your stream already come with the desired key, you can simply group your KStream using groupByKey()
        // Alternatively, you can use the groupBy() method when your stream does not have a key.
        // The groupBy() method creates a KGroupedStream.
        // You can't do anything on a KGroupedStream except applying an aggregation formula. The formula is count()
        // method.
        wordStream.groupBy((key, value) -> value)
                .count()
                .toStream()
                .peek((k, v) -> log.info("Word: {} Count: {}", k, v));



    }
}
