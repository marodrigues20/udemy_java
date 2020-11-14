package com.javathlon.section13;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class DuplicateWordRemover {

  public static void main(String[] args) {

    String sentence = "Collections is the most important item in Java development";
    DuplicateWordRemover d = new DuplicateWordRemover();
    System.out.println(d.removeDuplicateLetters(sentence));

  }

  String removeDuplicateLetters(String s) {

    Set<String> newSentence = new HashSet();
    StringBuilder sb = new StringBuilder();

    for (Character c : s.toCharArray()) {
      if (!newSentence.contains(c.toString()) || c.equals(' ')) {
        newSentence.add(c.toString());
        sb.append(c);
      }
    }
    return sb.toString();
  }

}
