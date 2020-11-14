package com.javathlon.section3;

public class Exercise6 {

    public static void main(String[] args){

        for(int i = 0; i < 10; i ++){
            for(int k = i; k < 10; k ++){
                System.out.print("*");
            }
            System.out.println();
            for(int j = 0; j < i + 1;j++){
                System.out.print(" ");
            }
        }

        System.out.println();

        //Second way to do it.
        for(int i = 10; i > 0;){
            for(int j = 0; j < i; j++){
                System.out.print("*");
            }
            System.out.println();
            if(i <= 10) {
                int k = --i;
                for (; k < 10; k++) {
                    System.out.print(" ");
                }
            }
        }
    }
}
