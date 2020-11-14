package com.javathlon.section20;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GenericExample {

    public static void main(String[] args) {

        List<Number> myValues = new ArrayList<>();
        myValues.add(5);
        myValues.add(5.5);
        //myValues.add(new Date());

        GenericExample genericExample = new GenericExample();
        genericExample.sum(myValues);



    }

    private void sum(List<Number> values){

        double sum=0;
        for (int i = 0; i < values.size(); i++){

            //sum += (Integer) values.get(i);
            sum += values.get(i).doubleValue();

        }
        System.out.println(sum);
    }
}
