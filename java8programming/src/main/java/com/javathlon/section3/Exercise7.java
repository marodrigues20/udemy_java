package com.javathlon.section3;

public class Exercise7 {

    public static void main(String[] args){

        for(int i = 0; i < 8; i++){

            if(i == 0 || i == 7){

                for(int j = 0; j < 8; j++){
                    System.out.print("*");
                }
                System.out.println();
            }

            if(i != 0 && i != 7) {
                System.out.print("*");
                for(int j = 1; j < 7; j++){
                    System.out.print(" ");
                }
                System.out.println("*");
            }


        }






    }
}
