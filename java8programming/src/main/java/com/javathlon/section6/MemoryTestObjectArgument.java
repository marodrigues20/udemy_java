package com.javathlon.section6;

public class MemoryTestObjectArgument {

    public static void main(String[] args){

        MemoryTestObjectArgument memoryTestObjectArgument = new MemoryTestObjectArgument();
        Customer customer = new Customer(1, "Talha", "turkey");
        memoryTestObjectArgument.changeAddress(customer);
        System.out.println(customer.address);

    }

    public void changeAddress(Customer cust){
        cust.address = "New Zeland";
    }
}
