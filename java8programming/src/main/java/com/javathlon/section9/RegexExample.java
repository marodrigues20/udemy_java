package com.javathlon.section9;

public class RegexExample {

    public static void main(String[] args){

        String text = "I've met many who have earned     their PhD in Computer Science with great hardship and suffering.";

        String text1 = "It is if they have gone to hell and come back!";

        String text2 = "However, most of them can hardly write any software code.";

        String text3 = "They have   obsolutely no knowledge or skills of an experienced front end development \n " +
                "all of them have absolutely no skills in developing anything full stack.";


        String[] words = text.split("\\s+");

        for (String word: words ) {

            System.out.println("word:" + word);

        }


    }
}
