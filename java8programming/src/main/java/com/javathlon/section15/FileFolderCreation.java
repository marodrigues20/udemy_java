package com.javathlon.section15;

import java.io.File;
import java.io.IOException;

public class FileFolderCreation {

  public static void main(String[] args) {

    File f = new File("c://Principal");

    if (f.exists()) {
      System.out.println("Principal is here");
    } else {
      System.out.println("No such folder exists");
    }

    if (f.isDirectory()) {
      System.out.println("This is a directory");
    }

    if(f.canWrite()){
      System.out.println("We can right in this directory");
    }

    if(f.exists() && f.isDirectory() && f.canWrite()){

      File newFolder = new File("c://Principal//teste");
      if(!newFolder.exists()){
        newFolder.mkdir();
      }

      newFolder = new File(f.getAbsolutePath() + "//example2");
      if(!newFolder.exists()){
        newFolder.mkdir();
      }

      //Nested directory creation is not allowed. You should create the upper directory
      //first and then the nested one.
      newFolder = new File(f.getAbsolutePath() + "//example3//example4");
      if(!newFolder.exists()){
        boolean isCreated = newFolder.mkdir();
        if(isCreated){
          System.out.println("Folder has been created");
        }else {
          System.out.println("Folder creation failed.");
        }
      }

      newFolder = new File(f.getAbsolutePath() + "//example3");
      if(!newFolder.exists()){
        boolean isCreated = newFolder.mkdir();
        if(isCreated){
          System.out.println("Folder has been created");
        }else {
          System.out.println("Folder creation failed.");
        }
      }

      newFolder = new File(f.getAbsolutePath() + "//example4");
      if(!newFolder.exists()){
        boolean isCreated = newFolder.mkdir();
        if(isCreated){
          System.out.println("Folder has been created");
        }else {
          System.out.println("Folder creation failed.");
        }
      }

      File newTxtFile= new File(newFolder.getAbsolutePath() + "/alex.txt");
      try{
        boolean isFileCreated = newTxtFile.createNewFile();
        if (isFileCreated) {
          System.out.println("File has been created");
        }else {
          System.out.println("File hasn't been created");
        }
      }catch (IOException e ){
        e.printStackTrace();
      }




    }

  }

}
