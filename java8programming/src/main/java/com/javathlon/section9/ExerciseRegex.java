package com.javathlon.section9;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExerciseRegex {

    public String regularExpression = "[a-z]+";  // write your regular expression here
    private Pattern pattern;

    public static void main(String[] args){

        ExerciseRegex exerciseRegex = new ExerciseRegex();
        boolean result = exerciseRegex.isStringFullOfLowercaseAlphabeticCharacters("minha mae e muito legal");

        System.out.println("all text is lowercase : " + result);
    }

    public ExerciseRegex() {
        pattern = Pattern.compile(regularExpression);
    }

    public boolean isStringFullOfLowercaseAlphabeticCharacters(String input) {
        // write your pattern matching code here.
        // if pattern matches return true, else false

        String[] letters = input.split(" ");

        for(String text : letters){

            Matcher matcher = this.pattern.matcher(text);
            boolean isMatches = matcher.matches();

            if(isMatches == false)
                return false;
        }

        return true;

    }

}