package com.mygdx.dragmania.test.models;

import com.mygdx.dragmania.models.Car;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CarTest {

    private Car car;

    @Before
    public void setUp() {
        int maxVelocity = 3;
        car = new Car(maxVelocity);
    }

    @After
    public void tearDown() {
        car = null;
    }

    @Test
    public void testCarInstantiation() {
        assertEquals(0, car.getPosition());
        assertEquals(0, car.getVelocity());
        assertEquals(3, car.getMaxVelocity());
    }

    @Test
    public void testAccelerate() {
        assertEquals(0, car.getVelocity());
        car.accelerate(true);
        assertEquals(1, car.getVelocity());
    }
    
    @Test
    public void testUpdate() {
        // position: 0, velocity: 0, maxVelocity: 3
        car.update(1, true);
        assertEquals(1, car.getVelocity());
        assertEquals(1, car.getPosition()); // position = 0 + 1*1 = 1

        car.update(2, true);
        assertEquals(2, car.getVelocity());
        assertEquals(5, car.getPosition()); // position = 1 + 2*2 = 5

        car.update(3, true);
        assertEquals(3, car.getVelocity());
        assertEquals(14, car.getPosition()); // position = 5 + 3*3 = 14

        car.update(4, true);
        assertEquals(3, car.getVelocity()); // max velocity reached
        assertEquals(26, car.getPosition()); // position = 14 + 3*4 = 26

        car.update(5, false);
        assertEquals(2, car.getVelocity());
        assertEquals(36, car.getPosition()); // position = 26 + 2*5 = 36

        car.update(6, false);
        assertEquals(1, car.getVelocity());
        assertEquals(42, car.getPosition()); // position = 36 + 1*6 = 42

        car.update(7, false);
        assertEquals(0, car.getVelocity());
        assertEquals(42, car.getPosition()); // position = 42 + 0*7 = 42
    }

    @Test
    public void testUpdateMaxVelocity1() {
        int maxVelocity = 1;
        car = new Car(maxVelocity);

        // position: 0, velocity: 0
        car.update(1, true);
        assertEquals(1, car.getVelocity());
        assertEquals(1, car.getPosition()); // position = 0 + 1*1 = 1

        car.update(2, true);
        assertEquals(1, car.getVelocity()); // max velocity reached
        assertEquals(3, car.getPosition()); // position = 1 + 1*2 = 3

        car.update(3, false);
        assertEquals(0, car.getVelocity());
        assertEquals(3, car.getPosition()); // position = 3 + 3*0 = 3
    }
}