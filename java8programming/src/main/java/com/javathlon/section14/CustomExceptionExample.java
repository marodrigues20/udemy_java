package com.javathlon.section14;

import com.javathlon.section14.domain.Product;

public class CustomExceptionExample {

    public static void main(String[] args) throws PriceException {

        Product p = new Product();

        p.setPrice(400);

        System.out.println(p.getPrice());
    }
}
