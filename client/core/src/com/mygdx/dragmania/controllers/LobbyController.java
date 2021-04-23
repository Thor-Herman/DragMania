package com.mygdx.dragmania.controllers;

import com.badlogic.gdx.Gdx;
import com.mygdx.dragmania.models.LobbyModel;
import com.mygdx.dragmania.views.GameView;
import com.mygdx.dragmania.views.ViewManager;
import com.utilities.GameClient;

public class LobbyController {

    private static LobbyController instance;
    private GameClient client;
    private LobbyModel model;

    public static LobbyController getInstance() {
        if (instance == null)
            instance = new LobbyController();
        return instance;
    }

    private LobbyController() {
        model = new LobbyModel(-1, false);
        client = GameClient.getInstance();
    }

    public void connectToServer() {
        client.setup();
    }

    public void connected() {
        model.setIsConnected(true);
        System.out.println(model);
    }

    public void disconnected() {
        model.reset();
        System.out.println(model);
    }

    public void createGame(String username) {
        client.createGame(username);
        model.setIsHost(true);
        System.out.println(model);
    }

    public void joinGame(String username, int roomCode) {
        client.joinGame(username, roomCode);
        model.setIsHost(false);
        System.out.println(model);
    }

    public void joinSuccess(int roomCode, String[] usernames) {
        model.setRoomCode(roomCode);
        if (usernames != null)
            for (String username : usernames) {
                playerJoinedLobby(username);
            }
        System.out.println(model);
    }

    public void errorOccurred(String errorMessage) {
        model.setErrorMessage(errorMessage);
        System.out.println(model);
    }

    public void resetModelOnError() {
        model.reset();
        System.out.println(model);
    }

    public void playerJoinedLobby(String username) {
        model.playerJoinedLobby(username);
        if (areThereEnoughPlayers()) {
            client.readyUp();
            // TODO: Remove lobbyListener from client
            // TODO: Add gameListener to client
        }
    }

    public LobbyModel getModel() {
        return model;
    }

    private boolean areThereEnoughPlayers() {
        return LobbyModel.MAX_PLAYER_COUNT == model.getCurrentConnectedCount();
    }

    public void playerLeftLobby(String username) {
        model.playerLeftLobby(username);
    }

}
