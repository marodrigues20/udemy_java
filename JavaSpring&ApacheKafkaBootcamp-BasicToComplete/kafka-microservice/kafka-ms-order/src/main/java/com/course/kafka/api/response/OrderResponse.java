package com.course.kafka.api.response;

/**
 * Section 11. 55 Order - API & Finalize App
 */
public class OrderResponse {

    private String orderNumber;


    public OrderResponse() {
    }

    public OrderResponse(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    @Override
    public String toString() {
        return "OrderResponse{" +
                "orderNumber='" + orderNumber + '\'' +
                '}';
    }
}
