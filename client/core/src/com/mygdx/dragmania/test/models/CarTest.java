package com.mygdx.dragmania.test.models;

import com.mygdx.dragmania.models.Car;
import com.mygdx.dragmania.models.CarFactory;
import com.utilities.CarType;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CarTest {

    private Car car;
    private double delta = 0.01;
    private float startPosY;
    
    @Before
    public void setUp() {
        float width = 40;
        float height = 100;
        int acceleration = 1;
        int maxVelocity = 3;
        car = new Car(width, height, acceleration, maxVelocity);
        startPosY = car.getStartPosition().y;
    }

    @After
    public void tearDown() {
        car = null;
    }

    @Test
    public void testCarInstantiation() {
        assertEquals(car.getStartPosition(), car.getPosition());
        assertEquals(0, car.getVelocity(), delta);
        assertEquals(3, car.getMaxVelocity(), delta);
    }

    @Test
    public void testCarFactory() {
        // Check that the car was made successfully (with check for positive acceleration)
        car = CarFactory.makeCar(CarType.NORMAL);
        assertTrue(car.getAcceleration() > 0);
    }

    @Test
    public void testUpdate() {
        // position.y: startPosY, velocity: 0, maxVelocity: 3

        car.update(1, true);
        assertEquals(1, car.getVelocity(), delta);
        assertEquals(1, car.getPosition().y - startPosY, delta); // position = 0 + 1*1 = 1

        car.update(2, true);
        assertEquals(2, car.getVelocity(), delta);
        assertEquals(5, car.getPosition().y - startPosY, delta); // position = 1 + 2*2 = 5

        car.update(3, true);
        assertEquals(3, car.getVelocity(), delta);
        assertEquals(14, car.getPosition().y - startPosY, delta); // position = 5 + 3*3 = 14

        car.update(4, true);
        assertEquals(3, car.getVelocity(), delta); // max velocity reached
        assertEquals(26, car.getPosition().y - startPosY, delta); // position = 14 + 3*4 = 26

        car.update(5, false);
        assertEquals(2, car.getVelocity(), delta);
        assertEquals(36, car.getPosition().y - startPosY, delta); // position = 26 + 2*5 = 36

        car.update(6, false);
        assertEquals(1, car.getVelocity(), delta);
        assertEquals(42, car.getPosition().y - startPosY, delta); // position = 36 + 1*6 = 42

        car.update(7, false);
        assertEquals(0, car.getVelocity(), delta);
        assertEquals(42, car.getPosition().y - startPosY, delta); // position = 42 + 0*7 = 42
    }

    @Test
    public void testUpdateMaxVelocity1() {
        car.setMaxVelocity(1);
        
        // position.y: startPosY, velocity: 0
        car.update(1, true);
        assertEquals(1, car.getVelocity(), delta);
        assertEquals(1, car.getPosition().y - startPosY, delta); // position = 0 + 1*1 = 1
    
        car.update(2, true);
        assertEquals(1, car.getVelocity(), delta); // max velocity reached
        assertEquals(3, car.getPosition().y - startPosY, delta); // position = 1 + 1*2 = 3
        
        car.update(3, false);
        assertEquals(0, car.getVelocity(), delta);
        assertEquals(3, car.getPosition().y - startPosY, delta); // position = 3 + 3*0 = 3
    }
}
