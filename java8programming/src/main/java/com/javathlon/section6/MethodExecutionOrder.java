package com.javathlon.section6;

public class MethodExecutionOrder {

    public static void main(String[] args){
        MethodExecutionOrder methodExecutionOrder = new MethodExecutionOrder();

        methodExecutionOrder.firstMethod();

        System.out.println("Program is finished");
    }

    public void firstMethod(){
        int x = 5;
        secondMethod();
        System.out.println("First method:" + x);
    }

    public void secondMethod(){
        int x = 3;
        thirdMethod();
        System.out.println("Second Number: " + x);
    }

    public void thirdMethod(){
        int x = 1;
        System.out.println("Third method: " + x);
    }


}
