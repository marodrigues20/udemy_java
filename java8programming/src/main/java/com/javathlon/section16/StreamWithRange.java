package com.javathlon.section16;

import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamWithRange {

    public static void main(String[] args){

        Stream<String> stringStream = Stream.of("talha","ocakci","java");
        stringStream.forEach(System.out::println);

        Stream<Double> doubleStream = Stream.of(3.5, 4.3);
        doubleStream.forEach(System.out::println);

        DoubleStream myDoubleStream = DoubleStream.of(3.5, 4.3);
        myDoubleStream.forEach(System.out::println);

        IntStream intStream = IntStream.range(10, 100);
        intStream.forEach(System.out::println);

        IntStream intStreamClosed = IntStream.rangeClosed(10, 100).skip(10);
        intStreamClosed.forEach(System.out::println);

    }


}
