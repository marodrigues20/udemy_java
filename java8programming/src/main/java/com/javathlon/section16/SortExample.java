package com.javathlon.section16;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class SortExample {

    public static void main(String[] args) {

        List<Player> playerList = new ArrayList();

        playerList.add(new Player(1,2,3,4));
        playerList.add(new Player(0,10,0,20));
        playerList.add(new Player(8,5,10,3));
        playerList.add(new Player(1,0,1,4));
        playerList.add(new Player(1,3,1,8));

        //Stream<Player> playerStream = playerList.stream().sorted(new PlayerComparator());
        //playerStream.forEach(System.out::println);
        //playerStream.forEach(player -> System.out.println(player.matchCount));

        playerList.stream().sorted((player1, player2) -> player1.matchCount - player2.matchCount)
        .forEach(player -> System.out.println(player.score));

    }
}
