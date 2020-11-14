package com.javathlon.section13;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class IteratorExampleOnList {

  public static void main(String[] args) {

    List customerList = new ArrayList<Customer>();

    Customer customer = new Customer(1, "Talha", "Ocaki", "M", true);
    Customer customer2 = new Customer(2, "Josh", "Halloway", "M", true);
    Customer customer3 = new Customer(3, "Marie", "Jane", "F", true);
    Customer customer4 = new Customer(4, "Natalie", "Portman", "F", true);
    Customer customer5 = new Customer(5, "Brad", "Pitt", "M", true);

    customerList.add(customer);
    customerList.add(customer2);
    customerList.add(customer3);
    customerList.add(customer4);
    customerList.add(customer5);

    Iterator<Customer> it = customerList.iterator();

    while (it.hasNext()) {
      String name = it.next().getName();
      System.out.println(name);
    }

    // It doesn't print out because there is no more items to apply hasNext().
    while (it.hasNext()) {
      String name = it.next().getName();
      System.out.println(name);
    }

    it = customerList.iterator();

    System.out.println("------------------- 2nd time --------------------------");

    while (it.hasNext()) {
      String name = it.next().getName();

      if (name.equals("Marie")) {
        it.remove();
      }
    }

    System.out.println("--------- Iteration after removing operation");

    it = customerList.iterator();
    while (it.hasNext()) {
      String name = it.next().getName();
      System.out.println(name);
    }

    System.out.println(" ------------- Iterator with listIterator ------------------------------");
    ListIterator<Customer> listIterator = customerList.listIterator();
    while (listIterator.hasNext()){
      Customer c = listIterator.next();
      System.out.println(c.getName());
    }

    System.out.println(" ------------- Iterator with listIterator end to beginning---------------");

    //if you don't use hasPrevious - you gonna receive java.util.NoSuchElementException
    while (listIterator.hasPrevious()){
      Customer c = listIterator.previous();
      System.out.println(c.getName());
    }




  }

}
