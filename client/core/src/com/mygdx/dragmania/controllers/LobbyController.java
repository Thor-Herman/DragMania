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

    private LobbyController() {}

    public void createGame(String username) {}

    public void joinGame(String username, int roomCode) {}

    public void joinSuccess() {}

    public void joinTimeout() {}

    public void playerJoinedLobby() {}

    private boolean areThereEnoughPlayers(int playerCount) {return true;}

}
