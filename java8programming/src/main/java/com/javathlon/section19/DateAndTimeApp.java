package com.javathlon.section19;

import java.time.*;
// Java 8 added 3 new Classes but now they are immutable and easy to operate them.
public class DateAndTimeApp {

    public static void main(String[] args) {

        LocalDate localDate = LocalDate.now();
        System.out.println(localDate);
        //2020-01-08

        LocalTime localTime = LocalTime.now();
        System.out.println(localTime);
        //21:38:16.100156100

        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println(localDateTime);
        //2020-01-08T21:38:16.100156100 -> T is standard ios

        LocalDate twoMonthsLater = localDate.plusMonths(2);
        System.out.println(twoMonthsLater);
        //2020-03-08

        //LocalDate is unmutable object
        //Methods do not change the original object!
        LocalDate twoMonthsAndThreeDaysLater = localDate.plusMonths(2).plusDays(3);
        System.out.println(twoMonthsAndThreeDaysLater);
        System.out.println("Keeps imutable: " + localDate);

        Month month = localDate.getMonth();
        System.out.println(month);

        DayOfWeek day = localDate.getDayOfWeek();
        System.out.println(day);

        LocalDateTime startOfToday = localDate.atStartOfDay();
        System.out.println(startOfToday);

        boolean isAfter = twoMonthsAndThreeDaysLater.isAfter(localDate);
        System.out.println(isAfter);

        LocalDate firstJanuaryOfThisYear = localDate.withMonth(1).withDayOfMonth(1);
        System.out.println(firstJanuaryOfThisYear);

        LocalDateTime timeWithMinuteFiftyNine = localDateTime.withMinute(59);
        System.out.println(timeWithMinuteFiftyNine);

        LocalDateTime localDateTime1 = LocalDateTime.now();
        System.out.println("Let's see localDateTime -> " + localDateTime1);
        System.out.println("Let's see localDate converted -> " + localDateTime1.toLocalDate());





    }
}
