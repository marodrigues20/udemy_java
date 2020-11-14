package com.javathlon.section13;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class SetExample {

  public static void main(String[] args) {



    Customer customer = new Customer(1, "Talha", "Ocaki", "M", true);
    Customer customer2 = new Customer(2, "Josh", "Halloway", "M", true);
    Customer customer3 = new Customer(3, "Marie", "Jane", "F", true);
    Customer customer4 = new Customer(4, "Natalie", "Portman", "F", true);
    Customer customer5 = new Customer(5, "Brad", "Pitt", "M", true);
    Customer customer7 = new Customer(1, "Talha", "Ocaki", "M", true);

    Set<Customer> set = new HashSet<Customer>();

    set.add(customer);
    set.add(customer);
    set.add(customer);
    set.add(customer);
    set.add(customer);
    //This one won't put into the set.
    set.add(customer7);

    System.out.println(customer.hashCode());
    System.out.println(customer7.hashCode());


    Iterator<Customer> customerIterator = set.iterator();

    while (customerIterator.hasNext()){
      Customer c = customerIterator.next();
      System.out.println(c);
    }





  }

}
