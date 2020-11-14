package com.javathlon.section6;

public class MemoryTestPrimitiveArguments {

    public static void main(String[] args){

        MemoryTestPrimitiveArguments test = new MemoryTestPrimitiveArguments();
        int testNumber = 10;
        test.doubleTheValue(testNumber);
        System.out.println(testNumber);


    }

    public int doubleTheValue(int val){
        val *= 2;
        return val;
    }
}
