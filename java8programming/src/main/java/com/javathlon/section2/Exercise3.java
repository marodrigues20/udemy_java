package com.javathlon.section2;

public class Exercise3 {

    public static void main(String[] args){

        int radius = 85;
        int piInt = 22/7;
        //float piInt = 22 / 7; //It will result to 3.0
        //float piInt  = (float) (22.0 / 7.0); //It will result to 3.142857
        float piFloat = 22.0f / 7.0f; //It will result to 3.142857
        double piDouble = 22.0 / 7.0;

        System.out.println("piInt: " + piInt);

        System.out.println("int result: " + 2*piInt*radius);
        System.out.println("float result: " + 2*piFloat*radius);
        System.out.println("double result: " + 2*piDouble*radius);




    }
}
