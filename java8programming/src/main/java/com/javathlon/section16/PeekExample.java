package com.javathlon.section16;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

//We use the peek method while the process is going on.
//We get each item one-by-one
//we use each item the way that we want.
//peek method doesn't remove the item from stream but these items are processed into the nexts stesp.
public class PeekExample {

    public static void main(String[] args) {

        List<String> dateList = new ArrayList();
        dateList.add("01/01/2016");
        dateList.add("02/02/2016");
        dateList.add("03/04/2016");
        dateList.add("05/06/2016");

       List<Date> dates = new ArrayList<>();
       List<Date> weekendsDate = new ArrayList<>();

       dateList.stream()
               .map(TransformExample::convertDate)
               .peek(d -> dates.add(d))
               .filter(d -> !TransformExample.isWeekend(d))
               .peek(d -> weekendsDate.add(d))
               .forEach(System.out::println);

       System.out.println("Dates size: " + dates.size());
       System.out.println("Weekend dates size: " + weekendsDate.size());







    }
}
