package com.javathlon.section15;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class InputStreamReaderExample {

  public static void main(String[] args) {

    File f = new File("C:\\Principal\\example.txt");

    InputStream inputStream = null;

    try {
      inputStream = new FileInputStream(f);
      int i = 0;
      while ((i = inputStream.read()) != -1) {
        char c = (char) i;
        System.out.print(c);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
