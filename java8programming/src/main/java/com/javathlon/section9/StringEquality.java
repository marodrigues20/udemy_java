package com.javathlon.section9;

public class StringEquality {

    public static void main(String[] args){

        String s = new String("javathlone");
        String s2 = new String("javathlone");

        System.out.println("NEW KEYWORD  " + s.equals(s2));
        System.out.println("NEW KEYWORD  " + (s == s2));

        String stringliteral1 = "talha";
        String stringliteral2 = "talha";

        System.out.println("STRING LITERAL:  " + stringliteral1.equals(stringliteral2));
        System.out.println("STRING LITERAL:  " + (stringliteral1 == stringliteral2));

    }
}
