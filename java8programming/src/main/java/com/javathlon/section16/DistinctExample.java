package com.javathlon.section16;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class DistinctExample {

    public static void main(String[] args) {

        List<String> stringList = new ArrayList();
        stringList.add("talha");
        stringList.add("Talha");
        stringList.add("talha");
        stringList.add("ocakci");
        stringList.add("Ocakci");

        //stringList.stream().forEach(System.out::println);

        stringList.stream()
                .map(t -> t.toLowerCase())
                .distinct().forEach(System.out::println);

        IntStream intStream = IntStream.of(3,5,2,5,3,4,6,3,4,5,6,7);
        intStream.distinct().forEach(System.out::println);

        intStream = IntStream.of(3,5,2,5,3,4,6,3,4,5,6,7);
        long count = intStream.distinct().count();
        System.out.println(count + " distinct items");



    }

}
