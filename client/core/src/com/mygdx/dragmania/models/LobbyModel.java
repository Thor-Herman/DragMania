package com.mygdx.dragmania.models;

import java.util.Arrays;

public class LobbyModel {

    public static final int MAX_PLAYER_COUNT = 2;
    private int currentConnectedCount;
    private String[] usernames = new String[MAX_PLAYER_COUNT];
    private int roomCode;
    private boolean isHost;
    private boolean isConnected;
    private String errorMessage;

    public LobbyModel(int roomCode, boolean isHost) {
        this.roomCode = roomCode;
        this.isHost = isHost;
        isConnected = false;
    }

    public void reset() {
        this.currentConnectedCount = 0;
        this.usernames = new String[MAX_PLAYER_COUNT];
        this.roomCode = -1;
        this.isHost = false;
        this.isConnected = false;
        this.errorMessage = null;
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

    public void setIsConnected(boolean isConnected) {
        this.isConnected = isConnected;
    }
    
	public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
	}

    public void setRoomCode(int roomCode) {
        this.roomCode = roomCode;
    }

    public void setIsHost(boolean isHost) {
        this.isHost = isHost;
    }

    public int getRoomCode() {
        return roomCode;
    }

    @Override
    public String toString() {
        return "roomCode: " + roomCode + " isHost: " + isHost + " isConnected: " + isConnected + " usernames: " + Arrays.toString(usernames) + " errorMessage: " + errorMessage;
    }

    public void playerLeftLobby(String username) {
        for (int i = 0; i < usernames.length ; i++) {
            if (usernames[i].equals(username)) usernames[i] = null;
        }
        currentConnectedCount--;
    }
}
