package com.javathlon.section4;

public class Exercise1Arrays {

    public static void main(String[] args){

        int countArray[] = {8,3,4,1,9,5,2};
        int sum = 0;

        for(int i = 0; i < countArray.length; i++){
            if( countArray[i] % 2 == 0){
                sum += countArray[i];
            }
        }

        System.out.println("sum: " + sum);


        sum = 0;

        //enhanced for loop
        for(int val : countArray){
            if(val % 2 == 0){
                sum += val;
            }
        }

        System.out.println("sum: " + sum);
    }
}
