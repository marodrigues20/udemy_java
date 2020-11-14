package com.javathlon.section2;

public class Exercise1 {

    public static void main(String[] args){

        int distance = 4750;
        int km;
        int m;

        km = distance / 1000;
        m = distance % 1000;

        System.out.println("Km " + km);
        System.out.println("meters " + m);

    }
}
