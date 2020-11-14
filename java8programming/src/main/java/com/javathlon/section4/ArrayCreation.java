package com.javathlon.section4;

public class ArrayCreation {

    public static void main(String[] args){

        //double doubleArray[] = new double[]; //Array Initializer expected.
        double doubleArray[] = new double[8];
        doubleArray[0] = 3.2;
        System.out.println(doubleArray[0]);
        System.out.println(doubleArray[1]); // All items of primitive number arrays are initialized with 0s.
        System.out.println(doubleArray); // If you print the object you gonna see [D@3b6eb2ec


        int myArray[] = new int[]{-1, 2, 3, 4, 9, 0};

        System.out.println("First item:" + myArray[0]);
        System.out.println("Last item:" + myArray[5]);
        System.out.println("First item:" + myArray[6]); //java.lang.ArrayIndexOutOfBoundsException






    }
}
