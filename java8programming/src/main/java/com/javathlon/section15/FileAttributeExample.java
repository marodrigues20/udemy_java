package com.javathlon.section15;

import java.io.File;
import java.io.FileFilter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class FileAttributeExample {

  public static void main(String[] args) {

    File file = new File("c://Principal");
    File[] allFiles = file.listFiles();

    for (File f : allFiles) {
      if (f.isFile()) {
        long size = f.length();
        long sizeInKb = size / 1024;
        System.out.println(f.getName() + " " + sizeInKb + "KB");

        long lastModified = f.lastModified();
        Date lastModifiedTime = new Date(lastModified);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        System.out.println(sdf.format(lastModifiedTime));
      }
    }

    Arrays.sort(allFiles, new LastModifiedTimeComparator());

    System.out.println("-------------------------------------------------------");
    for (File f : allFiles) {
      long lastModified = f.lastModified();
      Date lastModifiedTime = new Date(lastModified);
      SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
      System.out.println(f.getName() + " " + sdf.format(lastModifiedTime));
    }

    System.out.println("Ordered by Size");
    Arrays.sort(allFiles, new FileSizeComparator());
    for (File f : allFiles) {
      System.out.println(f.getName() + " " + f.length());
    }

    FileFilter lastModifiedTimeFilter = new FileFilter() {
      @Override
      public boolean accept(File file) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);

        Date beginningOfDate = c.getTime();

        if (file.lastModified() > beginningOfDate.getTime()) {
          return true;
        }
        return false;
      }
    };


    System.out.println("------------------------------------------------------");
    System.out.println("Today Modified");
    File[] todayModifiedFiles = file.listFiles(lastModifiedTimeFilter);
    for(File f: todayModifiedFiles){
      long lastModified = f.lastModified();
      Date lastModifiedTime = new Date(lastModified);
      SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
      System.out.println(f.getName() + " " + sdf.format(lastModifiedTime));

    }


  }

}
