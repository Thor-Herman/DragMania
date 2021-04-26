package com.mygdx.dragmania.models;

import java.util.ArrayList;

public class GameMap {

    private ArrayList<Integer> pedestrianPlacements;

    private Car car;
    private Policeman policeman;
    private final int mapLength;

    public GameMap(ArrayList<Integer> pedestrianPlacements, ArrayList<Integer> policeManTurnPositions, ArrayList<Integer> policeManFakeTurnPositions, int mapLength, Car car) {
        this.pedestrianPlacements = pedestrianPlacements;
        this.car = car;
        policeman = new Policeman(policeManTurnPositions, policeManFakeTurnPositions, car);
        this.mapLength = mapLength;
    }

    public void update(float dt) {
        policeman.update(dt);
    }

    public Policeman getPoliceman() {
        return policeman;
    }

    public int getMapLength() {
        return mapLength;
    }
}
