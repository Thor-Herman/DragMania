package com.mygdx.dragmania.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.sound.sampled.SourceDataLine;

import com.mygdx.dragmania.models.GameModel;
import com.utilities.GameClient;
import com.utilities.messages.GameMapMessage;

public class GameController extends Controller {

    private static GameController instance;
    private MetaController metaController;
    private GameClient client;

    private GameModel model;
    private float timePassedSinceScoreSent = 0;
    private static final float SCORE_SEND_FREQUENCY = 250;

    private GameController() {
        this.client = GameClient.getInstance();
        this.metaController = MetaController.getInstance();
    }

    public static GameController getInstance() {
        if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }

    public void newGame(GameMapMessage map) {
        List<Integer> list = Arrays.stream(map.getCrossings()).boxed().collect(Collectors.toList());
        ArrayList<Integer> crossingPlacements = new ArrayList<>(list);
        list = Arrays.stream(map.getPolicemanTurnPoints()).boxed().collect(Collectors.toList());
        ArrayList<Integer> policeManTurnTimes = new ArrayList<>(list);
        list = Arrays.stream(map.getPolicemanFakeTurnPoints()).boxed().collect(Collectors.toList());
        ArrayList<Integer> policeManFakeTurnTimes = new ArrayList<>(list);
        String username = ""; // TODO: Change
        model = new GameModel(username, crossingPlacements, policeManTurnTimes, policeManFakeTurnTimes);
    }

    public void update(float dt, boolean isTouching) {
        // model.update(dt, isTouching);
        timePassedSinceScoreSent += dt;
        if (timePassedSinceScoreSent >= SCORE_SEND_FREQUENCY) {
            // client.sendScore(model.getPlayerScore());
            client.sendScore(50);
            timePassedSinceScoreSent = 0;
        }
    }

    public void setOpponentScore(int opponentScore) {
        model.setOpponentScore(opponentScore);
    }

    public void disconnected() {
        model = null;
        LobbyController.getInstance().disconnected();
        // back();
    }

}
