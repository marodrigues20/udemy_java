package com.javathlon.section16;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

public class TransformExample {

    public static void main(String[] args) {

        List<String> dateList = new ArrayList();
        dateList.add("01/01/2016");
        dateList.add("02/02/2016");
        dateList.add("03/04/2016");
        dateList.add("05/06/2016");

        Stream<String> stringStream = dateList.stream();
        stringStream.map(TransformExample::convertDate)
                //.filter(TransformExample::isWeekend)
                .filter(d -> !TransformExample.isWeekend(d))
                .forEach(System.out::println);

    }


    public static boolean isWeekend(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY ||
                calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY;
    }

    public static Date convertDate(String text){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try{
            return sdf.parse(text);
        } catch (ParseException e){
            e.printStackTrace();
        }
        return null;
    }
}
