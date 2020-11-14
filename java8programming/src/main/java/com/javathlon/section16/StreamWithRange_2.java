package com.javathlon.section16;

import java.util.OptionalInt;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamWithRange_2 {

    public static void main(String[] args){

        IntStream myIntStream = IntStream.rangeClosed(10, 100).skip(10);
        OptionalInt optionalInt = myIntStream.max();
        Integer max  = optionalInt.getAsInt();
        System.out.println("max:" + max);
        myIntStream = IntStream.rangeClosed(10, 100).skip(10);
        System.out.println("min:" + myIntStream.min().getAsInt());
        myIntStream = IntStream.rangeClosed(10, 100).skip(10);
        System.out.println("avarage:" + myIntStream.average().getAsDouble());
        myIntStream = IntStream.rangeClosed(10, 100).skip(10);;
        System.out.println("sum:" + myIntStream.sum());

    }


}
