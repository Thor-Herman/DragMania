package tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.files.GameMapGenerator;

public class GameMapGeneratorTest {

    private GameMapGenerator map;

    @Before
    public void setUp() {
        map = new GameMapGenerator();
    }

    @Test
    public void testThatPolicemanTurnPointsDontOverlap() {
        for (int turnPoint : map.getPolicemanTurnPoints()) {
            assertFalse(isOverlappingWithAnotherPoint(turnPoint, map.getPolicemanTurnPoints()));
            assertFalse(isOverlappingWithAnotherPoint(turnPoint, map.getPolicemanFakeTurnPoints()));
        }
    }

    @Test
    public void testThatPolicemanFakeTurnPointsDontOverlap() {
        for (int turnPoint : map.getPolicemanFakeTurnPoints()) {
            assertFalse(isOverlappingWithAnotherPoint(turnPoint, map.getPolicemanTurnPoints()));
            assertFalse(isOverlappingWithAnotherPoint(turnPoint, map.getPolicemanFakeTurnPoints()));
        }
    }

    private boolean isOverlappingWithAnotherPoint(int turnPoint, int[] turnPointArray) {
        for (int point : turnPointArray) {
            if (point == turnPoint)
                continue;
            if (turnPoint >= (point - map.MINIMUM_POLICEMAN_PLACEMENT_SPACING)
                    && turnPoint <= (point + map.MINIMUM_POLICEMAN_PLACEMENT_SPACING))
                return true;
        }
        return false;
    }

    @Test
    public void testPointsAreSorted() {
        assertTrue(arePointsInIncreasingOrder(map.getPolicemanTurnPoints()));
        assertTrue(arePointsInIncreasingOrder(map.getPolicemanFakeTurnPoints()));
        assertTrue(arePointsInIncreasingOrder(map.getCrossings()));

    }

    private boolean arePointsInIncreasingOrder(int[] pointArray) {
        int prevPoint = -1;
        for (int point : pointArray) {
            if (point <= prevPoint)
                return false;
        }
        return true;
    }

    @Test
    public void testCorrectArraySizes() {
        int turnPointsLength = map.getPolicemanTurnPoints().length;
        assertTrue(turnPointsLength >= GameMapGenerator.MIN_POLICEMAN_TURN_POINTS
                && turnPointsLength <= GameMapGenerator.MAX_POLICEMAN_TURN_POINTS);
        int fakeTurnPointsLength = map.getPolicemanFakeTurnPoints().length;
        assertTrue(fakeTurnPointsLength >= GameMapGenerator.MIN_POLICEMAN_TURN_POINTS
                && fakeTurnPointsLength <= GameMapGenerator.MAX_POLICEMAN_TURN_POINTS);
        assertEquals(map.getCrossings().length, GameMapGenerator.CROSSING_PLACEMENTS_LENGTH);
    }
}
