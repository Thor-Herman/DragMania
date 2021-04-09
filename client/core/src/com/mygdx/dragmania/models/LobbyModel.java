package com.mygdx.dragmania.models;

import java.util.Arrays;

public class LobbyModel {

    private static final int MAX_PLAYER_COUNT = 2;
    private int currentConnectedCount;
    private String[] usernames = new String[MAX_PLAYER_COUNT];
    private int roomCode;
    private boolean isHost;

    public LobbyModel(int roomCode, boolean isHost) {
        this.roomCode = roomCode;
        this.isHost = isHost;
    }

    public void playerJoinedLobby(String username) {
        if (currentConnectedCount == MAX_PLAYER_COUNT)
            throw new IllegalStateException("Too many players already connected");
        usernames[currentConnectedCount] = username;
        currentConnectedCount++;
    }

    public int getCurrentConnectedCount() {
        return currentConnectedCount;
    }

    public String getUsername(int index) {
        if (index < 0 || index > currentConnectedCount - 1)
            throw new IllegalArgumentException("Index is not a legal value");
        return usernames[index];
    }
}
