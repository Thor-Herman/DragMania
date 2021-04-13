package tests;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.files.messages.GameMapMessage;
import com.files.GameMapGenerator;

public class GameMapGeneratorTest {

    private GameMapMessage map;
    private GameMapGenerator generator;

    @Before
    public void setUp() {
        generator = new GameMapGenerator();
        map = generator.generateMap(5000);
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
            if (turnPoint >= (point - generator.getMinPolicemanSpacing())
                    && turnPoint <= (point + generator.getMinPolicemanSpacing()))
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
        assertTrue(turnPointsLength >= generator.getMinPolicemanFakeTurnPoints()
                && turnPointsLength <= generator.getMaxPolicemanFakeTurnPoints());
        int fakeTurnPointsLength = map.getPolicemanFakeTurnPoints().length;
        assertTrue(fakeTurnPointsLength >= generator.getMinPolicemanFakeTurnPoints()
                && fakeTurnPointsLength <= generator.getMaxPolicemanFakeTurnPoints());
        assertEquals(map.getCrossings().length, generator.getNumberOfCrossings());
    }
}
