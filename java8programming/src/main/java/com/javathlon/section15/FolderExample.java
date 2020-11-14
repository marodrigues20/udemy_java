package com.javathlon.section15;

import java.io.File;
import java.io.FilenameFilter;

public class FolderExample {

  public static void main(String[] args) {

    File f = new File("C:\\Principal");

    String[] allItems = f.list();

    System.out.println("All items");
    for (String item : allItems) {
      System.out.println(item);
    }

    System.out.println("All folders");

    File[] allFileItems = f.listFiles();
    for (File file : allFileItems) {
      if (file.isDirectory()) {
        System.out.println(file.getName());
      }
    }

    System.out.println("All files");
    for (File file : allFileItems) {
      if (!file.isDirectory()) {
        System.out.println(file.getName());
      }
    }

    FilenameFilter filenameFilter = new FilenameFilter() {
      @Override
      public boolean accept(File file, String name) {
        if (name.endsWith("txt")) {
          return true;
        }
        return false;
      }
    };

    System.out.println(" ");
    System.out.println("TXT files ");
    File[] pptxFile = f.listFiles(filenameFilter);
    for (File myFile : pptxFile) {
      System.out.println(myFile.getName() + " - " + myFile.getAbsolutePath() );
    }


  }

}
