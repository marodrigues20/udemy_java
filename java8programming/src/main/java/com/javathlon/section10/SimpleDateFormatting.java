package com.javathlon.section10;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SimpleDateFormatting {

  public static void main(String[] args) {

    Date d = new Date();
    System.out.println(d.getTime()); //get from 1/1/1970
    System.out.println(d);

    String pattern;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
    String formattedString = sdf.format(d);
    System.out.println(formattedString);




  }

}
