package com.javathlon.test;

import java.util.Arrays;

public class BankTest {

  public static void main(String[] args) {

    System.out.println(polimonio("mario"));



  }

  public static boolean polimonio(String word) {

    String[] charWord = word.split("");
    String[] newCharWord = new String[charWord.length];


    int indexInverse = charWord.length;
    for (int i = 0; i <= charWord.length; i++) {
      if (indexInverse != 0) {
        newCharWord[--indexInverse] = charWord[i];
        System.out.print(newCharWord[indexInverse]);
      }
    }

    String newWord = String.join("",newCharWord);

    if (newWord.contains(word)) {
      return true;
    } else {
      return false;
    }

  }

}
