package com.course.kafka.entity;

/**
 * Section 7: 40. Idempotency Alternative
 */
public class PaymentRequest {

    private String paymentNumber;
    private int amount;
    private String currency;
    private String notes;
    private String transactionType;

    public PaymentRequest() {
    }

    public PaymentRequest(String paymentNumber, int amount, String currency, String notes, String transactionType) {
        this.paymentNumber = paymentNumber;
        this.amount = amount;
        this.currency = currency;
        this.notes = notes;
        this.transactionType = transactionType;
    }

    public String getPaymentNumber() {
        return paymentNumber;
    }

    public void setPaymentNumber(String paymentNumber) {
        this.paymentNumber = paymentNumber;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    @Override
    public String toString() {
        return "PaymentRequest{" +
                "paymentNumber='" + paymentNumber + '\'' +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", notes='" + notes + '\'' +
                ", transactionType='" + transactionType + '\'' +
                '}';
    }
}
