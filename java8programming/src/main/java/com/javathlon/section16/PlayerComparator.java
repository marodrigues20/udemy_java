package com.javathlon.section16;

import java.util.Comparator;

public class PlayerComparator implements Comparator<Player> {


    @Override
    public int compare(Player player1, Player player2) {
        return player1.matchCount - player2.matchCount;
    }
}
