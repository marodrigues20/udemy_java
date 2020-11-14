package com.javathlon.section14;

import com.javathlon.section16.Custormer;

public class ArithmeticExceptionExample {

    public static void main(String[] args) {

        int x = 0;
        int y = 0;
        try{
            y = 3 / x;
        }catch (Exception e){
            System.out.println("Please provide another number");
        }

        Custormer c = null;

        try{
            System.out.println(c.getName());
        }catch (NullPointerException e){ //Unchecked exception
            System.out.println("Customer is null");
        }

        System.out.println(y);

    }
}
