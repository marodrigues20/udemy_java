package com.javathlon.section13;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.w3c.dom.ls.LSOutput;

public class LetterCounter {

  public static void main(String[] args) {

    String sentence = "Java, is the most used object oriented language";
    //String sentence = "Java";
    LetterCounter l = new LetterCounter();
    Map<String, Integer> mapa = l.harfSay(sentence);
    l.printTheLetterCounts(mapa);

  }


  private Map<String, Integer> harfSay(String sentence) {
    String[] list = sentence.split("");
    Map<String, Integer> mapa = new HashMap<>();
    int count = 0;
    for (int i = 0; i < list.length; i++) {
      if (mapa.get(list[i]) != null && mapa.containsKey(list[i])) {
        count = mapa.get(list[i]);
        mapa.put(list[i], ++count);
      } else {
        mapa.put(list[i], 1);
      }
      count = 0;
    }
    return mapa;
  }


  public void printTheLetterCounts(Map m) {

    Collection<Map.Entry<String,Integer>> collection = m.entrySet();
    Iterator<Entry<String, Integer>> entryIt = collection.iterator();

    while (entryIt.hasNext()){
      Map.Entry<String,Integer> entry = entryIt.next();
      System.out.println(entry);
    }

  }


}
