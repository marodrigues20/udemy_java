package com.javathlon.section16;

import java.util.Optional;
import java.util.function.Function;

public class OptionalExample {

  public static void main(String[] args) {

    Function<String, String> getSecondWord = ( String s) ->
    { return s.split(" ").length > 1 ? s.split(" ")[1] : null;};


    Function<String, Integer> getLetterCount = t -> t.length();

    //Integer count = getSecondWord.andThen(getLetterCount).apply("talha");

    //System.out.println(count);

    Optional<String> myOptional = Optional.ofNullable(getSecondWord.apply("talha ocakci"));

    /*if(myOptional.isPresent()){
      String s = myOptional.get();
      myOptional.ifPresent(System.out::println);
    }else{
      String s = myOptional.orElse("no else");
      System.out.println(s);
    }*/

    //Optional.ofNullable(getSecondWord.apply("talha ocakci")).ifPresent(System.out::println);

    Optional.ofNullable(getSecondWord.apply("talha ocakci")).map(getLetterCount).ifPresent(System.out::println);



  }

}
