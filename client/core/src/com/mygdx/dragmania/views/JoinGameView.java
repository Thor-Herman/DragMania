package com.mygdx.dragmania.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.loaders.AssetLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.mygdx.dragmania.views.buttons.StartGameButton;

public class JoinGameView extends View {
    private BackArrow backArrow;
    private StartGameButton startGameButton;
    private Texture background;
    private float screenWidth;

    public JoinGameView(ViewManager viewManager) {
        super(viewManager);
        backArrow = new BackArrow(0,0);
        startGameButton = new StartGameButton(200, 400);
        background = new Texture("background_plain.png");
        PinInputListener listener = new PinInputListener();
        Gdx.input.getTextInput(listener, "Input game pin", "", "Pin:");
        screenWidth = Gdx.graphics.getWidth();
    }

    @Override
    public void update(float dt) {
        handleInput();

    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()) {
            checkBackTouched(backArrow);
            checkStartGameButtonTouched();
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
        sb.draw(startGameButton.getButton(), screenWidth/2-(startGameButton.getWidth()/2), startGameButton.getPosition().y, startGameButton.getWidth(), startGameButton.getHeight());
        sb.end();
    }

    public void checkStartGameButtonTouched() {
        if(startGameButton.getBounds().contains(Gdx.input.getX(), Gdx.graphics.getHeight()-Gdx.input.getY())) {
            viewManager.push(new GameView(viewManager));
        }
    }
}
