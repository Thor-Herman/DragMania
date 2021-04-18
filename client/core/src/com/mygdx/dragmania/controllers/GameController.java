package com.mygdx.dragmania.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.mygdx.dragmania.models.GameModel;
import com.utilities.GameClient;
import com.utilities.messages.GameMapMessage;

public class GameController extends Controller {

    private static GameController instance;
    private GameClient client;
    private GameModel model;
    private static final float SCORE_SEND_FREQUENCY = 250;
    private float timePassedSinceScoreSent = 0;

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
        model = new GameModel(username, crossingPlacements, policeManTurnTimes, policeManFakeTurnTimes, 2000); // TODO:
                                                                                                               // Change
                                                                                                               // map
                                                                                                               // size
    }

    public void update(float dt, boolean isTouching) {
        if (model.getIsGameOver())
            return; // TODO: Change
        else if (model.getCar().getPosition() > model.getMapLength()) // TODO: Is this the right place for this?
            client.sendGameOver();
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

    public void disconnected() {
        model = null;
        LobbyController.getInstance().disconnected();
        back();
        // TODO: Remove listener from client
    }

    public static void main(String[] args) throws InterruptedException {
        LobbyController lobbyController = LobbyController.getInstance();
        lobbyController.connectToServer();
        lobbyController.createGame("TH");
        Thread.sleep(8000);
        GameController gameController = GameController.getInstance();

        new Thread() {
            public void run() {
                long now;
                long updateTime;
                long wait;

                final int TARGET_FPS = 60;
                final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;

                while (true) {
                    now = System.nanoTime();
                    updateTime = System.nanoTime() - now;
                    wait = (OPTIMAL_TIME - updateTime) / 1000000;
                    gameController.update(wait, true);

                    try {
                        Thread.sleep(wait);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

}
