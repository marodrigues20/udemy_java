package com.javathlon.section13;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class HashMapExample {

  public static void main(String[] args) {

    List customerList = new ArrayList<Customer>();

    Customer customer = new Customer(1, "Talha", "Ocaki", "M", true);
    Customer customer2 = new Customer(2, "Josh", "Halloway", "M", true);
    Customer customer3 = new Customer(3, "Marie", "Jane", "F", true);
    Customer customer4 = new Customer(4, "Natalie", "Portman", "F", true);
    Customer customer5 = new Customer(5, "Brad", "Pitt", "M", true);

    Map<String, Customer> map = new HashMap<String,Customer>();
    map.put(customer.getName(),customer);
    map.put(customer2.getName(),customer2);
    map.put(customer3.getName(),customer3);
    map.put(customer4.getName(),customer4);
    map.put(customer5.getName(),customer5);

    Customer c = map.get("Marie");
    System.out.println(c);

    //initialCapacity define the amount of memory blocks there will be.
    Map<Integer, Customer> map2 = new HashMap(100);

    map2.put(customer.getId(),customer);
    map2.put(customer2.getId(),customer2);
    map2.put(customer3.getId(),customer3);
    map2.put(customer4.getId(),customer4);
    map2.put(customer5.getId(),customer5);



    Customer c2 = map2.get(5);
    System.out.println(c2);

    c2 = map.get(5);
    System.out.println(c2);

    System.out.println("------------------- Mapping to Collection -------------------------");
    Collection<Customer> collection = map.values();
    Iterator<Customer> it = collection.iterator();

    while (it.hasNext()){
      Customer customerReference = it.next();
      System.out.println(customerReference.getName());
    }

    System.out.println("------------------- Mapping to Collection Map.Entry -------------------------");

    Collection<Map.Entry<String, Customer>> entrySet = map.entrySet();
    Iterator<Map.Entry<String, Customer>> entryIt = entrySet.iterator();
    while (entryIt.hasNext()){
      Map.Entry<String, Customer> entry = entryIt.next();
      System.out.println("key: " + entry.getKey() + " value " +  entry.getValue() );
    }

    Collection<String> keyCollection = map.keySet();
    Iterator<String> keyIterator = keyCollection.iterator();
    while (keyIterator.hasNext()){
      String key = keyIterator.next();
      System.out.println(key);
    }















  }

}
