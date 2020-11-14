package com.javathlon.section15;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class BufferedFileReaderExample {

  public static void main(String[] args) {

    BufferedReader bufferedReader = null;
    try {
      FileReader reader = new FileReader(new File("C:\\Principal\\example.txt"));
      bufferedReader = new BufferedReader(reader);
      String s;
      StringBuilder stringBuilder = new StringBuilder();
      while ((s = bufferedReader.readLine()) != null) {
        //process the line
        stringBuilder.append(s).append(System.lineSeparator());
      }
      System.out.println(stringBuilder.toString());
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (bufferedReader != null) {
          bufferedReader.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }


  }

}
