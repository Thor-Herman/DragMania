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

    private Pedestrian pedestrian = null;
    private Iterator<Integer> pedestrianPlacements;
    public static final int PEDESTRIAN_CAR_OFFSET = 700;
    public static final int PEDESTRIAN_CAR_OFFSET_BACKWARD = 100;
    private PedestrianGenerationInfo nextPedestrianInfo;

    public GameMap(List<Integer> pedestrianPlacements, List<Integer> policeManTurnPositions, List<Integer> policeManFakeTurnPositions, int mapLength, Car car) {
        this.pedestrianPlacements = pedestrianPlacements.iterator();
        this.car = car;
        this.policeman = new Policeman(policeManTurnPositions, policeManFakeTurnPositions, car);
        this.mapLength = mapLength;
        if (this.pedestrianPlacements.hasNext()) {
            setNextPedestrianInfo();
        }
    }

    public void update(float dt) {
        // Check if car has passed crossing with an offset and that there are more crossing to be placed
        if (this.nextPedestrianInfo != null && this.pedestrian == null && (car.getTopYPosition() + PEDESTRIAN_CAR_OFFSET > this.nextPedestrianInfo.getStartPosition().y)) {
            createPedestrian();
        }

        if (pedestrian != null) {
            System.out.println("Car hitbox: " + car.getHitBox());
            System.out.println("Pedestrian hitbox: " + pedestrian.getHitBox());
            System.out.println("----");
            pedestrian.setYVelocity(-4*car.getVelocity());
            pedestrian.update(dt);
            if (car.getPosition().y > this.pedestrian.getPosition().y + PEDESTRIAN_CAR_OFFSET_BACKWARD) {
                deletePedestrian();
            }
        }
        checkForCrash();
        policeman.update(dt);
    }

    private void createPedestrian() {
        this.pedestrian = PedestrianFactory.makePedestrian(nextPedestrianInfo.getType(), nextPedestrianInfo.getStartPosition());
        System.out.println("Pedestrian created");
        this.pedestrian.addCarCrashListener(policeman);
        if (this.pedestrianPlacements.hasNext()) {
            setNextPedestrianInfo();
        }
    }

    public void deletePedestrian() {
        this.pedestrian.removeCarCrashListener(policeman);
        this.pedestrian = null;
        this.nextPedestrianInfo = null;
        if (this.pedestrianPlacements.hasNext()) {
            setNextPedestrianInfo();
        }
    }

    public Policeman getPoliceman() {
        return policeman;
    }

    public int getMapLength() {
        return mapLength;
    }
    
    private void setNextPedestrianInfo() {
        this.nextPedestrianInfo = new PedestrianGenerationInfo(new Vector2(0, pedestrianPlacements.next()), PedestrianType.STANDARD);
    }

    private void checkForCrash() {
        if (pedestrian != null && pedestrian.collides(car)) {
            pedestrian.fireCarCrashAlarm();
            deletePedestrian();
        }
    }

    public Pedestrian getPedestrian() {
        return this.pedestrian;
    }
}
