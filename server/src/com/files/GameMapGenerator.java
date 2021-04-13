package com.files;

import com.files.messages.GameMapMessage;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class GameMapGenerator {

    private int MAP_LENGTH;
    private int MIN_POLICEMAN_TURN_POINTS;
    private int MAX_POLICEMAN_TURN_POINTS;
    private int MIN_POLICEMAN_FAKE_TURN_POINTS;
    private int MAX_POLICEMAN_FAKE_TURN_POINTS;
    
    private int CROSSING_PLACEMENTS_LENGTH = 3;
    private int MIN_POLICEMAN_PLACEMENT_SPACING;

    private int[] policemanTurnPoints;
    private int[] policemanFakeTurnPoints;
    private int[] crossingPlacements;
    private ThreadLocalRandom random = ThreadLocalRandom.current();

    public GameMapMessage generateMap(final int MAP_LENGTH) {
        if (MAP_LENGTH < 500) throw new IllegalArgumentException("Map value too low");
        this.MAP_LENGTH = MAP_LENGTH;
        determineMapParameters();
        determineNumberOfPolicemanTurnPoints();
        policemanTurnPoints = generatePolicemanTurnPoints(policemanTurnPoints);
        policemanFakeTurnPoints = generatePolicemanTurnPoints(policemanFakeTurnPoints);
        crossingPlacements = generateRandomCrossings();
        return new GameMapMessage(crossingPlacements, policemanTurnPoints, policemanFakeTurnPoints);
    }

    private void determineMapParameters() {
        MIN_POLICEMAN_TURN_POINTS = (int) MAP_LENGTH / 1000;
        MAX_POLICEMAN_TURN_POINTS = (int) MAP_LENGTH / 500;
        MIN_POLICEMAN_FAKE_TURN_POINTS = MIN_POLICEMAN_TURN_POINTS;
        MAX_POLICEMAN_FAKE_TURN_POINTS = MAX_POLICEMAN_TURN_POINTS;
        MIN_POLICEMAN_PLACEMENT_SPACING = (int) MAP_LENGTH / 50;
    }

    private int[] generateRandomCrossings() {
        int[] crossingPlacements = new int[CROSSING_PLACEMENTS_LENGTH];
        final int MIN_PLACEMENT_SPACING = (int) MAP_LENGTH / 10;
        final int CROSSING_INTERVAL_SIZE = (int) MAP_LENGTH / CROSSING_PLACEMENTS_LENGTH + 1;
        int array_index = 0;
        for (int MIN_PLACEMENT = 0; MIN_PLACEMENT < MAP_LENGTH; MIN_PLACEMENT += CROSSING_INTERVAL_SIZE) {
            int crossingPlacement = random.nextInt(MIN_PLACEMENT + MIN_PLACEMENT_SPACING, MIN_PLACEMENT + CROSSING_INTERVAL_SIZE);
            crossingPlacements[array_index] = crossingPlacement;
            array_index++;
        }
        return crossingPlacements;
    }

    private void determineNumberOfPolicemanTurnPoints() {
        int turnPoints = random.nextInt(MIN_POLICEMAN_TURN_POINTS, MAX_POLICEMAN_TURN_POINTS + 1);
        int fakeTurnPoints = random.nextInt(MIN_POLICEMAN_FAKE_TURN_POINTS, MAX_POLICEMAN_FAKE_TURN_POINTS + 1);
        policemanTurnPoints = new int[turnPoints];
        policemanFakeTurnPoints = new int[fakeTurnPoints];
    }

    private int[] generatePolicemanTurnPoints(int[] turnPointArray) {
        int turnPointIndex = 0;
        while (turnPointIndex < turnPointArray.length) {
            int generatedPoint = random.nextInt(0, MAP_LENGTH);
            if (!isOverlappingWithAnotherPoint(generatedPoint, policemanTurnPoints)
                    && !isOverlappingWithAnotherPoint(generatedPoint, policemanFakeTurnPoints)) {
                turnPointArray[turnPointIndex] = generatedPoint;
                turnPointIndex++;
            }
        }
        Arrays.sort(turnPointArray);
        return turnPointArray;
    }

    private boolean isOverlappingWithAnotherPoint(int turnPoint, int[] turnPointArray) {
        for (int point : turnPointArray) {
            if (turnPoint >= (point - MIN_POLICEMAN_PLACEMENT_SPACING)
                    && turnPoint <= (point + MIN_POLICEMAN_PLACEMENT_SPACING))
                return true;
        }
        return false;
    }

    public int getMinPolicemanTurnPoints() {return this.MIN_POLICEMAN_TURN_POINTS;}

    public int getMaxPolicemanTurnPoints() {return this.MAX_POLICEMAN_TURN_POINTS;}
    
    public int getMinPolicemanFakeTurnPoints() {return this.MIN_POLICEMAN_FAKE_TURN_POINTS;}

    public int getMaxPolicemanFakeTurnPoints() {return this.MAX_POLICEMAN_FAKE_TURN_POINTS;}
    
    public int getNumberOfCrossings() {return this.CROSSING_PLACEMENTS_LENGTH;}

    public int getMapLength() {return this.MAP_LENGTH;}

    public int getMinPolicemanSpacing() {return this.MIN_POLICEMAN_PLACEMENT_SPACING;}
}
