package com.mygdx.dragmania.models;

import java.util.ArrayList;

import com.utilities.CarType;

public class GameModel {

    private Player player;
    private Car car;
    private int playerScore;
    private int opponentScore;
    private GameMap gameMap;
    
    public GameModel(String username, ArrayList<Integer> crossingPlacements, ArrayList<Integer> policeManTurnTimes, ArrayList<Integer> policeManFakeTurnTimes, int mapLength) {
        player = new Player(username);
        car = CarFactory.makeCar(CarType.NORMAL); // TODO: Pass CarType in dynamically
        car.setMaxVelocity(3);
        gameMap = new GameMap(crossingPlacements, policeManTurnTimes, policeManFakeTurnTimes, mapLength, car);
        opponentScore = 0;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public void update(float dt, boolean isTouching, int opponentScore) {
        this.opponentScore = opponentScore;
        gameMap.update(dt);
        car.update(dt, isTouching);
        playerScore = car.getPosition().y;
    }

    public float getOpponentScore() {
        return opponentScore;
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    public Car getCar() {
        return car;
    }

    public void gameIsUp(boolean won){
        player.gameIsUp(won);
    }
}
