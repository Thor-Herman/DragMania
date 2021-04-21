package com.mygdx.dragmania.models;

import java.util.List;

import com.utilities.CarType;

public class GameModel {

    private Player player;
    private Car car;
    private int playerScore;
    private int opponentScore;
    private GameMap gameMap;
    private boolean isGameOver;


    public GameModel(String username, List<Integer> pedestrianPlacements, List<Integer> policeManTurnTimes, List<Integer> policeManFakeTurnTimes, int mapLength) {
        player = new Player(username);
        car = CarFactory.makeCar(CarType.NORMAL); // TODO: Pass CarType in dynamically
        gameMap = new GameMap(pedestrianPlacements, policeManTurnTimes, policeManFakeTurnTimes, mapLength, car);
        opponentScore = 0;
        isGameOver = false;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public void update(float dt, boolean isTouching) {
        if (isGameOver)
            throw new IllegalStateException("Game is over");
        gameMap.update(dt);
        car.update(dt, isTouching);
        playerScore = (int) car.getPosition().y;
    }

    public void setOpponentScore(int opponentScore) {
        this.opponentScore = opponentScore;
    }

    public int getOpponentScore() {
        return opponentScore;
    }

    public GameMap getGameMap() {
        return gameMap;
    }

    public Car getCar() {
        return car;
    }

    public void gameIsUp(boolean won) {
        player.gameIsUp(won);
        isGameOver = true;
    }

    public boolean getIsGameOver() {
        return isGameOver;
    }
}
