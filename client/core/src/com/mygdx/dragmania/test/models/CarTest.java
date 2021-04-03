package com.mygdx.dragmania.test.models;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.dragmania.models.Car;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CarTest {

    private Car car;
    private Texture texture;

    @Before
    public void setUp() {
        int maxVelocity = 3;
        texture = new Texture("textures/cars/car_red.png");
        car = new Car(maxVelocity, texture);
    }

    @After
    public void tearDown() {
        car = null;
    }

    @Test
    public void testCarInstantiation() {
        assertEquals(0, car.getPosition().x);
        assertEquals(0, car.getPosition().y);
        assertEquals(0, car.getVelocity());
        assertEquals(3, car.getMaxVelocity());
    }

    @Test
    public void testUpdate() {
        // position.y: 0, velocity: 0, maxVelocity: 3
        car.update(1, true);
        assertEquals(1, car.getVelocity());
        assertEquals(1, car.getPosition().y); // position = 0 + 1*1 = 1

        car.update(2, true);
        assertEquals(2, car.getVelocity());
        assertEquals(5, car.getPosition().y); // position = 1 + 2*2 = 5

        car.update(3, true);
        assertEquals(3, car.getVelocity());
        assertEquals(14, car.getPosition().y); // position = 5 + 3*3 = 14

        car.update(4, true);
        assertEquals(3, car.getVelocity()); // max velocity reached
        assertEquals(26, car.getPosition().y); // position = 14 + 3*4 = 26

        car.update(5, false);
        assertEquals(2, car.getVelocity());
        assertEquals(36, car.getPosition().y); // position = 26 + 2*5 = 36

        car.update(6, false);
        assertEquals(1, car.getVelocity());
        assertEquals(42, car.getPosition().y); // position = 36 + 1*6 = 42

        car.update(7, false);
        assertEquals(0, car.getVelocity());
        assertEquals(42, car.getPosition().y); // position = 42 + 0*7 = 42
    }

    @Test
    public void testUpdateMaxVelocity1() {
        int maxVelocity = 1;
        car = new Car(maxVelocity, texture);

        // position.y: 0, velocity: 0
        car.update(1, true);
        assertEquals(1, car.getVelocity());
        assertEquals(1, car.getPosition().y); // position = 0 + 1*1 = 1

        car.update(2, true);
        assertEquals(1, car.getVelocity()); // max velocity reached
        assertEquals(3, car.getPosition().y); // position = 1 + 1*2 = 3

        car.update(3, false);
        assertEquals(0, car.getVelocity());
        assertEquals(3, car.getPosition().y); // position = 3 + 3*0 = 3
    }
}
