package com.javathlon.section19;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

public class FormattingDates {

    public static void main(String[] args) {
        ZonedDateTime now = ZonedDateTime.now();

        //DateTimeFormatter is thread-safe. We can use multithreads without interfier.
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_TIME;
        String format1 = dateTimeFormatter.format(now);
        System.out.println(format1);

        String format2 = DateTimeFormatter.ISO_OFFSET_DATE.format(now);
        System.out.println(format2);

        String customFormat1 = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss Z").format(now);
        System.out.println(customFormat1);

        String customFormat2 = DateTimeFormatter.ofPattern("dd MM yyyy hh:mm:ss O").format(now);
        System.out.println(customFormat2);

        String customFormat3 = DateTimeFormatter.ofPattern("dd MM yyyy HH:mm:ss O").format(now);
        System.out.println(customFormat3);

        String formatStyleFull = DateTimeFormatter.ofLocalizedTime(FormatStyle.FULL).format(now);
        System.out.println(formatStyleFull);

        String formatStyleLong = DateTimeFormatter.ofLocalizedTime(FormatStyle.LONG).format(now);
        System.out.println(formatStyleLong);


    }
}
