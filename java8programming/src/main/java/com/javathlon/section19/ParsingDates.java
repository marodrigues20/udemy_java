package com.javathlon.section19;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ParsingDates {

    public static void main(String[] args) {

        DateTimeFormatter parser = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime localDateTime = LocalDateTime.parse("2014-01-01T09:00", parser);
        LocalDateTime localDateTime2 = LocalDateTime.parse("2014-01-01T09:30", parser);

        System.out.println(localDateTime);
        System.out.println(localDateTime2);

        Duration twentyMinutes = Duration.ofMinutes(20);
        localDateTime = localDateTime.plus(twentyMinutes);
        localDateTime2 = localDateTime2.plus(twentyMinutes);
        System.out.println(localDateTime);
        System.out.println(localDateTime2);

        LocalDate localDate = LocalDate.parse("2014-01-01T09:00", parser);
        System.out.println(localDate);

        LocalTime localTime = LocalTime.parse("2014-01-01T09:00", parser);
        System.out.println(localTime);

        DateTimeFormatter customFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        String myTimeString = "03/07/2019 13:15";
        LocalDateTime myTime = LocalDateTime.parse(myTimeString, customFormatter);
        System.out.println(myTime);

        String myDateString = "03/07/2019";
        DateTimeFormatter customDateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate myDate = LocalDate.parse(myDateString, customDateFormatter);
        System.out.println(myDate);








    }
}
