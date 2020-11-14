package com.javathlon.section9;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlExtractor {

    public static void main(String[] args){

        String regex = "(http(s)?)://(www\\.)?([a-z0-9]+.[a-z0-9]{2,})/([a-z0-9]*)";

        Pattern pattern = Pattern.compile(regex);

        Matcher m = pattern.matcher("https://www.google.com/search");

        boolean isMatch = m.matches();

        // if you remove this if using Matcher m = pattern.matcher("http le.com/search");
        // you will receive "java.lang.IllegalStateException: No match found" because
        // because the input doesn't match witch the regular expression.
        if(isMatch){
            System.out.println("Whole match: " + m.group(0));
            System.out.println("Protocol: " + m.group(1));
            System.out.println("Domain name: " + m.group(4));
            System.out.println("Local path: " + m.group(5));
        }

        System.out.println(isMatch);





    }
}
