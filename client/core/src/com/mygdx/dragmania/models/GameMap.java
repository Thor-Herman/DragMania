package com.mygdx.dragmania.models;

import java.util.ArrayList;

public class GameMap {

    private ArrayList<Integer> sideWalkPlacements;
    private ArrayList<Integer> policeManTurnPositions;
    private ArrayList<Integer> policeManFakeTurnPositions;

    private Sidewalk sidewalk;
    private Car car;
    private Policeman policeman;

    private int currentSidewalk;
    public static final int SIDEWALK_OFFSET = 10;

    public GameMap(ArrayList<Integer> sideWalkPlacements, ArrayList<Integer> policeManTurnPositions, ArrayList<Integer> policeManFakeTurnPositions, Car car) {

        this.sideWalkPlacements = sideWalkPlacements;
        this.policeManTurnPositions = policeManTurnPositions;
        this.policeManFakeTurnPositions = policeManFakeTurnPositions;
        this.sidewalk = new Sidewalk(sideWalkPlacements.get(0));
        this.car = car;
        this.currentSidewalk = 0;
        policeman = new Policeman(policeManTurnPositions, policeManFakeTurnPositions, car);

    }

    public int getCurrentSidewalk(){
        return this.currentSidewalk;
    }


    public void update(float dt) {
        // Check if car has passed sidewalk with an offset and that there are more sidewalks to be placed
        if (car.getPosition() + SIDEWALK_OFFSET > sideWalkPlacements.get(getCurrentSidewalk()) && sideWalkPlacements.size()-1 > getCurrentSidewalk()) {
            moveSidewalk(sideWalkPlacements.get(getCurrentSidewalk()+1));
        }
        sidewalk.update(dt);
        policeman.update(dt);
    }

    public Sidewalk getSidewalk() {
        return sidewalk;
    }

    public void moveSidewalk(int yPos) {
        getSidewalk().reposition(yPos);
        this.currentSidewalk++;
    }


}