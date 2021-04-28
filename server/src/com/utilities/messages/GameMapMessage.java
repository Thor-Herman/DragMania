package com.utilities.messages;

public class GameMapMessage extends Message {

    private int[] policemanTurnPoints;
    private int[] policemanFakeTurnPoints;
    private int[] crossingPlacements;
    private int mapLength;

    public GameMapMessage() {
    }

    public GameMapMessage(int[] crossingPlacements, int[] policemanTurnPoints, int[] policemanFakeTurnPoints,
            int mapLength) {
        this.crossingPlacements = crossingPlacements;
        this.policemanTurnPoints = policemanTurnPoints;
        this.policemanFakeTurnPoints = policemanFakeTurnPoints;
        this.mapLength = mapLength;
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

    public int getMapLength() {
        return this.mapLength;
    }
}
