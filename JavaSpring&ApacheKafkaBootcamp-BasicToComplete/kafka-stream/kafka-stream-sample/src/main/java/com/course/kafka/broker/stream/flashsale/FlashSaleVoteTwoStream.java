package com.course.kafka.broker.stream.flashsale;

import com.course.kafka.broker.message.FlashSaleVoteMessage;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Section 16: 97. Timestamp
 *
 */
@Configuration
public class FlashSaleVoteTwoStream {

    @Bean
    public KStream<String, String> flashSaleVote(StreamsBuilder builder){

        var stringSerde = Serdes.String();
        var flashSaleVoteSerde = new JsonSerde<>(FlashSaleVoteMessage.class);

        var voteStart = LocalDateTime.of(LocalDate.now(), LocalTime.of(5, 30));
        var voteEnd = LocalDateTime.of(LocalDate.now(), LocalTime.of(6, 30));

        //We need to convert the json value into new key-value pair, with user id as key and item name as value. Use map for this.
        var flashSaleVoteStream = builder
                .stream("t-commodity-flashsale-vote", Consumed.with(stringSerde, flashSaleVoteSerde))
                .transformValues(() -> new FlashSaleVoteTwoValueTransformer(voteStart, voteEnd))
                .filter(((key, transformedValue) -> transformedValue != null ))
                .map((key, value) -> KeyValue.pair(value.getCustomerId(), value.getItemName()));

        //Now that we have stream with user id and item name,
        // Now that we have stream with user id and item name, stream this into intermediary topic,
        // so we can build a table from it.
        flashSaleVoteStream.to("t-commodity-flashsale-vote-user-item");


        // At this point, we have table, now we need to group the table.
        // Group by is grouping by key, so we must convert the key, which is currently user id on intermediary stream, to itemName.
        // Now we can count the group elements.
        builder.table("t-commodity-flashsale-vote-user-item", Consumed.with(stringSerde, stringSerde))
                .groupBy((user, votedItem) -> KeyValue.pair(votedItem, votedItem)).count().toStream()
                .to("t-commodity-flashsale-vote-two-result");

        return flashSaleVoteStream;
    }
}
