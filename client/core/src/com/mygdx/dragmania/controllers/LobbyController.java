package com.mygdx.dragmania.controllers;

import com.mygdx.dragmania.models.GameModel;
import com.utilities.GameClient;

public class LobbyController extends Controller {

    private GameClient client = GameClient.getInstance();
    private LobbyModel model;
    private MetaController metaController = MetaController.getInstance();

    public LobbyController() {
        this.model = new LobbyModel();
    }

    public void createGame(String username) {}

    public void joinGame(String username, int roomCode) {}

    public void joinSuccess() {}

    public void joinTimeout() {}

    public void playerJoinedLobby() {}

    private boolean areThereEnoughPlayers(int playerCount) {return true;}

}
