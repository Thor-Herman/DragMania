package com.mygdx.dragmania.test.models;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.dragmania.DragMania;
import com.mygdx.dragmania.models.Car;
import com.mygdx.dragmania.models.CarFactory;
import com.mygdx.dragmania.models.Pedestrian;
import com.mygdx.dragmania.models.PedestrianFactory;
import com.mygdx.dragmania.models.Policeman;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.dragmania.models.PolicemanAnimation;
import com.utilities.CarType;
import com.utilities.PedestrianType;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PolicemanTest {

    private Policeman policeman;
    private Pedestrian pedestrian;
    private Car car;

    private ArrayList<Integer> policeManTurnPositions;
    private ArrayList<Integer> policeManFakeTurnPositions;

    @Before
    public void setUp() {
        car = CarFactory.makeCar(CarType.NORMAL); // TODO: Pass CarType in dynamically
        car.setMaxVelocity(3);

        policeManTurnPositions = new ArrayList<>();
        policeManTurnPositions.add(100);
        policeManFakeTurnPositions = new ArrayList<>();
        policeManFakeTurnPositions.add(150);
        policeman = new Policeman(policeManTurnPositions, policeManFakeTurnPositions, car);

        pedestrian = PedestrianFactory.makePedestrian(PedestrianType.STANDARD, new Vector2(0, 200));
    }

    @After
    public void tearDown() {
        policeman = null;
    }

    // Tests raises NullPointerException because libgdx features (Texture) is not initialized in the JUnit environment
    // To run test, comment out the initialization of policemanAnimation in Policeman
    @Test
    public void testCheckPolicemanTurn() {

        assertEquals("Away", policeman.getHasTurned());
        policeman.checkPolicemanTurn();
        assertEquals("Away", policeman.getHasTurned());

        policeman.toggleTurn("Towards");
        policeman.checkPolicemanTurn();
        assertEquals("Towards", policeman.getHasTurned());


    @Test
    public void testCarCrashListener() {
        setUp();

        pedestrian.addCarCrashListener(policeman);
        float startPosition = pedestrian.getPosition().y;

        //Check that the car can drive, and that the speeds are correct
        assertTrue(car.getDrivingStatus());

        car.update(1, true);
        //Check that the car's velocity increases as time passes by, given that isTouching is set to true.
        assertTrue(car.getVelocity() == 1);

        for (int i = 2; i < 10; i++) {
            car.update(i, true);
            pedestrian.update(i);
            assertTrue(pedestrian.getVelocity() > 0);
            if (pedestrian.collides(car)) {
                pedestrian.crashed();
                //Check that the car's speed is set to 0 when it collides with a pedestrian
                assertTrue(car.getVelocity() == 0);
                //Check that pedestrian is repositioned when after collission with a car
                assertTrue(pedestrian.getPosition().y > startPosition);
                assertTrue(pedestrian.getPosition().x == 0);
            }
        }
    }
}
