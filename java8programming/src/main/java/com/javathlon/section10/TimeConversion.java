package com.javathlon.section10;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeConversion {

  public static void main(String[] args) throws ParseException {

    // String --> Date --> Calendar
    String timeString = "01/02/2013";
    SimpleDateFormat sdf = new SimpleDateFormat(  "dd/MM/yyyy");
    Date date = sdf.parse(timeString);

    System.out.println(date.getTime());

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    calendar.add(Calendar.DAY_OF_YEAR,1);
    calendar.set(Calendar.YEAR, 2017);

    System.out.println(sdf.format(calendar.getTime()));

    // Calendar --> Date --> String

    Date date2 = calendar.getTime();
    String s = sdf.format(date2);
    System.out.println("Formatted date " + s);







  }

}
