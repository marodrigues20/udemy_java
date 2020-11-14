package com.javathlon.section9;

public class StringIntroduction {

    public static void main(String[] args){

        String s = new String("Javathlong.com");
        String s2 = "The best Java course";

        System.out.println(s);
        System.out.println(s2);

        // This concatenation won't modify s nor s2
        System.out.println(s + " " + s2);

        //These strings keep immutable
        System.out.println(s);
        System.out.println(s2);

        // It doesn't compile because there isn't assigned.
        // I am creating a new string but I can't because there isn't variable.
        // to be assigned.
        //s + s2;

        String s3 = s + s2;

        System.out.println(s3);

        s = s + s2;

        System.out.println("News value:" + s);

        // This won't modify the string s.
        // You must to reassigned the result to a variable.
        //s.concat("String to append");

        s = s.concat("String to append");

        System.out.println(s);








    }
}
