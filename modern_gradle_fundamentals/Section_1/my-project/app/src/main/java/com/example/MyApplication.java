package com.example;

//import org.apache.commons.lang3.StringUtils;

public class MyApplication {

    public static void main(String[] args) {
        //StringUtils.capitalize(""); // If you change in the business-logic from implemenation to api you can see the library in this project.
        new PrintService().print(new MessageModel("Hi! :)"));
    }
}
