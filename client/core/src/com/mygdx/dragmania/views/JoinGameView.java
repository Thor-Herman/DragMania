package com.mygdx.dragmania.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.dragmania.controllers.ViewManager;

public class JoinGameView extends View {
    private BackArrow backArrow;
    private Texture background;
    private float screenWidth;

    public JoinGameView(ViewManager viewManager) {
        super(viewManager);
        backArrow = new BackArrow(0,0);
        background = new Texture("textures/backgrounds/background_plain.png");
        PinInputListener listener = new PinInputListener();
        Gdx.input.getTextInput(listener, "Input game pin", "", "Pin:");
        screenWidth = Gdx.graphics.getWidth();
    }

    @Override
    public void checkBackTouched() {
        if(backArrow.getBounds().contains(Gdx.input.getX(), Gdx.graphics.getHeight()-Gdx.input.getY())) {
            viewManager.pop();
            viewManager.push(new MainMenuView(viewManager));
        }
    }

    @Override
    public void update(float dt) {
        handleInput();

    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()) {
            checkBackTouched();
            checkButtonTouch();
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        SpriteBatch sb = new SpriteBatch();
        sb.begin();
        sb.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        sb.draw(backArrow.getBackArrow(), backArrow.getPosition().x, backArrow.getPosition().y, backArrow.getWidth()/3, backArrow.getHeight()/3);
        sb.end();
    }

    public void dispose() {
        backArrow.dispose();
        background.dispose();
    }

    public void checkButtonTouch() {
        if(backArrow.getBounds().contains(Gdx.input.getX(), Gdx.graphics.getHeight()-Gdx.input.getY())) {
            viewManager.pop();
            viewManager.push(new MainMenuView(viewManager));
        }
    }
}
