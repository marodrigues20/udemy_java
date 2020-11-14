package com.javathlon.section10;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Trainning {

  public static void printDates(String today, int interval) {

    SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");

    //EEEE means format the day of the week as it is.
    SimpleDateFormat sdfDays = new SimpleDateFormat("MM-dd-yyyy EEEE");

    try {
      Date startDate = sdf.parse(today);
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(startDate);

      String startDateDetail = sdfDays.format(startDate);

      System.out.println(startDateDetail);

      for (int i = 0; i <= 3; i++) {
        calculateDate(calendar, 10);
        System.out.println(sdfDays.format(calendar.getTime()));
      }

    } catch (ParseException e) {

    }
  }

  public static void calculateDate(Calendar calendar, int days) {
    calendar.add(Calendar.DAY_OF_YEAR, 10);
    if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
      calendar.add(Calendar.DAY_OF_YEAR, 1);
    }
  }

  public static void main(String[] args) {
    Trainning.printDates("08-01-2016", 10);
  }


}


