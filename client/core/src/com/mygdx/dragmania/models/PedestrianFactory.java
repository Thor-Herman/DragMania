package com.mygdx.dragmania.models;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.utilities.PedestrianType;

public class PedestrianFactory {
    private static final Map<PedestrianType, String> TEXTURES;
    private static final Map<PedestrianType, Integer> VELOCITIES;

    static {
        // Initialize TEXTURES
        Map<PedestrianType, String> tmpTextures = new EnumMap<>(PedestrianType.class);
        tmpTextures.put(PedestrianType.STANDARD, "textures/pedestrians/pedestrian.png");
        tmpTextures.put(PedestrianType.CYCLIST, "textures/pedestrians/pedestrian.png");
        tmpTextures.put(PedestrianType.CHILD, "textures/pedestrians/pedestrian.png");
        tmpTextures.put(PedestrianType.OLD, "textures/pedestrians/pedestrian.png");
        TEXTURES = Collections.unmodifiableMap(tmpTextures);

        // Initialize VELOCITY
        Map<PedestrianType, Integer> tmpVelocities = new EnumMap<>(PedestrianType.class);
        tmpVelocities.put(PedestrianType.STANDARD, 250);
        tmpVelocities.put(PedestrianType.CYCLIST, 4);
        tmpVelocities.put(PedestrianType.CHILD, 1);
        tmpVelocities.put(PedestrianType.OLD, 1);
        VELOCITIES = Collections.unmodifiableMap(tmpVelocities);
    }

    private PedestrianFactory() {}

    public static Pedestrian makePedestrian(PedestrianType type, Vector2 startPosition) {
        int xVelocity = VELOCITIES.get(type);

        try {
            Texture texture = new Texture(TEXTURES.get(type));
            return new Pedestrian(startPosition, xVelocity, texture);
        }
        catch(NullPointerException e) {
            // For testing purposes
            return new Pedestrian(startPosition, xVelocity, 40, 100);
        }
    }
}
