package com.javathlon.section3;

public class NestedLoopExample {

    public static void main(String[] args){

        for( int i = 30; i <= 60; i = i+2){

            System.out.print(i + " ");

            for(int j=2; j <= 4; j++){

                System.out.print( i / j + " ");
            }

            System.out.println("");

        }

    }
}
