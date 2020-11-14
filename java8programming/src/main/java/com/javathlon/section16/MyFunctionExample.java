package com.javathlon.section16;

import java.util.function.Function;
import javax.print.DocFlavor.INPUT_STREAM;

public class MyFunctionExample {

  public static void main(String[] args){

    Function<Integer,String> numberToText = new Function<Integer, String>() {
      @Override
      public String apply(Integer t) {

        switch (t){
          case 0: return "zero";
          case 1: return "one";
          case 2: return "two";
          default: return "unknown";
        }
      }
    };


    Function<String, Integer> wordCountFunction = new Function<String, Integer>() {
      @Override
      public Integer apply(String t) {
        return t.split(" ").length;
      }
    };

    String result = numberToText.apply(2);

    System.out.println(result);

    Integer wordCount = wordCountFunction.apply("lets try something new");

    System.out.println(wordCount);

    // First the wordCountFunction will be executed and after numberToText instance.
    String text = wordCountFunction.andThen(numberToText).apply("lets new");

    // In this case we are going to receive error because the compose method invert the execution order.
    //String res = wordCountFunction.compose(numberToText).apply("lets new");

    Integer textAsInteger = wordCountFunction.compose(numberToText).apply(6);

    System.out.println(textAsInteger);


    Function<String,String> toUppercase = new Function<String, String>() {
      @Override
      public String apply(String s) {
        return text.toUpperCase();
      }
    };

  }

}
