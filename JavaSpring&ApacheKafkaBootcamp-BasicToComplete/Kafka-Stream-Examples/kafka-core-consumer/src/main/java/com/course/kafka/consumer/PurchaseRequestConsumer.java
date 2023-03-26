package com.course.kafka.consumer;

import com.course.kafka.entity.PurchaseRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.github.benmanes.caffeine.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Section 07: 39. Idempotency
 */
@Service
public class PurchaseRequestConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(PurchaseRequestConsumer.class);
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    @Qualifier("cachePurchaseRequest")
    private Cache<Integer, Boolean> cache;

    private boolean isExistInCache(int purchaseRequestId){
        return Optional.ofNullable(cache.getIfPresent(purchaseRequestId)).orElse(false);
    }

    @KafkaListener(topics = "t-purchase-request")
    public void consume(String message) throws JsonProcessingException {
        var purchaseRequest = objectMapper.readValue(message, PurchaseRequest.class);

        var processed = isExistInCache(purchaseRequest.getId());

        if(processed){
            return;
        }

        LOG.info("Processing {}", purchaseRequest);
        cache.put(purchaseRequest.getId(), true);
    }


}
