package com.javathlon.section2;

public class CharArithmetic {

    public static void main(String[] args){

        char myChar = 'c';
        char myChar2 = 100;

        myChar2 = (char) (myChar + 1); //here I have to do a cast because myChar is a integer
        //implicit. The result of operation will be integer 32bits. You have to do the cast.
        System.out.println(myChar2);


        char a = 's' + 2; //Here you don't need a cast because I haven't declared a char variable to represent 's'.
        System.out.println(a);


        char variable = 65;

        char result = (char) (variable + 32);

        System.out.println("my exercise --> " + result);

        int test = result;
        System.out.println(test);






    }
}
