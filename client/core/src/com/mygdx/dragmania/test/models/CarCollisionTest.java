package com.mygdx.dragmania.test.models;

import com.mygdx.dragmania.models.Car;

import org.junit.Test;
import static org.junit.Assert.fail;


public class CarCollisionTest {

    @Test
    public void testCollision() {
        float width = 40;
        float height = 100;
        int acceleration = 1;
        int maxVelocity = 1;
        Car car1 = new Car(width, height, acceleration, maxVelocity);
        Car car2 = null;

        boolean collided = false;

        for (int i = 0; i < 70; i++) {
            car1.update(i, true);
            System.out.println("Car1: " + car1.getPosition().y);
            if (i > 40) {
                if (car2 == null) {
                    maxVelocity = 8;
                    car2 = new Car(width, height, acceleration, maxVelocity);
                }
                car2.update(i - 40, true);
                System.out.println("Car2: " + car2.getPosition().y);
            }
            if (car2 != null && car1.collides(car2)) {
                collided = true;
                System.out.println("Car 2 collided with car 1");
                break;
            }
        }
        if (!collided) {
            fail("The cars should have collided.");
        }
    }
}