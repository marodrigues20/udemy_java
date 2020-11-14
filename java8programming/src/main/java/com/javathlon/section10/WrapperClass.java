package com.javathlon.section10;

public class WrapperClass {

    public static void main(String[] args){

        int x = 5;

        // Auto-Boxing
        Integer xInstance = x;

        // Boxing
        Integer xInstance2 = 65;

        //What do we need a class?
        //Because this class has some utility and function methods.
        String xString = xInstance.toString();
        xString = xString + " goats";
        System.out.println(xString);

        String input = "-89";
        //Auto-Boxing
        int newInt = Integer.parseInt(input);
        Integer newInt2 = Integer.parseInt(input);
        newInt++;
        System.out.println(newInt);
        System.out.println(Math.abs(newInt));

        Double doubleInstance = 6.59;
        newInt = doubleInstance.intValue();
        System.out.println(newInt);


        String doubleString = "-99.99";
        double doubleValue = Double.parseDouble(doubleString);
        System.out.println(doubleValue + 10);

        Double min = Double.min(4.5, 9.0);
        System.out.println(min);


        Short s = 5;
        Long l = 45l;
        Float f = 5.6f;

        Character c = 'R';
        boolean isVAdigit = c.isDigit('v');
        System.out.println(isVAdigit);

        isVAdigit = Character.isDigit(c);
        System.out.println(isVAdigit);

        System.out.println(Character.isAlphabetic(c));




    }
}
