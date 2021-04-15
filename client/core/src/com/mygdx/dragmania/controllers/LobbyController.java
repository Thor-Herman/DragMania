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
        client.setup();
        model = new LobbyModel(-1, false);
    }

    public void connected() {
        model.setIsConnected(true);
        System.out.println(model);
    }

    public void disconnected() {
        model.setIsConnected(false);
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
                model.playerJoinedLobby(username);
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
        System.out.println(model);
    }

    private boolean areThereEnoughPlayers(int playerCount) {
        return true;
    }

}
