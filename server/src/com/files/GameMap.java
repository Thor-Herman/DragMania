package src.com.files;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class GameMap {

    public static final int MIN_POLICEMAN_TURN_TIMES = 5;
    public static final int MAX_POLICEMAN_TURN_TIMES = 10;

    public static final int MIN_POLICEMAN_FAKE_TURN_TIMES = 5;
    public static final int MAX_POLICEMAN_FAKE_TURN_TIMES = 10;

    public static final int MAP_LENGTH = 5000;

    private final int CROSSING_PLACEMENTS_LENGTH = 3;
    private int[] policemanTurnTimes;
    private int[] policemanFakeTurnTimes = {};

    private ThreadLocalRandom random = ThreadLocalRandom.current();

    public GameMap() {
        determinePoliceManTurnTimes();
        int[] crossingPlacements = generateRandomCrossings();
    }

    private int[] generateRandomCrossings() {
        int[] crossingPlacements = new int[CROSSING_PLACEMENTS_LENGTH];
        final int MINIMUM_PLACEMENT_SPACING = (int) MAP_LENGTH / 10;
        final int CROSSING_INTERVAL_SIZE = (int) MAP_LENGTH / crossingPlacements.length;
        int array_index = 0;
        for (int i = 0; i < MAP_LENGTH; i += CROSSING_INTERVAL_SIZE) {
            int crossingPlacement = random.nextInt(i + MINIMUM_PLACEMENT_SPACING, i + CROSSING_INTERVAL_SIZE);
            crossingPlacements[array_index] = crossingPlacement;
            array_index++;
        }
        return crossingPlacements;
    }

    public void determinePoliceManTurnTimes() {
        int turnTimes = random.nextInt(MIN_POLICEMAN_TURN_TIMES, MAX_POLICEMAN_TURN_TIMES + 1);
        int fakeTurnTimes = random.nextInt(MIN_POLICEMAN_FAKE_TURN_TIMES, MAX_POLICEMAN_FAKE_TURN_TIMES + 1);
        policemanTurnTimes = new int[turnTimes];
        policemanFakeTurnTimes = new int[fakeTurnTimes];
    }
}
