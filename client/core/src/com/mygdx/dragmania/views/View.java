package com.mygdx.dragmania.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

public abstract class View extends ScreenAdapter {

    protected ViewManager viewManager;
    protected OrthographicCamera cam;

    protected View(ViewManager viewManager) {
        this.viewManager = viewManager;
        cam = new OrthographicCamera();
    }


    public void checkBackTouched(BackArrow backArrow) {
        if(backArrow.getBounds().contains(Gdx.input.getX(), Gdx.graphics.getHeight()-Gdx.input.getY())) {
            viewManager.pop();
        }

    }

    public abstract void update(float dt);
    public abstract void handleInput();

}
