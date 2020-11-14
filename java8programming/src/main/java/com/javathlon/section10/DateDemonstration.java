package com.javathlon.section10;

import java.util.Date;

public class DateDemonstration {

    public static void main(String[] args){

        Date d1 = new Date();
        //Time elapsed from the 1/1/1970
        long l = d1.getTime();
        System.out.println(l);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Date d2 = new Date();
        //Time elapsed from the 1/1/1970
        long l1 = d2.getTime();
        System.out.println(l1);


        long diff = l - l1;
        System.out.println(diff);

        d1.compareTo(d2);




    }
}
