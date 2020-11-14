package com.javathlon.section16;

import java.util.Date;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class MyDateExample {

  //Functional Programming style.
  //You can directly refer this pre-developed methods.
  public static void main(String[] args) {

    Supplier<MyDate> myDateSupplier = MyDate :: new;

    MyDate myDate = myDateSupplier.get();

    Function<Date, String> dayPrinter = myDate :: getDayName;

    System.out.println(dayPrinter.apply(new Date()));

    //If your method is a instance you have to define an instance.
    Supplier<String> dayPrinter1 = myDate :: getDayName;

    // If you write Class Name you have define static methods or constructor
    //Supplier<String> dayPrinter1 = MyDate :: getDayName;

    System.out.println(dayPrinter1.get());

    Predicate<Date> weekendPredicate = myDate :: isWeekend;

    System.out.println(weekendPredicate.test(myDate.getNextDate()));

    Predicate<Date> weekendPredicate2 = MyDate :: isThursday;

    System.out.println(weekendPredicate.test(myDate.getNextDate()));

  }



}
