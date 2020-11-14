package com.javathlon.section16;

import java.util.Calendar;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class MyLambdaExample {

  public static void main(String[] args) {

    //If you use curl braces you have to use return.
    //If you not use curl braces you don't use return.
    Function<String, Integer> findWordCount =
        //t -> { return t.split(" ").length; };
        t -> t.split(" ").length;

    Integer i = findWordCount.apply("test test");
    System.out.println(i);


    //The instance is created automacatly by JVM.
    //This lambda expression will refer to the Predicate. Lambda is just a shorthand to create
    // some functional interfaces. But in the background JVM create Predicate instance again.
    // If you remove <String> you receive compilation error because JVM doesn't understand the type
    // of t variable.
    Predicate<String> sizeChecker =
        t -> t.length() < 50;

    System.out.println(sizeChecker.test("Talha"));


    // When you don't have any method parameter you have to use ().
    Supplier<Calendar> calendarSupplier =
        () -> { return Calendar.getInstance(); };


    Calendar c = calendarSupplier.get();

    System.out.println(c.getTime());

  }



}
