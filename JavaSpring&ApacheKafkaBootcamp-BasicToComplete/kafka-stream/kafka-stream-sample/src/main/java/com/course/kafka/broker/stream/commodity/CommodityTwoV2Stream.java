package com.course.kafka.broker.stream.commodity;

import com.course.kafka.broker.message.OrderMessage;
import com.course.kafka.broker.message.OrderPatternMessage;
import com.course.kafka.broker.message.OrderRewardMessage;
import com.course.kafka.util.CommodityStreamUtil;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Branched;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

/**
 * Section 13: 79. Additional Requirements
 */
//@Configuration
public class CommodityTwoV2Stream {

    //@Bean
    public KStream<String, OrderMessage> kstreamCommodityTrading(StreamsBuilder builder){
        var stringSerde = Serdes.String();
        var orderSerde = new JsonSerde<>(OrderMessage.class);
        var orderPatternSerde = new JsonSerde<>(OrderPatternMessage.class);
        var orderRewardSerde = new JsonSerde<>(OrderRewardMessage.class);

        var maskedCreditCardStream = builder.stream("t-commodity-order", Consumed.with(stringSerde, orderSerde));

        // Compare this class with CommodityTwoStream. This is a new way to do the same thing.
        maskedCreditCardStream.mapValues(CommodityStreamUtil::mapToOrderPattern).split()
                .branch(CommodityStreamUtil.isPlastic(), Branched.withConsumer(ks -> ks.to("t-commodity-pattern-two-plastic", Produced.with(stringSerde, orderPatternSerde))))
                .branch((k, v) -> true, Branched.withConsumer(ks -> ks.to("t-commodity-pattern-two-notplastic", Produced.with(stringSerde, orderPatternSerde))));

        var rewardStream = maskedCreditCardStream.filter(CommodityStreamUtil.isLargeQuantity())
                .filterNot(CommodityStreamUtil.isCheap()).mapValues(CommodityStreamUtil::mapToOrderReward);
        rewardStream.to("t-commodity-reward-two", Produced.with(stringSerde, orderRewardSerde));

        var storageStream = maskedCreditCardStream.selectKey(CommodityStreamUtil.generateStorageKey());
        storageStream.to("t-commodity-storage-two", Produced.with(stringSerde, orderSerde));

        maskedCreditCardStream.to("t-commodity-storage-two", Produced.with(stringSerde, orderSerde));

        return maskedCreditCardStream;
    }


}