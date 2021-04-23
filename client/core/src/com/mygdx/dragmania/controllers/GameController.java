package com.mygdx.dragmania.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.badlogic.gdx.Gdx;
import com.mygdx.dragmania.models.GameModel;
import com.mygdx.dragmania.views.GameFinishedView;
import com.mygdx.dragmania.views.GameView;
import com.mygdx.dragmania.views.ViewManager;
import com.utilities.GameClient;
import com.utilities.messages.GameMapMessage;

public class GameController {

    private static GameController instance;
    private GameClient client;
    private GameModel model;
    private static final float SCORE_SEND_FREQUENCY = 0.250f;
    private float timePassedSinceScoreSent = 0;
    private boolean isSendingGameOver = false;

    private GameController() {
        this.client = GameClient.getInstance();
    }

    public static GameController getInstance() {
        if (instance == null) {
            instance = new GameController();
        }
        return instance;
    }

    public void readyUp() {
        client.readyUp();
    }

    public void receiveGameMap(GameMapMessage map) {
        List<Integer> list = Arrays.stream(map.getCrossings()).boxed().collect(Collectors.toList());
        ArrayList<Integer> crossingPlacements = new ArrayList<>(list);
        list = Arrays.stream(map.getPolicemanTurnPoints()).boxed().collect(Collectors.toList());
        ArrayList<Integer> policeManTurnTimes = new ArrayList<>(list);
        list = Arrays.stream(map.getPolicemanFakeTurnPoints()).boxed().collect(Collectors.toList());
        ArrayList<Integer> policeManFakeTurnTimes = new ArrayList<>(list);
        String username = ""; // TODO: Change
        System.out.println(map.toString());
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                model = new GameModel(username, crossingPlacements, policeManTurnTimes, policeManFakeTurnTimes, 200); // TODO: Change map size
                ViewManager viewManager = ViewManager.getInstance();
                viewManager.push(new GameView(viewManager));
            }
        });
    }

    public void update(float dt, boolean isTouching) {
        if (model == null || model.getIsGameOver()) {
            return;
        }
        boolean hasCrossedFinishedLine = model.getCar().getPosition().y > model.getGameMap().getMapLength();
        if (hasCrossedFinishedLine && !isSendingGameOver) {
            isSendingGameOver = true;
            client.sendGameOver();
        }
        model.update(dt, isTouching);
        timePassedSinceScoreSent += dt;
        if (timePassedSinceScoreSent >= SCORE_SEND_FREQUENCY) {
            client.sendScore(model.getPlayerScore());
            timePassedSinceScoreSent = 0;
        }
    }

    public void setOpponentScore(int opponentScore) {
        model.setOpponentScore(opponentScore);
        System.out.println("Score: " + opponentScore);
    }

    public void gameOver(boolean won) {
        ViewManager viewManager = ViewManager.getInstance();
        Gdx.app.postRunnable(new Runnable() {
            @Override
            public void run() {
                viewManager.push(new GameFinishedView(viewManager));
            }
        });
        model.gameIsUp(won);
        model = null;
        isSendingGameOver = false;
    }


    public void disconnected() {
        model = null;
        LobbyController.getInstance().disconnected();
        // TODO: Remove listener from client
    }

    public GameModel getModel() {
        return this.model;
    }
}
