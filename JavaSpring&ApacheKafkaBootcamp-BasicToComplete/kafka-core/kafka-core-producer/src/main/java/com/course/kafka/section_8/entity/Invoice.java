package com.course.kafka.section_8.entity;


/**
 * Section 8: 44. Dead Letter Topic
 */
public class Invoice {

    private String invoiceNumber;
    private int amount;
    private String currency;

    public Invoice() {
    }

    public Invoice(String invoiceNumber, int amount, String currency) {
        this.invoiceNumber = invoiceNumber;
        this.amount = amount;
        this.currency = currency;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
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

    @Override
    public String toString() {
        return "Invoice{" +
                "invoiceNumber='" + invoiceNumber + '\'' +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                '}';
    }
}
