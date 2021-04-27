package com.mygdx.dragmania.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.mygdx.dragmania.controllers.GameController;
import com.mygdx.dragmania.controllers.ViewManager;
import com.mygdx.dragmania.models.GameModel;

public class GameFinishedView extends View{

    private Texture background;
    private BitmapFont font;
    private Button rematchButton;
    private Button mainMenuButton;
    private float screenWidth;
    private float screenHeight;

    private static GlyphLayout glyphLayout = new GlyphLayout();
    private String gameStatus;
    private GameModel model;

    public GameFinishedView(ViewManager viewManager, GameModel model) {
        super(viewManager);
        this.model = model;
        background = new Texture("textures/backgrounds/background_plain.png");
        // Generate font
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/GovtAgentBB.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 125;
        font = generator.generateFont(parameter);
        generator.dispose();
        rematchButton = new Button(200, 500, "textures/buttons/rematch.png");
        mainMenuButton = new Button(200, 800, "textures/buttons/main_menu.png");
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
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
        gameStatus = model.getOpponentScore() < model.getPlayerScore() ? "Game Won" : "Game Lost";
        glyphLayout.setText(font, gameStatus);
        font.draw(sb, glyphLayout, screenWidth/2-(glyphLayout.width/2), (float) (screenHeight*0.8));
        sb.draw(rematchButton.getButton(), screenWidth/2-(rematchButton.getWidth()/2), rematchButton.getPosition().y, rematchButton.getWidth(), rematchButton.getHeight());
        sb.draw(mainMenuButton.getButton(), screenWidth/2-(rematchButton.getWidth()/2), mainMenuButton.getPosition().y, mainMenuButton.getWidth(), mainMenuButton.getHeight());
        sb.end();
    }

    public void checkRematchButtonTouched() {
        if(rematchButton.getBounds().contains(Gdx.input.getX(), Gdx.graphics.getHeight()-Gdx.input.getY())) {
            viewManager.pop();
            GameController.getInstance().readyUp();
        }
    }

    public void checkMainMenuButtonTouched() {
        if(mainMenuButton.getBounds().contains(Gdx.input.getX(), Gdx.graphics.getHeight()-Gdx.input.getY())) {
            viewManager.pop();
            GameController.getInstance().leaveGame();
        }
    }

    public void dispose() {
        background.dispose();
        font.dispose();
        rematchButton.dispose();
        mainMenuButton.dispose();
    }
}
