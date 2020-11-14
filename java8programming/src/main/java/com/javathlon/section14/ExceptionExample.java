package com.javathlon.section14;

public class ExceptionExample {

    public static void main(String[] args) {

        try{
            int id = Integer.parseInt("5");
        }catch (NumberFormatException e){
            e.printStackTrace();
            System.out.println("test " + e.getMessage());
        }finally {
            System.out.println("string has been tried to convert into a integer");
        }

        System.out.println("Program has finished");

    }
}
