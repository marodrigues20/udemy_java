package com.javathlon.section16.flatmap;

import java.util.ArrayList;
import java.util.List;

public class FlatMapExample {

    public static void main(String[] args) {

        Customer customerTalha = new Customer("talha ocaki");
        Customer customerJimi = new Customer("jimi hendrix");

        customerTalha.addPurchase(new Purchase(2000.0,"Galaxy S8"));
        customerTalha.addPurchase(new Purchase(3000.0,"Iphone 8"));
        customerJimi.addPurchase(new Purchase(1500.0,"Ibanez"));
        customerJimi.addPurchase(new Purchase(300.0,"Equalizer"));

        List<Customer> customers = new ArrayList<>();
        customers.add(customerJimi);
        customers.add(customerTalha);

        /*customers.stream()
                .map(customer -> customer.getPurchases())
                .flatMap(l -> l.stream())
                .map(p -> p.getPrice() * 0.8)
                .forEach(System.out::println);*/

        double totalProfit = customers.stream()
                .map(customer -> customer.getPurchases())
                .flatMap(l -> l.stream())// combine several collection or streams into a single stream
                .map(p -> p.getPrice() * 0.8)
                .reduce(0.0, Double::sum);

        System.out.println("Total profit : " + totalProfit);
    }
}
