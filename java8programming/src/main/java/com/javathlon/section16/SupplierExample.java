package com.javathlon.section16;

import java.util.Calendar;
import java.util.function.Supplier;

public class SupplierExample {

  public static void main(String[] args) {

    Supplier<Calendar> calendarSupplier = new Supplier<Calendar>() {
      @Override
      public Calendar get() {
        return Calendar.getInstance();
      }
    };


    Calendar c = calendarSupplier.get();

    c.clear();

    String s = ":";
    switch (s){
      case "sada" : System.out.println("fds");
    }

  }

}
