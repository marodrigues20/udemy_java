package com.javathlon.section2;

public class CastExamples {

    public static void main(String[] args){

        int x = 15;

        long y = x; // perfect no data loss.

        int z;

        //z = y; // I will have data loss. I will receive error.
        z = (int) y; //I am accepting the data loss.

        System.out.println(z);



    }
}
