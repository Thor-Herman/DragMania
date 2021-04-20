package com.mygdx.dragmania.test.models;

import com.mygdx.dragmania.DragMania;
import com.mygdx.dragmania.models.Car;
import com.mygdx.dragmania.models.Policeman;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.dragmania.models.PolicemanAnimation;
import com.utilities.CarType;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


import static org.junit.Assert.assertEquals;

public class PolicemanTest {

    private Policeman policeman;

    private ArrayList<Integer> policeManTurnPositions;
    private ArrayList<Integer> policeManFakeTurnPositions;

    @Before
    public void setUp() {
        Car car = CarFactory.makeCar(CarType.NORMAL); // TODO: Pass CarType in dynamically
        car.setMaxVelocity(3);

        policeManTurnPositions = new ArrayList<>();
        policeManTurnPositions.add(100);
        policeManFakeTurnPositions = new ArrayList<>();
        policeManFakeTurnPositions.add(150);

        policeman = new Policeman(policeManTurnPositions, policeManFakeTurnPositions, car);
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


    }

}
