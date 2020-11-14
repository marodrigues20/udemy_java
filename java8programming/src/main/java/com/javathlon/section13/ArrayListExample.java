package com.javathlon.section13;

import java.util.ArrayList;
import java.util.List;

public class ArrayListExample {

  public static void main(String[] args) {

    //ArrayList may grow or shrink according to the number of items automatically.
    ArrayList<Integer> integerList = new ArrayList<>();
    integerList.add(5);
    integerList.add(1);
    integerList.add(-6);

    System.out.println(integerList.size());
    System.out.println("3rd item is" + integerList.get(2));

    integerList.add(1,8);

    System.out.println("Size: " + integerList.size());

    System.out.println("3rd item is: " + integerList.get(2));

    integerList.remove(0);
    System.out.println("Size: " + integerList.size());

    System.out.println("0th item after removal is: " + integerList.get(0));

    int index = integerList.indexOf(-6);
    System.out.println("Index of -6 is:" + index);

    Integer searchItem = integerList.get(index);
    System.out.println("Item at 2 is: " + searchItem);

    integerList.clear();
    System.out.println(integerList.size());
    System.out.println("Size of the list after clearing:" + integerList.size());

    ////////////////////

    List customerList = new ArrayList<Customer>();

    Customer customer = new Customer(1, "Talha", "Ocaki", "M", true);
    Customer customer2 = new Customer(2, "Josh", "Halloway", "M", true);
    Customer customer3 = new Customer(3, "Marie", "Jane", "F", true);
    Customer customer4 = new Customer(4, "Natalie", "Portman", "F", true);
    Customer customer5 = new Customer(5, "Brad", "Pitt", "M", true);

    customerList.add(customer);

    System.out.println("Size of customer list: " + customerList.size());

    customerList.add(0, customer2);
    System.out.println("First item customer list: " + customerList.size());

    System.out.println("First item of customer list: " + customerList.get(0));

    int indexOfCustomer = customerList.indexOf(customer2);
    System.out.println("Index of Josh Halloway: " + indexOfCustomer);

    customerList.add(customer3);
    customerList.add(customer4);
    customerList.add(customer5);

    Customer c = new Customer(4, "fdasdf", "fasdfad","M", false);
    //For use the indexOf the equals method needs to be implemented.
    int index4 = customerList.indexOf(c);
    System.out.println("indexOf4:" + index4);
    Customer natalie = (Customer) customerList.get(index4);
    System.out.println(natalie);

    boolean isId4 = customerList.contains(c);
    System.out.println("Is anybody there with 4 : " + isId4);

    Customer c2 = new Customer(26, "dfas","fdafs", "M", false);
    customerList.contains(c2);
    System.out.println(customerList.contains(c2));

    //Everytime I don't find any item in the ArrayList return -1.
    int indexOf26 = customerList.indexOf(c2);
    System.out.println("indexOf26: " + indexOf26);











  }

}
