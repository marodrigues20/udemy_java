package com.course.kafka.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * Section 7: 40. Idempotency Alternative
 */
public class PaymentRequestCacheKey {

    private String paymentNumber;
    private int amount;
    private String transactionType;

    public PaymentRequestCacheKey(String paymentNumber, int amount, String transactionType) {
        super();
        this.paymentNumber = paymentNumber;
        this.amount = amount;
        this.transactionType = transactionType;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PaymentRequestCacheKey other = (PaymentRequestCacheKey) obj;
        return amount == other.amount && Objects.equals(paymentNumber, other.paymentNumber)
                && Objects.equals(transactionType, other.transactionType);
    }

    public int getAmount() {
        return amount;
    }

    public String getPaymentNumber() {
        return paymentNumber;
    }

    public String getTransactionType() {
        return transactionType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, paymentNumber, transactionType);
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setPaymentNumber(String paymentNumber) {
        this.paymentNumber = paymentNumber;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    @Override
    public String toString() {
        return "PaymentRequestCacheKey [paymentNumber=" + paymentNumber + ", amount=" + amount + ", transactionType="
                + transactionType + "]";
    }

}
