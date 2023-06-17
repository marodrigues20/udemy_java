package com.course.kafka.broker.stream.flashsale;


import com.course.kafka.broker.message.FlashSaleVoteMessage;
import com.course.kafka.util.LocalDateTimeUtil;
import org.apache.kafka.streams.kstream.ValueTransformer;
import org.apache.kafka.streams.processor.ProcessorContext;

import java.time.LocalDateTime;

/**
 * Section 16. Kafka Stream - Flash Sale
 * 97. Timestamp
 */
public class FlashSaleVoteTwoValueTransformer implements ValueTransformer<FlashSaleVoteMessage, FlashSaleVoteMessage> {

    private final long voteStartTime;
    private final long voteEndTime;

    // To access processor API
    private ProcessorContext processorContext;

    public FlashSaleVoteTwoValueTransformer(LocalDateTime voteStartTime, LocalDateTime voteEndTime) {
        this.voteStartTime = LocalDateTimeUtil.toEpochTimestamp(voteStartTime);
        this.voteEndTime = LocalDateTimeUtil.toEpochTimestamp(voteEndTime);
    }

    @Override
    public void init(ProcessorContext context) {
        this.processorContext = context;
    }

    @Override
    public FlashSaleVoteMessage transform(FlashSaleVoteMessage value) {
        var recordTime = processorContext.timestamp();
        return (recordTime >= voteStartTime && recordTime <= voteEndTime) ? value : null;
    }

    @Override
    public void close() {

    }
}
