package com.javathlon.section15;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileWriterExample {

  public static void main(String[] args) {

    File file;
    FileWriter writer = null;
    try {
      //false not use the append mode.
      //true use the append mode.
      writer = new FileWriter("c://Principal//example1.txt", true);
      writer.append(" alex");
      writer.append(System.lineSeparator());
      writer.write("new line");

    } catch (IOException e) {
      e.printStackTrace();
    }finally {
      if(writer != null ){
        try {
          writer.flush();
          writer.close();
        } catch (IOException e) {
          e.printStackTrace();
        }

      }


    }


  }

}
