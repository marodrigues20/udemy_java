package com.javathlon.section11.excercise;

public class EqualsTest {

  public static void main(String[] args) {

    Customer customer = new Customer(1, "Talha", "Ocakci", "M", true);
    Customer customer1 = new Customer(2, "Josh", "Hollowy", "M", true);
    Customer customer2 = new Customer(2, "Marie", "Jane", "F", true);
    Customer customer3 = new Customer(4, "Natalie", "Portman", "F", true);
    Customer customer4 = new Customer(5, "Bred", "Pitt", "M", true);

    boolean isSame = customer.equals(customer2);
    System.out.println(isSame);

    boolean isSame2 = customer1.equals(customer2);
    System.out.println(isSame2);






  }

}
