package com.javathlon.section2;

public class Exercise4 {


    //If an arithmetic operation sequence exists, their execut
    //ion sequences as follows:
    //1- Modulus, Division and multiplication (left to right)
    //3- Sum and subtraction (left to right)
    public static void main(String[] args){

        System.out.println(( 3 * 5 ) % 7); // result = 1 --> Multiple after module
        System.out.println(3 * 5 % 7); // result = 1 --> Multiple after module
        System.out.println(3 + 5 % 7); // result = 8 --> module after sum
        System.out.println(( 3 + 5 ) % 7); // result = 1 --> first parentheses after module
        System.out.println(6 * 5 + 11 % 20); // result 41 --> first multiplication after module to finish sum.
        System.out.println(( 6 * ( 5 + 11 ) ) % 20); // result 16 --> sum, multiplication and module.



    }
}
