package com.mygdx.dragmania.models;

// import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
// import com.utilities.CarSound;
import com.utilities.CarType;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class CarFactory {

    private static final Map<CarType, String> TEXTURES;
    private static final Map<CarType, Integer> ACCELERATIONS;
    private static final Map<CarType, Integer> MAXVELOCITIES;
    // private static final Map<CarType, Map<CarSound, Sound>> CARTYPESOUNDS;

    static {
        // Initialize TEXTURES
        Map<CarType, String> tmpTextures = new HashMap<>();
        tmpTextures.put(CarType.NORMAL, "textures/cars/car_red.png");
        tmpTextures.put(CarType.TRUCK, "badlogic.png");
        tmpTextures.put(CarType.MOTORCYCLE, "badlogic.png");
        TEXTURES = Collections.unmodifiableMap(tmpTextures);

        // Initialize ACCELERATIONS
        Map<CarType, Integer> tmpAccelerations = new HashMap<>();
        tmpAccelerations.put(CarType.NORMAL, 1);
        tmpAccelerations.put(CarType.TRUCK, 1);
        tmpAccelerations.put(CarType.MOTORCYCLE, 1);
        ACCELERATIONS = Collections.unmodifiableMap(tmpAccelerations);

        // Initialize MAXVELOCITIES
        Map<CarType, Integer> tmpMaxVelocities = new HashMap<>();
        tmpMaxVelocities.put(CarType.NORMAL, 3);
        tmpMaxVelocities.put(CarType.TRUCK, 3);
        tmpMaxVelocities.put(CarType.MOTORCYCLE, 3);
        MAXVELOCITIES = Collections.unmodifiableMap(tmpMaxVelocities);

        // // Initialize CARTYPESOUNDS with same sounds for all car types
        // Map<CarSound, Sound> tmpSounds = new HashMap<>();
        // tmpSounds.put(CarSound.ACCELERATING, Gdx.audio.newSound(Gdx.files.internal("sound.wav")));
        // tmpSounds.put(CarSound.BRAKING, Gdx.audio.newSound(Gdx.files.internal("sound.wav")));
        // tmpSounds.put(CarSound.CRASH, Gdx.audio.newSound(Gdx.files.internal("sounds/car_crash.mp3")));

        // Map<CarType, Map<CarSound, Sound>> tmpCarTypeSounds = new HashMap<>();
        // tmpCarTypeSounds.put(CarType.NORMAL, tmpSounds);
        // tmpCarTypeSounds.put(CarType.TRUCK, tmpSounds);
        // tmpCarTypeSounds.put(CarType.MOTORCYCLE, tmpSounds);
        // CARTYPESOUNDS = Collections.unmodifiableMap(tmpCarTypeSounds);
    }

    public static Car makeCar(CarType type) {
        int acceleration = ACCELERATIONS.get(type);
        int maxVelocity = MAXVELOCITIES.get(type);

        try {
            Texture texture = new Texture(TEXTURES.get(type));
            // Map<CarSound, Sound> sounds = CARTYPESOUNDS.get(type);
            return new Car(texture, acceleration, maxVelocity);
        }
        catch(NullPointerException e) {
            // For testing purposes
            return new Car(40, 100, acceleration, maxVelocity);
        }
    }
}
