package com.javathlon.section10;

public class WrapperClassDemo {

  public static void main(String[] args) {
    int x = 5;
    // The x will be automatically boxed
    Integer xInstance = x;
    //It'll be automatically boxed
    Integer xInstance2 = 65;

    String xString = xInstance.toString();
    xString = xString + " goats";
    System.out.println(xString);

    String input = "-89";
    //it'll be converted automatically
    //Auto-unboxing
    int newInt = Integer.parseInt(input);

    newInt ++;
    System.out.println(newInt);
    System.out.println(Math.abs(newInt));

    //Auto-Boxing - Primitive to Wrapper class
    Double doubleInstance = 6.59;
    newInt = doubleInstance.intValue();
    System.out.println(newInt);

    String doubleString = "-99.99";
    double doubleValue = Double.parseDouble(doubleString);
    System.out.println(doubleInstance);

    double min = Double.min(4.5, 40.6);
    System.out.println(min);

    Short s = 5;
    Long l = 45l;
    Float f = 5.6f;

    Character c = 'R';
    boolean isVAdigit = c.isDigit(c);
    System.out.println(isVAdigit);

    isVAdigit = Character.isDigit(c);
    System.out.println(isVAdigit);

    System.out.println(Character.isAlphabetic(c));


    int g = 45;
    int h = 82;

    System.out.println(h - g);


  }

}
