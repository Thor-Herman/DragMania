package com.mygdx.dragmania.test.models;

import com.mygdx.dragmania.DragMania;
import com.mygdx.dragmania.models.Car;
import com.mygdx.dragmania.models.Policeman;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.dragmania.models.PolicemanAnimation;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.xml.soap.Text;

import static org.junit.Assert.assertEquals;

public class PolicemanTest {

    private Policeman policeman;
    private Car car;

    private ArrayList<Integer> policeManTurnPositions;
    private ArrayList<Integer> policeManFakeTurnPositions;

    @Before
    public void setUp() {
        car = new Car(3);

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
        assertEquals(false, policeman.getHasTurned());
        policeman.checkPolicemanTurn();
        assertEquals(false, policeman.getHasTurned());

        policeman.toggleTurn(true);
        policeman.checkPolicemanTurn();
        assertEquals(true, policeman.getHasTurned());


    }

}
