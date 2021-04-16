package com.mygdx.dragmania.controllers;

import java.util.ArrayList;

import com.mygdx.dragmania.models.GameModel;
import com.utilities.GameClient;

public class GameController extends Controller {
    
    private static GameController instance;
    private MetaController metaController;
    private GameClient client;

    private GameModel model;

    private GameController() {
        this.client = GameClient.getInstance();
        this.metaController = MetaController.getInstance();
    }

    public GameController getInstance(){
        if (instance == null) {
            instance  = new GameController();
        }
        return instance;
    }

    public void newGame(String username, ArrayList<Integer> crossingPlacements, ArrayList<Integer> policeManTurnTimes,
            ArrayList<Integer> policeManFakeTurnTimes) {
        model = new GameModel(username, crossingPlacements, policeManTurnTimes, policeManFakeTurnTimes);
    }

    public void update(float dt, boolean isTouching) {
        model.update(dt, isTouching);
    }

    public void setOpponentScore(int opponentScore){
        model.setOpponentScore(opponentScore);
    }
}
