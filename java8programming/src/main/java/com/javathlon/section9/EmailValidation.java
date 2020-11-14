package com.javathlon.section9;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidation {

    public static void main(String[] args){

        String emailValidationString = "^[a-zA-Z_.0-9-]+@[a-z0-9]+(\\.[a-z]{2,})+";

        Pattern pattern = Pattern.compile(emailValidationString);

        String[] emails = {
                "btocakci@",
                "btocakci@d",
                "talha ocakci@gmail.com",
                "btocakci@@",
                "TALHAOCAKCI@gmail.com",
                "btocakci@@yahoo.com",
                "btocakciatgmail.com",
                "talha.ocakci@gmail.c.uk",
                "talha_ocakci@gmail.co.uk",
                "talha_ocakci@gmail.co.tr",
                "talhaocaci65@gmail.com",
                "talha_ocakci@gmail.co.45",
                "talha@45.net.tr"
        };

        for (String input: emails ) {

            Matcher matcher = pattern.matcher(input);

            boolean isMatch = matcher.matches();

            System.out.println(input + " : " + isMatch);


        }



    }
}
