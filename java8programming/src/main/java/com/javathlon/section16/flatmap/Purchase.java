package com.javathlon.section16.flatmap;

public class Purchase {

    private Double price;
    private String productName;

    public Purchase(Double price, String productName) {
        this.price = price;
        this.productName = productName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
