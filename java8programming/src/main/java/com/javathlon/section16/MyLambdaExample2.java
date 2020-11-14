package com.javathlon.section16;

import java.util.function.Function;
import java.util.function.Predicate;

public class MyLambdaExample2 {

  public static void main(String[] args) {

    Function<String, Integer> findWordCount = t ->
    {
      return t.split(" ").length;
    };

    Integer integer = findWordCount.apply("test test");

    System.out.println(integer);

    Predicate<String> sizeChecker =
        t -> {
          return t.length() < 50;
        };

    boolean result = sizeChecker.test("test");

    System.out.println(result);
  }

}
