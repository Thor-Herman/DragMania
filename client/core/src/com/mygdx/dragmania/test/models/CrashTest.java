package com.mygdx.dragmania.test.models;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.dragmania.models.Car;
import com.mygdx.dragmania.models.CarFactory;
import com.mygdx.dragmania.models.GameMap;
import com.mygdx.dragmania.models.Pedestrian;
import com.utilities.CarType;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CrashTest {

    @Test
    public void pedestrianCarCrashTest() {
        Car car = CarFactory.makeCar(CarType.NORMAL);
        List<Integer> pedestrianPlacements = Arrays.asList(1000, 2000, 3000, 4000, 5000);
        List<Integer> policemanTurnTimes = Arrays.asList(10000);

        GameMap map = new GameMap(pedestrianPlacements, policemanTurnTimes, policemanTurnTimes, car); 
        
        for (int i = 0; i < 70; i++) {
            map.update(i);
            car.update(i, true);
            System.out.println("Car position: " + car.getPosition());
            Pedestrian pedestrian = map.getPedestrian();
            if (pedestrian != null) {
                System.out.println("Pedestrian position: " + pedestrian.getPosition());
            }
        }
    }
}