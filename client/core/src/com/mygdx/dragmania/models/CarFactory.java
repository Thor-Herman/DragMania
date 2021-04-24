package com.mygdx.dragmania.models;

import com.badlogic.gdx.graphics.Texture;
import com.utilities.CarType;
import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;


public class CarFactory {

    private static final Map<CarType, String> TEXTURES;
    private static final Map<CarType, Float> ACCELERATIONS;
    private static final Map<CarType, Float> MAXVELOCITIES;

    static {
        // Initialize TEXTURES
        Map<CarType, String> tmpTextures = new EnumMap<>(CarType.class);
        tmpTextures.put(CarType.NORMAL, "car_red2.png");
        tmpTextures.put(CarType.TRUCK, "textures/cars/car_blue.png");
        tmpTextures.put(CarType.MOTORCYCLE, "textures/cars/car_green.png");
        TEXTURES = Collections.unmodifiableMap(tmpTextures);

        // Initialize ACCELERATIONS
        Map<CarType, Float> tmpAccelerations = new EnumMap<>(CarType.class);
        tmpAccelerations.put(CarType.NORMAL, 0.75f);
        tmpAccelerations.put(CarType.TRUCK, 1f);
        tmpAccelerations.put(CarType.MOTORCYCLE, 1f);
        ACCELERATIONS = Collections.unmodifiableMap(tmpAccelerations);

        // Initialize MAXVELOCITIES
        Map<CarType, Float> tmpMaxVelocities = new EnumMap<>(CarType.class);
        tmpMaxVelocities.put(CarType.NORMAL, 200f);
        tmpMaxVelocities.put(CarType.TRUCK, 3f);
        tmpMaxVelocities.put(CarType.MOTORCYCLE, 3f);
        MAXVELOCITIES = Collections.unmodifiableMap(tmpMaxVelocities);
    }

    private CarFactory() {}

    public static Car makeCar(CarType type) {
        float acceleration = ACCELERATIONS.get(type);
        float maxVelocity = MAXVELOCITIES.get(type);

        try {
            Texture texture = new Texture(TEXTURES.get(type));
            return new Car(texture, acceleration, maxVelocity);
        }
        catch(NullPointerException e) {
            // For testing purposes
            return new Car(40, 100, acceleration, maxVelocity);
        }
    }
}
