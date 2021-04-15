package com.mygdx.dragmania.controllers;

import com.mygdx.dragmania.models.GameModel;
import com.mygdx.dragmania.models.LobbyModel;
import com.utilities.GameClient;

public class LobbyController extends Controller {

    private static LobbyController instance = new LobbyController();
    private GameClient client = GameClient.getInstance();
    private LobbyModel model;
    private MetaController metaController = MetaController.getInstance();

    public static LobbyController getInstance() {
        return instance;
    }

    private LobbyController() {
        model = new LobbyModel(-1, false);
    }

    public void connected() {
        model.setIsConnected(true);
    }

    public void disconnected() {
        model.setIsConnected(false);
    }

    public void createGame(String username) {
        client.createGame(username);
        model.setIsHost(true);
    }

    public void joinGame(String username, int roomCode) {
        client.joinGame(username, roomCode);
        model.setIsHost(false);
    }

    public void joinSuccess(int roomCode, String[] usernames) {
        model.setRoomCode(roomCode);
        if (usernames != null)
            for (String username : usernames) {
                model.playerJoinedLobby(username);
            }
    }

    public void errorOccurred(String errorMessage) {
        model.setErrorMessage(errorMessage);
    }

    public void resetModelOnError() {
        model.reset();
    }

    public void playerJoinedLobby(String username) {
        model.playerJoinedLobby(username);
    }

    private boolean areThereEnoughPlayers(int playerCount) {
        return true;
    }

}
