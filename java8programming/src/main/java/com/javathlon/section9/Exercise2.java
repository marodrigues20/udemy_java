package com.javathlon.section9;

public class Exercise2 {

    public static void main(String[] args){

        String sentence = "Javathlon is the best resource to learn Java.";
        String word = "Java";

        String newSentence = deleteAll(sentence, word);

        System.out.println(newSentence);
    }

    public static String deleteAll(String sentence, String word){

        return sentence.replace(word,"");


    }
}
