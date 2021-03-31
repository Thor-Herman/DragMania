package com.mygdx.dragmania.models;

public class Player {
    private String username;
    private int totalWins;
    private int totalLosses;

    public Player(String username) {
        this.username = username;
    }

    public int getTotalWins() {
        return totalWins;
    }

    public int getTotalLosses() {
        return totalLosses;
    }

    public String getUsername() {
        return username;
    }

    public void gameIsUp(boolean won) {
        if(won) {
            totalWins++;
        }
        else {
            totalLosses++;
        }
    }
}
