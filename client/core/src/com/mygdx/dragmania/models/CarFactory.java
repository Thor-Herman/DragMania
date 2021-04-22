package com.mygdx.dragmania.models;

import com.badlogic.gdx.graphics.Texture;
import com.utilities.CarType;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;


public class CarFactory {

    private static final Map<CarType, String> TEXTURES;
    private static final Map<CarType, Integer> ACCELERATIONS;
    private static final Map<CarType, Integer> MAXVELOCITIES;

    static {
        // Initialize TEXTURES
        Map<CarType, String> tmpTextures = new EnumMap<>(CarType.class);
        tmpTextures.put(CarType.NORMAL, "textures/cars/car_red.png");
        tmpTextures.put(CarType.TRUCK, "textures/cars/car_blue.png");
        tmpTextures.put(CarType.MOTORCYCLE, "textures/cars/car_green.png");
        TEXTURES = Collections.unmodifiableMap(tmpTextures);

        // Initialize ACCELERATIONS
        Map<CarType, Integer> tmpAccelerations = new EnumMap<>(CarType.class);
        tmpAccelerations.put(CarType.NORMAL, 100);
        tmpAccelerations.put(CarType.TRUCK, 1);
        tmpAccelerations.put(CarType.MOTORCYCLE, 1);
        ACCELERATIONS = Collections.unmodifiableMap(tmpAccelerations);

        // Initialize MAXVELOCITIES
        Map<CarType, Integer> tmpMaxVelocities = new EnumMap<>(CarType.class);
        tmpMaxVelocities.put(CarType.NORMAL, 1000);
        tmpMaxVelocities.put(CarType.TRUCK, 3);
        tmpMaxVelocities.put(CarType.MOTORCYCLE, 3);
        MAXVELOCITIES = Collections.unmodifiableMap(tmpMaxVelocities);
    }

    private CarFactory() {}

    public static Car makeCar(CarType type) {
        int acceleration = ACCELERATIONS.get(type);
        int maxVelocity = MAXVELOCITIES.get(type);

        try {
            Texture texture = new Texture(TEXTURES.get(type));
            return new Car(texture, acceleration, maxVelocity);
        }
        catch(NullPointerException e) {
            // For testing purposes
            return new Car(200, 300, acceleration, maxVelocity);
        }
    }
}
