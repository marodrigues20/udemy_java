package com.course.kafka.broker.stream.customer.preference;

import com.course.kafka.broker.message.CustomerPreferenceWishlistMessage;
import com.course.kafka.broker.message.CustomerPreferenceAggregateMessage;
import org.apache.kafka.streams.kstream.Aggregator;


/**
 * Section 15. 93. Cart & Wishlist
 */
public class CustomerPreferenceWishlistAggregator implements Aggregator<String, CustomerPreferenceWishlistMessage, CustomerPreferenceAggregateMessage> {
    @Override
    public CustomerPreferenceAggregateMessage apply(String key, CustomerPreferenceWishlistMessage value, CustomerPreferenceAggregateMessage aggregate) {
        aggregate.putWishlistItem(value.getItemName(), value.getWishlistDatetime());
        return aggregate;
    }
}
