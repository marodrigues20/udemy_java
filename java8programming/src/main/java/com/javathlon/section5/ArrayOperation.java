package com.javathlon.section5;

public class ArrayOperation {

    public int sumItems(int[] source){

        int sum = 0;
        System.out.println("int[]");
        for(int i = 0; i < source.length; i++){
            sum += source[i];
        }

        return sum;
    }

    public int sumItems(double[] source){

        int sum = 0;
        System.out.println("double[]");
        for(int i = 0; i < source.length; i++){
            sum += source[i];
        }

        return sum;
    }

    public String concatanateStrings (String[] sArrays){

        String result = "";

        for(int i=0; i < sArrays.length; i ++){
            result += sArrays[i];
        }
        return result;
    }




}
