package com.javathlon.section16;

import java.util.function.Predicate;

public class PredicateExample {

  public static void main(String[] args) {

    Predicate<String> sizeChecker = new Predicate<String>() {
      @Override
      public boolean test(String t) {

        return t.length() < 50;
      }
    };


    Predicate<String> specialWordChecker = new Predicate<String>() {
      @Override
      public boolean test(String t) {
        return t.contains("Download");
      }
    };


    String text = "Download Java functional programming book.";

    boolean isProper = sizeChecker.test(text);

    sizeChecker.negate().and(specialWordChecker).test(text);

    System.out.println(isProper);

    boolean moreThan50WithDonwload = sizeChecker.negate().and(specialWordChecker).test(text);

    System.out.println(moreThan50WithDonwload);


  }

}
