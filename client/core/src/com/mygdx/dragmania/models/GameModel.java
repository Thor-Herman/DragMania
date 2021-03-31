package com.mygdx.dragmania.models;

import java.util.ArrayList;

public class GameModel {
    private Player player;
    //private Policeman policeman;
    private Car car;
    private int playerScore;
    private int opponentScore;
    private GameMap gameMap;

    public GameModel(String username, ArrayList<Integer> sideWalkPlacements, ArrayList<Integer> policeManTurnTimes, ArrayList<Integer> policeManFakeTurnTimes) {
        player = new Player(username);
        //policeman = new Policeman();
        car = new Car();
        // Circular dependency?
        gameMap = new GameMap(sideWalkPlacements, policeManTurnTimes, policeManFakeTurnTimes, car);
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public void update(float dt, boolean isTouching, int opponentScore) {
        this.opponentScore = opponentScore;
        gameMap.update(dt);
        car.update(dt, isTouching);
        playerScore = car.getPosition();
    }

    public int getOpponentScore() {
        return opponentScore;
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    /*
    public Policeman getPoliceman() {
        return policeman;
    }
     */

    public Car getCar() {
        return car;
    }

    public void gameIsUp(boolean won){
        player.gameIsUp(won);
    }
}
