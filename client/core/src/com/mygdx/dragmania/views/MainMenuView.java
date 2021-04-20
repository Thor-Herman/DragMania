package com.mygdx.dragmania.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.dragmania.views.buttons.CreateGameButton;
import com.mygdx.dragmania.views.buttons.HelpButton;
import com.mygdx.dragmania.views.buttons.JoinGameButton;

public class MainMenuView extends View {

    private HelpButton helpButton;
    private JoinGameButton joinGameButton;
    private CreateGameButton createGameButton;
    private Texture background;

    public MainMenuView(ViewManager viewManager) {
        super(viewManager);
        helpButton = new HelpButton(200, 400);
        joinGameButton = new JoinGameButton(200, 700);
        createGameButton = new CreateGameButton(200, 1000);
        background = new Texture("background_straight.png");
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()) {
            checkHelpButtonTouched();
            checkJoinGameButtonTouched();
            checkCreateGameButtonTouched();
        }

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        SpriteBatch sb = new SpriteBatch();
        sb.begin();
        sb.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        sb.draw(helpButton.getButton(), helpButton.getPosition().x, helpButton.getPosition().y, helpButton.getWidth(), helpButton.getHeight());
        sb.draw(joinGameButton.getButton(), joinGameButton.getPosition().x, joinGameButton.getPosition().y, joinGameButton.getWidth(), joinGameButton.getHeight());
        sb.draw(createGameButton.getButton(), createGameButton.getPosition().x, createGameButton.getPosition().y, createGameButton.getWidth(), createGameButton.getHeight());
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
}
