package com.javathlon.section9;

public class StringMethods {

    public static void main(String[] args){

        String s = "This is the best course on internet";

        int count = s.length();
        System.out.println(count);

        char c = s.charAt(0);

        System.out.println(c);

        char c2 = s.charAt(s.length() - 1);

        System.out.println(c2);

        int indexOfIs = s.indexOf("is");
        System.out.println(indexOfIs);

        indexOfIs = s.indexOf("is", 3);
        System.out.println(indexOfIs);

        String sub = s.substring(8);
        System.out.println(sub);

        sub = s.substring(s.indexOf("the"));
        System.out.println(sub);

        sub = s.substring(s.indexOf("the"),15);
        System.out.println(sub);


        // Below lines will get a String index out of range: 90 exception
        //
        //sub = s.substring(s.indexOf("the"),90);
        //System.out.println(sub);

        // If the string there isn't in the string the method return -1
        //indexOfIs = s.indexOf("Scala");
        //  System.out.println(indexOfIs);


        // below lines get an exception: String index out of range: -1
        //sub = s.substring(s.indexOf("Scala"),15);
        //System.out.println(sub);

        if(s.indexOf("scala") >= 0)
            sub = s.substring(s.indexOf("scala"),15);
        else
            System.out.println("Scala is not found in this string: " + s);

        indexOfIs = s.lastIndexOf("is");
        System.out.println(indexOfIs);

        s = s.toLowerCase();
        System.out.println(s);

        s = s.toUpperCase();
        System.out.println(s);

        //It will return -1 because indexOf is case sensitive.
        int i = s.indexOf("course");
        System.out.println(i);

        i = s.toLowerCase().indexOf("course");
        System.out.println(i);

        s = s.replace("INTERNET","NET");
        System.out.println(s);

        String[] words = s.split(" ");

        for(String word : words){
            System.out.println(word);

        }

        boolean isContain = s.contains("NET");
        System.out.println(isContain);





    }
}
