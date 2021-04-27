package com.mygdx.dragmania.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.dragmania.controllers.ViewManager;

import java.util.ArrayList;

public class HelpView extends View {

    private BackArrow backArrow;
    private ArrayList<Texture> helpTextures;
    private Button nextButton;
    private int textureIndex;
    private float screenWidth;
    private SpriteBatch sb;

    public HelpView(ViewManager viewManager) {
        super(viewManager);
        backArrow = new BackArrow(0,0);
        helpTextures = new ArrayList<>();
        helpTextures.add(new Texture("textures/help/help1.png"));
        helpTextures.add(new Texture("textures/help/help2.png"));
        helpTextures.add(new Texture("textures/help/help3.png"));
        nextButton = new Button(320, 20, "textures/buttons/next.png");
        textureIndex = 0;
        screenWidth = Gdx.graphics.getWidth();
        sb = new SpriteBatch();
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
            checkNextButtonTouched();
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        sb.begin();
        sb.draw(helpTextures.get(textureIndex), 0, 0, screenWidth, Gdx.graphics.getHeight());
        sb.draw(backArrow.getBackArrow(), backArrow.getPosition().x, backArrow.getPosition().y, backArrow.getWidth()/3, backArrow.getHeight()/3);
        if(textureIndex < 2) {
            sb.draw(nextButton.getButton(), screenWidth/2-(nextButton.getWidth()/2), nextButton.getPosition().y);
        }
        sb.end();
    }

    public void checkNextButtonTouched() {
        if(nextButton.getBounds().contains(Gdx.input.getX(), Gdx.graphics.getHeight()-Gdx.input.getY())) {
            textureIndex++;
        }
    }

    public void dispose() {
        for(Texture texture : helpTextures) {
            texture.dispose();
        }
        nextButton.dispose();
        backArrow.dispose();
    }
}
