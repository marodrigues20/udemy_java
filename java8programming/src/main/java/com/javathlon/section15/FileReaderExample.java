package com.javathlon.section15;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileReaderExample {

  public static void main(String[] args) {

    try {
      FileReader reader = new FileReader("C:\\Principal\\example.txt");

      //This char array is called buffer.
      //If you use much many position than necessary you gonna get problems.
      //It's complicated adjust the size of the buffer
      char array[] = new char[100];

      while((reader.read(array)) != -1){
        System.out.print(array);
      }


      reader.read(array);
      System.out.println(array);

    } catch (IOException e) {
      e.printStackTrace();
    }

  }

}
