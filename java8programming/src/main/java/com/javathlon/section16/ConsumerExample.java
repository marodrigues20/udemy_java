package com.javathlon.section16;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.function.Consumer;

public class ConsumerExample {

  public static void main(String[] args) {

    Consumer<Date> oneDayIncrement = new Consumer<Date>() {
      @Override
      public void accept(Date date) {

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        date = calendar.getTime();
        System.out.println("tomorrow: " + date);
      }
    };

    Consumer<Date> dayPrinter = new Consumer<Date>() {
      @Override
      public void accept(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy EEEE");
        System.out.println(sdf.format(date));
      }
    };

    oneDayIncrement.accept(new Date());

    dayPrinter.accept(new Date());

    oneDayIncrement.andThen(dayPrinter).accept(new Date());

  }

}
