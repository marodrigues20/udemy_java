package com.course.kafka.broker.stream.feedback;

import com.course.kafka.broker.message.FeedbackMessage;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Section 14: 90. Save and Continue
 */
//@Configuration
public class FeedbackFiveStream {
    private static final Set<String> GOOD_WORDS = Set.of("happy", "good", "helpful");
    private static final Set<String> BAD_WORDS = Set.of("angry", "sad", "bad");

    @Bean
    public KStream<String, FeedbackMessage> kStreamFeedback(StreamsBuilder builder) {
        var stringSerde = Serdes.String();
        var feedbackSerde = new JsonSerde<>(FeedbackMessage.class);
        var sourceStream = builder.stream("t-commodity-feedback", Consumed.with(stringSerde, feedbackSerde));


        //However, we use deprecated syntax on this file. So let’s see the newer alternative.
        var feedbackStream = sourceStream.flatMap(splitWords()).branch(isGoodWord(), isBadWord());

        feedbackStream[0].through("t-commodity-feedback-five-good").groupByKey().count().toStream()
                .to("t-commodity-feedback-five-good-count");
        feedbackStream[1].through("t-commodity-feedback-five-bad").groupByKey().count().toStream()
                .to("t-commodity-feedback-five-bad-count");

        return sourceStream;
    }


    private Predicate isBadWord() {
        return (key, value) -> BAD_WORDS.contains(value);
    }

    private Predicate isGoodWord() {
        return (key, value) -> GOOD_WORDS.contains(value);
    }

    private KeyValueMapper<String, FeedbackMessage, Iterable<KeyValue<String, String>>> splitWords() {
        return (key, value) -> Arrays
                .asList(value.getFeedback().replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+")).stream()
                .distinct().map(word -> KeyValue.pair(value.getLocation(), word)).collect(Collectors.toList());
    }


}
