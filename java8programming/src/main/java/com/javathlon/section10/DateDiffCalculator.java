package com.javathlon.section10;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateDiffCalculator {

  public static String getTimeDifference(String date1, String date2) {

    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    try {

      Date firstDate = sdf.parse(date1);
      Date secondDate = sdf.parse(date2);

      long difference = firstDate.getTime() - secondDate.getTime();

      System.out.println(difference);

      int daysinMilliSeconds = 1000 * 60 * 60 * 24;

      long day = difference / daysinMilliSeconds;

      difference = difference % daysinMilliSeconds;

      int hoursInMilliSeconds = 1000 * 60 * 60;

      long hour = difference / hoursInMilliSeconds;

      System.out.println(difference);

      difference = difference % hoursInMilliSeconds;

      long minutes = difference / 1000 / 60;

      difference = difference % (1000 * 60);

      long second = difference / 1000;

      return day + " days " + hour + " hours " + minutes + " minutes " + second + " second" ;

    } catch (ParseException e) {
      e.printStackTrace();
    }

    return "";

  }

  public static void main(String[] args) {

    String s = DateDiffCalculator.getTimeDifference("09/08/2016 02:30:22", "01/03/2016 01:23:09");

    System.out.println(s);



  }

}
