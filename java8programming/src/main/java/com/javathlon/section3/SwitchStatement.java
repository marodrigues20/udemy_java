package com.javathlon.section3;

public class SwitchStatement {

    public static void main(String[] args){

        int numberOfBytes = 1024;

        switch (numberOfBytes){
            case 1: System.out.println("");
            break;
            case 1024: System.out.println("This is KB");
            //break;
            case 1024 * 1024: System.out.println("This is MB");
            break;
            default: System.out.println("I don't know what it is.");
            break;
        }

        System.out.println("Switch is done");

    }
}
