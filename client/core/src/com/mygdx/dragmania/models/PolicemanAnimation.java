package com.mygdx.dragmania.models;

import com.badlogic.gdx.graphics.Texture;

public class PolicemanAnimation {

    private Texture test1;
    private Texture test2;

    public PolicemanAnimation() {
        // Test textures
        test1 = new Texture("8bit-pikachu.png");
        test2 = new Texture("mickey_mouse.png");

    }

    public Texture getTurnedAwayAnimation() {
        return test1;
    }

    public Texture getTurnedTowardsAnimation() {
        return test2;
    }

}
