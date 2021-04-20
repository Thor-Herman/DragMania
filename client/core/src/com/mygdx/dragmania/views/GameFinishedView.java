package com.mygdx.dragmania.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.mygdx.dragmania.views.buttons.MainMenuButton;
import com.mygdx.dragmania.views.buttons.RematchButton;

public class GameFinishedView extends View{

    private Texture background;
    private BitmapFont font;
    private RematchButton rematchButton;
    private MainMenuButton mainMenuButton;

    protected GameFinishedView(ViewManager viewManager) {
        super(viewManager);
        background = new Texture("background_plain.png");
        // Generate font
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("GovtAgentBB.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 125;
        font = generator.generateFont(parameter);
        generator.dispose();
        rematchButton = new RematchButton(200, 500);
        mainMenuButton = new MainMenuButton(200, 800);
    }

    @Override
    public void update(float dt) {
        handleInput();

    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()) {
            checkMainMenuButtonTouched();
            checkRematchButtonTouched();
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        SpriteBatch sb = new SpriteBatch();
        sb.begin();
        sb.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        font.draw(sb, "Game Won!", 250, 1750);
        sb.draw(rematchButton.getButton(), rematchButton.getPosition().x, rematchButton.getPosition().y, rematchButton.getWidth(), rematchButton.getHeight());
        sb.draw(mainMenuButton.getButton(), mainMenuButton.getPosition().x, mainMenuButton.getPosition().y, mainMenuButton.getWidth(), mainMenuButton.getHeight());
        sb.end();
    }

    public void checkRematchButtonTouched() {
        if(rematchButton.getBounds().contains(Gdx.input.getX(), Gdx.graphics.getHeight()-Gdx.input.getY())) {
            viewManager.push(new GameView(viewManager));
        }
    }

    public void checkMainMenuButtonTouched() {
        if(mainMenuButton.getBounds().contains(Gdx.input.getX(), Gdx.graphics.getHeight()-Gdx.input.getY())) {
            viewManager.push(new MainMenuView(viewManager));
        }
    }
}
