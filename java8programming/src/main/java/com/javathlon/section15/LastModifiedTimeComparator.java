package com.javathlon.section15;

import java.io.File;
import java.util.Comparator;

public class LastModifiedTimeComparator implements Comparator<File> {


  @Override
  public int compare(File o1, File o2) {

    if(o1.lastModified() > o2.lastModified()){
      return 1;
    }else if(o1.lastModified() == o2.lastModified()){
      return 0;
    }else{
      return -1;
    }


  }
}
