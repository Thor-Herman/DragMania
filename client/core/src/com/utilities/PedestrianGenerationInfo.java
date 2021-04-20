package com.utilities;

import com.badlogic.gdx.math.Vector2;

public class PedestrianGenerationInfo {
    Vector2 startPosition;
    PedestrianType type;

    public PedestrianGenerationInfo(Vector2 startPosition, PedestrianType type) {
        this.startPosition = startPosition;
        this.type = type;
    }

    public Vector2 getStartPosition() {
        return this.startPosition;
    }

    public PedestrianType getType() {
        return this.type;
    }
}
