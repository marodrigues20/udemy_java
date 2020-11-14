package com.javathlon.section11.excercise;

public class HashCodeTest {

  public static void main(String[] args) {

    Customer customer = new Customer(1, "Talha", "Ocakci", "M", true);
    Customer customer1 = new Customer(2, "Josh", "Hollowy", "M", true);
    Customer customer2 = new Customer(3, "Marie", "Jane", "F", true);
    Customer customer3 = new Customer(4, "Natalie", "Portman", "F", true);
    Customer customer4 = new Customer(5, "Bred", "Pitt", "M", true);

    System.out.println(customer.hashCode());
    System.out.println(customer1.hashCode());
    System.out.println(customer2.hashCode());
    System.out.println(customer3.hashCode());
    System.out.println(customer4.hashCode());
  }

}
