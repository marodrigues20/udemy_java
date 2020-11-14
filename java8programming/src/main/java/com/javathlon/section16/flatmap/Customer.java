package com.javathlon.section16.flatmap;

import java.util.ArrayList;
import java.util.List;

public class Customer {

    private String nameSurname;
    private List<Purchase> purchases = new ArrayList<>();

    public void addPurchase(Purchase p) {
        purchases.add(p);
    }

    public Customer(String nameSurname) {
        this.nameSurname = nameSurname;
    }

    public String getNameSurname() {
        return nameSurname;
    }

    public void setNameSurname(String nameSurname) {
        this.nameSurname = nameSurname;
    }

    public List<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<Purchase> purchases) {
        this.purchases = purchases;
    }
}
