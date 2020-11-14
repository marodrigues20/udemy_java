package com.javathlon.section16;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class FilterExample {

    public static void main(String[] args) {

        List<Player> playerList = new ArrayList();

        playerList.add(new Player(1,2,3,4));
        playerList.add(new Player(0,10,0,20));
        playerList.add(new Player(8,0,10,4));
        playerList.add(new Player(1,3,1,8));

        Stream<Player> playerStream = playerList.stream();

        playerStream.forEach(t-> System.out.println("match: " + t.matchCount));

        playerStream = playerList.stream();

        System.out.println("-------------------------------");

        playerStream.filter(p -> p.matchCount > 4)
                .filter(p -> p.score > 2)
                .forEach(t -> System.out.println("match:" + t.matchCount));
    }
}
