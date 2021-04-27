package com.mygdx.dragmania.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.dragmania.controllers.ViewManager;

public class MainMenuView extends View {

    private Button helpButton;
    private Button joinGameButton;
    private Button createGameButton;
    private Texture background;

    private float screenWidth;
    private float screenHeight;

    public MainMenuView(ViewManager viewManager) {
        super(viewManager);
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
        helpButton = new Button(200, 400, "textures/buttons/help.png");
        joinGameButton = new Button(200, 700, "textures/buttons/join_game.png");
        createGameButton = new Button(200, 1000, "textures/buttons/create_game.png");
        background = new Texture("textures/backgrounds/background_straight.png");
    }

    @Override
    public void checkBackTouched() {

    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()) {
            checkCreateGameButtonTouched();
            checkHelpButtonTouched();
            checkJoinGameButtonTouched();
        }

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        SpriteBatch sb = new SpriteBatch();
        sb.begin();
        sb.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        sb.draw(helpButton.getButton(), screenWidth/2-(helpButton.getWidth()/2), helpButton.getPosition().y, helpButton.getWidth(), helpButton.getHeight());
        sb.draw(joinGameButton.getButton(), screenWidth/2-(joinGameButton.getWidth()/2), joinGameButton.getPosition().y, joinGameButton.getWidth(), joinGameButton.getHeight());
        sb.draw(createGameButton.getButton(), screenWidth/2-(helpButton.getWidth()/2), createGameButton.getPosition().y, createGameButton.getWidth(), createGameButton.getHeight());
        sb.end();

    }

    public void checkHelpButtonTouched() {
        if(helpButton.getBounds().contains(Gdx.input.getX(), Gdx.graphics.getHeight()-Gdx.input.getY())) {
            viewManager.push(new HelpView(viewManager));
        }
    }

    public void checkJoinGameButtonTouched() {
        if(joinGameButton.getBounds().contains(Gdx.input.getX(), Gdx.graphics.getHeight()-Gdx.input.getY())) {
            viewManager.push(new JoinGameView(viewManager));
        }
    }

    public void checkCreateGameButtonTouched() {
        if(createGameButton.getBounds().contains(Gdx.input.getX(), Gdx.graphics.getHeight()-Gdx.input.getY())) {
            viewManager.push(new CreateGameView(viewManager));
        }
    }

    public void dispose() {
        helpButton.dispose();
        joinGameButton.dispose();
        createGameButton.dispose();
        background.dispose();
    }
}
