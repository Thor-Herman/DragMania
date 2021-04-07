package com.files;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class GameMapGenerator {

    public static final int MIN_POLICEMAN_TURN_POINTS = 5;
    public static final int MAX_POLICEMAN_TURN_POINTS = 10;

    public static final int MIN_POLICEMAN_FAKE_TURN_POINTS = 5;
    public static final int MAX_POLICEMAN_FAKE_TURN_POINTS = 10;

    public static final int MAP_LENGTH = 5000;
    public static final int CROSSING_PLACEMENTS_LENGTH = 3;
    public static final int MINIMUM_POLICEMAN_PLACEMENT_SPACING = (int) MAP_LENGTH / 50;

    private int[] policemanTurnPoints;
    private int[] policemanFakeTurnPoints;
    private int[] crossingPlacements;
    private ThreadLocalRandom random = ThreadLocalRandom.current();

    public GameMap generateMap() {
        determineNumberOfPolicemanTurnPoints();
        generatePolicemanTurnPoints(policemanTurnPoints);
        generatePolicemanTurnPoints(policemanFakeTurnPoints);
        crossingPlacements = generateRandomCrossings();
        return new GameMap(crossingPlacements, policemanTurnPoints, policemanFakeTurnPoints);
    }

    private int[] generateRandomCrossings() {
        int[] crossingPlacements = new int[CROSSING_PLACEMENTS_LENGTH];
        final int MINIMUM_PLACEMENT_SPACING = (int) MAP_LENGTH / 10;
        final int CROSSING_INTERVAL_SIZE = (int) MAP_LENGTH / CROSSING_PLACEMENTS_LENGTH + 1;
        int array_index = 0;
        for (int i = 0; i < MAP_LENGTH; i += CROSSING_INTERVAL_SIZE) {
            int crossingPlacement = random.nextInt(i + MINIMUM_PLACEMENT_SPACING, i + CROSSING_INTERVAL_SIZE);
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

    private void generatePolicemanTurnPoints(int[] turnPointArray) {
        int turnPointIndex = 0;
        while (!(turnPointIndex < turnPointArray.length)) {
            int generatedPoint = random.nextInt(0, MAP_LENGTH);
            if (!isOverlappingWithAnotherPoint(generatedPoint, policemanTurnPoints)
                    && !isOverlappingWithAnotherPoint(generatedPoint, policemanFakeTurnPoints)) {
                turnPointArray[turnPointIndex] = generatedPoint;
                turnPointIndex++;
            }
        }
        Arrays.sort(turnPointArray);
    }

    private boolean isOverlappingWithAnotherPoint(int turnPoint, int[] turnPointArray) {
        for (int point : turnPointArray) {
            if (turnPoint >= (point - MINIMUM_POLICEMAN_PLACEMENT_SPACING)
                    && turnPoint <= (point + MINIMUM_POLICEMAN_PLACEMENT_SPACING))
                return true;
        }
        return false;
    }
}
