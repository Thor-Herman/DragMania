package com.mygdx.dragmania.models;

import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.utilities.PedestrianGenerationInfo;
import com.utilities.PedestrianType;

public class GameMap {
    
    private Car car;
    private Policeman policeman;
    private final int mapLength;

    private Pedestrian pedestrian;
    private Iterator<Integer> pedestrianPlacements;
    public static final int PEDESTRIAN_CAR_OFFSET = 200;
    private PedestrianGenerationInfo nextPedestrianInfo;

    public GameMap(List<Integer> pedestrianPlacements, List<Integer> policeManTurnPositions, List<Integer> policeManFakeTurnPositions, int mapLength, Car car) {
        this.pedestrianPlacements = pedestrianPlacements.iterator();
        this.car = car;
        policeman = new Policeman(policeManTurnPositions, policeManFakeTurnPositions, car);
        this.mapLength = mapLength;
        setNextPedestrianInfo();
    }

    public void update(float dt) {
        // Check if car has passed crossing with an offset and that there are more crossing to be placed
//        System.out.println(car.getPosition().y + PEDESTRIAN_CAR_OFFSET);
//        System.out.println(this.nextPedestrianInfo.getStartPosition().y);
        if (car.getPosition().y + PEDESTRIAN_CAR_OFFSET > this.nextPedestrianInfo.getStartPosition().y) {
            createPedestrian();
            setNextPedestrianInfo();
        }

        // Delete pedestrian if the car is past it

        checkForCrash();
        policeman.update(dt);
        if (pedestrian != null) {
            pedestrian.update(dt);
        }
    }

    private void createPedestrian() {
        if (pedestrian != null) {
            this.pedestrian.removeCarCrashListener(policeman);
        }
        this.pedestrian = PedestrianFactory.makePedestrian(nextPedestrianInfo.getType(), nextPedestrianInfo.getStartPosition());
        this.pedestrian.addCarCrashListener(policeman);
        setNextPedestrianInfo();
        System.out.println("Pedestrian created");
    }
    
    private void setNextPedestrianInfo() {
        if (this.pedestrianPlacements.hasNext()) {
            this.nextPedestrianInfo = new PedestrianGenerationInfo(
                new Vector2(0, pedestrianPlacements.next()), PedestrianType.STANDARD
            );
        }
    }

    public Policeman getPoliceman() {
        return policeman;
    }

    public int getMapLength() {
        return mapLength;
    }
    
    private void checkForCrash() {
        if (pedestrian != null && pedestrian.collides(car)) {
            pedestrian.fireCarCrashAlarm();
        }
    }

    public Pedestrian getPedestrian() {
        return this.pedestrian;
    }
}
