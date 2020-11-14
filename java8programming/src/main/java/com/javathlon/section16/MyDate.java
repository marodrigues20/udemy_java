package com.javathlon.section16;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MyDate {

  private String dayName;
  private Date currentDate;
  private Date nextDate;


  public MyDate() {
    super();
    this.currentDate = new Date();
    this.nextDate = getNextDate();
    this.dayName = getDayName(currentDate);

  }


  public Date getNextDate(){
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(this.currentDate);
    calendar.add(Calendar.DAY_OF_YEAR, 1);
    Date dateNew = calendar.getTime();
    return dateNew;
  }

  public String getDayName(Date date){

    SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
    return sdf.format(date);
  }

  public String getDayName(){

    SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
    return sdf.format(new Date());
  }

  public boolean isWeekend(Date date){
    Calendar calendar = Calendar.getInstance();
    return calendar.get(Calendar.DAY_OF_WEEK) ==  Calendar.SATURDAY
        || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
  }

  public static boolean isThursday(Date date){
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    return calendar.get(Calendar.DAY_OF_WEEK) ==  Calendar.THURSDAY;
  }


}
