package com.javathlon.section16;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Stream;

public class StreamExample {

    public static void main(String[] args) {

        Set<Integer> mySet = new HashSet<Integer>();
        mySet.add(10);
        mySet.add(11);
        mySet.add(12);
        mySet.add(13);
        mySet.add(14);
        mySet.add(15);
        mySet.add(16);
        mySet.add(17);

        Stream<Integer> stream = mySet.stream();

        //stream.sorted().forEach(System.out::println); //ascendent order

        stream.sorted((first, second) -> second - first ).forEach(System.out::println); //descendent order

        System.out.println("---------------------------------");

        Set<Integer> myTreeSet = new TreeSet<Integer>();
        myTreeSet.add(10);
        myTreeSet.add(11);
        myTreeSet.add(12);
        myTreeSet.add(13);
        myTreeSet.add(14);
        myTreeSet.add(15);
        myTreeSet.add(16);
        myTreeSet.add(17);

        myTreeSet.stream().forEach(System.out::println);


    }
}
