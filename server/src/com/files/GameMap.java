package com.files;

public class GameMap {

    private int[] policemanTurnPoints;
    private int[] policemanFakeTurnPoints;
    private int[] crossingPlacements;

    public GameMap(int[] crossingPlacements, int[] policemanTurnPoints, int[] policemanFakeTurnPoints) {
        this.crossingPlacements = crossingPlacements;
        this.policemanTurnPoints = policemanTurnPoints;
        this.policemanFakeTurnPoints = policemanFakeTurnPoints;
    }

    public int[] getCrossings() {
        return this.crossingPlacements;
    }

    public int[] getPolicemanTurnPoints() {
        return this.policemanTurnPoints;
    }

    public int[] getPolicemanFakeTurnPoints() {
        return this.policemanFakeTurnPoints;
    }
}
