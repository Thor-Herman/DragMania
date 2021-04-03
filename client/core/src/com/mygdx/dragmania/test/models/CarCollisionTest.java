package com.mygdx.dragmania.test.models;

import com.mygdx.dragmania.models.Car;

import com.badlogic.gdx.graphics.Texture;

import org.junit.Test;

import static org.junit.Assert.fail;

public class CarCollisionTest {

    @Test
    public void testCollision() {
        Texture texture = new Texture("textures/cars/car_red.png");
        Car car1 = new Car(2, texture);
        Car car2 = new Car(6, texture);

        boolean collided = false;

        for (int i = 0; i < 20; i++) {
            car1.update(i, true);
            if (i > 5) {
                car2.update(i, true);
            }
            if (car1.collides(car2)) {
                collided = true;
                System.out.println("Car 2 collided with car 1");
                System.out.println(i);
                break;
            }
        }
        
        if (!collided) {
            fail("The cars should have collided");
        }
    }
}