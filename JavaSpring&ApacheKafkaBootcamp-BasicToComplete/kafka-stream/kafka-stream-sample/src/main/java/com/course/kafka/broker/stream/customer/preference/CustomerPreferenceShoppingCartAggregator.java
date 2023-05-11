package com.course.kafka.broker.stream.customer.preference;


import com.course.kafka.broker.message.CustomerPreferenceShoppingCartMessage;
import com.course.kafka.broker.message.CustomerPreferenceAggregateMessage;
import org.apache.kafka.streams.kstream.Aggregator;

/**
 * Section 15. 93. Cart & Wishlist
 */
public class CustomerPreferenceShoppingCartAggregator implements Aggregator<String, CustomerPreferenceShoppingCartMessage, CustomerPreferenceAggregateMessage> {


    @Override
    public CustomerPreferenceAggregateMessage apply(String key, CustomerPreferenceShoppingCartMessage value, CustomerPreferenceAggregateMessage aggregate) {
        aggregate.putShoppingCartItem(value.getItemName(), value.getCartDatetime());
        return aggregate;
    }
}
