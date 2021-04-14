package com.mygdx.dragmania.models;

import java.util.ArrayList;

public class GameMap {

    private ArrayList<Integer> crossingPlacements;

    private Crossing crossing;
    private Car car;
    private Policeman policeman;

    private int currentCrossing;
    public static final int CROSSING_OFFSET = 10;

    public GameMap(ArrayList<Integer> crossingPlacements, ArrayList<Integer> policeManTurnPositions, ArrayList<Integer> policeManFakeTurnPositions, Car car) {
        this.crossingPlacements = crossingPlacements;
        this.crossing = new Crossing(crossingPlacements.get(0));
        this.car = car;
        this.currentCrossing = 0;
        policeman = new Policeman(policeManTurnPositions, policeManFakeTurnPositions, car);
    }

    public int getCurrentCrossing(){
        return this.currentCrossing;
    }


    public void update(float dt) {
        // Check if car has passed crossing with an offset and that there are more crossing to be placed
        if (car.getPosition() > crossingPlacements.get(getCurrentCrossing()) + CROSSING_OFFSET && crossingPlacements.size()-1 > getCurrentCrossing()) {
            moveCrossing(crossingPlacements.get(getCurrentCrossing()+1));
        }
        crossing.update(dt);
        policeman.update(dt);
    }

    public Crossing getCrossing() {
        return crossing;
    }

    public void moveCrossing(int yPos) {
        getCrossing().reposition(yPos);
        this.currentCrossing++;
    }


}
