package com.javathlon.section3;

public class Exercise5_4 {

    public static void main(String[] args){

        int result = 1;

        if(0 < result && result <= 30){
            System.out.println("Your grade is D for " + result);
            System.out.println("You may re-attend the exam.");
        }else if (30 < result && result <= 50){
            System.out.println("Your grade is C for " + result);
            System.out.println("You may re-attend the exam.");
        }else if (50 < result && result < 60){
            System.out.println("Your grade is B for " + result);
            System.out.println("You may re-attend the exam.");
        }else if (60 <= result && result <= 80){
            System.out.println("Your grade is B for " + result);
            System.out.println("Thanks for attending the exam.");
        }else if (80 < result && result <= 100){
            System.out.println("Your grade is A for " + result);
            System.out.println("Thanks for attending the exam.");
        }


    }


}
