package com.mygdx.dragmania.models;

import com.mygdx.dragmania.models.Crossing;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.utilities.CarCrashListener;
import com.utilities.CarType;

import java.util.ArrayList;

public class Policeman implements CarCrashListener {

    private String hasTurned;
    private PolicemanAnimation policemanAnimation;
    private Vector2 position;

    private ArrayList<Integer> policemanTurnPositions;
    private ArrayList<Integer> policemanFakeTurnPositions;

    private Car car;
    private int currentTurnPositionIndex;
    private int currentFakeTurnPositionIndex;

    private long policemanTurnStart;
    private long policemanTurnStop;
    private long timePenaltyStart;
    private long timePenaltyStop;
    private long policeTurnDelayStart;
    private long policeFakeTurnDelayStart;

    // Duration in milliseconds
    private int timePenaltyDuration;
    private int policeTurnDuration;
    private int timePenaltyDuration2 =  1000;

    // Not correct position
    public static final int xPos = 200;
    public static final int yPos = 800;


    public Policeman(ArrayList<Integer> policemanTurnPositions, ArrayList<Integer> policemanFakeTurnPositions, Car car) {

        this.position = new Vector2(xPos, yPos);
        this.hasTurned = "Away";
        // The line below can be commented out to run PolicemanTest
        //this.policemanAnimation = new PolicemanAnimation();
        this.policemanTurnPositions = policemanTurnPositions;
        this.policemanFakeTurnPositions = policemanFakeTurnPositions;
        this.car = car;
        currentTurnPositionIndex = 0;
        currentFakeTurnPositionIndex = 0;
        policemanTurnStart = 0;
        timePenaltyStart = 0;
        timePenaltyDuration = 4000;
        policeTurnDuration = 2000;
        policeTurnDelayStart = 0;
        policeFakeTurnDelayStart = 0;

    }

    public void update(float dt) {
        checkIfPolicemanShouldTurn();
        checkIfPolicemanShouldFakeTurn();
        checkIfAllowedToDrive();
        if (policeTurnDelayStart > 0 && System.currentTimeMillis() - policeTurnDelayStart > 1000) {
            policeTurnDelayStart = 0;
            toggleTurn("Towards");
        }
        if (policeFakeTurnDelayStart > 0 && System.currentTimeMillis() - policeFakeTurnDelayStart > 1000) {
            policeFakeTurnDelayStart = 0;
            toggleTurn("Away");
        }
    }

    public void checkIfAllowedToDrive() {
        checkTimePenalty();
        checkPolicemanTurn();

    }

    // Check if the policeman has turned or if he should turn back
    // Allow or disallow car to drive
    public void checkPolicemanTurn() {
        // When policeman turns towards car, start timer and prevent car from driving
        if (hasTurned.equals("Towards") && policemanTurnStart == 0 ) {
            car.canDrive(false);
            policemanTurnStart = System.currentTimeMillis();
        }

        policemanTurnStop = System.currentTimeMillis();

        // Check if two seconds has gone since policeman turn
        // Allow car to drive again
        if (policemanTurnStart > 0 && (policemanTurnStop-policemanTurnStart) > policeTurnDuration) {
            toggleTurn("Away");
            if (timePenaltyStart == 0) {
                car.canDrive(true);
            }
            policemanTurnStart = 0;
        }
    }

    // Check if player should receive timepenalty or if the timepenalty is up
    public void checkTimePenalty() {
        // Start timer for timepenalty
        if (car.getVelocity() > 0 && hasTurned.equals("Towards") && timePenaltyStart == 0) {
            car.canDrive(false);
            timePenaltyStart = System.currentTimeMillis();
        }

        timePenaltyStop = System.currentTimeMillis();

        // Check if timepenalty is up
        if (timePenaltyStart > 0 && (timePenaltyStop-timePenaltyStart) > timePenaltyDuration) {
            car.canDrive(true);
            timePenaltyStart = 0;
        }

    }

    // Use turn positions and car position to determine if the policeman should turn towards player
    public void checkIfPolicemanShouldTurn() {
        if (currentTurnPositionIndex < policemanTurnPositions.size()) {
            if (car.getPosition().y > policemanTurnPositions.get(currentTurnPositionIndex)) {
                currentTurnPositionIndex++;
                policeTurnDelayStart = System.currentTimeMillis();
                toggleTurn("Sideways");
                if (policeFakeTurnDelayStart > 0) {
                    policeFakeTurnDelayStart = 0;
                }
            }
        }
    }

    public void checkIfPolicemanShouldFakeTurn() {
        if (currentFakeTurnPositionIndex < policemanFakeTurnPositions.size()) {
            if (car.getPosition().y > policemanFakeTurnPositions.get(currentFakeTurnPositionIndex)){
                currentFakeTurnPositionIndex++;
                if (hasTurned.equals("Away") && policeTurnDelayStart == 0) {
                    policeFakeTurnDelayStart = System.currentTimeMillis();
                    toggleTurn("Sideways");
                }
            }
        }
    }

    public void toggleTurn(String turnedTowards) {
        hasTurned = turnedTowards;
    }

    public String getHasTurned() {
        return hasTurned;
    }

    public Texture getAnimation() {
        if (hasTurned.equals("Sideways")) {
            return policemanAnimation.getTurnedSidewaysAnimation();
        }
        else if (hasTurned.equals("Towards")) {
            return policemanAnimation.getTurnedTowardsAnimation();
        }
        else {
            return this.policemanAnimation.getTurnedAwayAnimation();
        }
    }

    public Vector2 getPosition() {
        return position;
    }


    public void carCrashAlarm(Pedestrian pedestrian) {
        System.out.println("Car crashed with pedestrian, and gets a 1 second penalty.");
        // Start timer for timepenalty
        if (car.getVelocity() > 0 && timePenaltyStart == 0) {
            car.canDrive(false);
            timePenaltyStart = System.currentTimeMillis();
            System.out.println("Timer started");
            pedestrian.reposition(new Vector2(0, pedestrian.getPosition().y + 100));
        }
        timePenaltyStop = System.currentTimeMillis();
        // Check if timepenalty is up
        while ((timePenaltyStop - timePenaltyStart) < timePenaltyDuration2 + 1) {
            timePenaltyStop = System.currentTimeMillis();
            if ((timePenaltyStop - timePenaltyStart) > timePenaltyDuration2) {
                car.canDrive(true);
                System.out.println("The car waited " + (timePenaltyStop-timePenaltyStart)/1000 + " second and can drive.");
                timePenaltyStart = 0;
            }
        }
    }

}
