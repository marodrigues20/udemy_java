package com.javathlon.section2;

public class LongExample {

    public static void main(String[] args){

        // RANGE -2,147,483,648 to 2,147,483,647
        int value1 = 9000000;
        int value2 = 9000000;

        int value3 = value1 * value2;
        System.out.println(value3); // The result 1211764736 was truncated by VM because int type doesn't support.

        //long: stores between -9223372036854775807 and +9223372036854775807
        long value4 = 9000000;
        long value5 = 9000000;

        long value6 = value4 * value5;
        System.out.println(value6); // Correct result. 81000000000000

        //int value7 = 1000000000000000000000; //error: int number is too large

        //short: stores between -32768 and +36767
        //short s1 = 34000; //number too big.
        short s2 = -32768;
        short s3 = 32767;
        //short s4 = 3400 * 1000; //result will be bigger. error.


    }
}
