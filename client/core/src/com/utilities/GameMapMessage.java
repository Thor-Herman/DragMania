package com.utilities;

public class GameMapMessage extends Message {

    private int[] policemanTurnPoints;
    private int[] policemanFakeTurnPoints;
    private int[] crossingPlacements;

    public GameMapMessage() {}

    public GameMapMessage(int[] crossingPlacements, int[] policemanTurnPoints, int[] policemanFakeTurnPoints) {
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
