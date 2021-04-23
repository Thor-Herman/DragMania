package com.mygdx.dragmania.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.dragmania.DragMania;
import com.mygdx.dragmania.views.buttons.NextButton;

import java.util.ArrayList;

public class HelpView extends View {

    private BackArrow backArrow;
    private ArrayList<Texture> helpTextures;
    private NextButton nextButton;
    private int textureIndex;

    public HelpView(ViewManager viewManager) {
        super(viewManager);
        backArrow = new BackArrow(0,0);
        helpTextures = new ArrayList<>();
        helpTextures.add(new Texture("help1.png"));
        helpTextures.add(new Texture("help2.png"));
        helpTextures.add(new Texture("help3.png"));
        nextButton = new NextButton(320, 20);
        textureIndex = 0;
    }


    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()) {
            checkBackTouched(backArrow);
            checkNextButtonTouched();
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        SpriteBatch sb = new SpriteBatch();
        sb.begin();
        sb.draw(helpTextures.get(textureIndex), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        sb.draw(backArrow.getBackArrow(), backArrow.getPosition().x, backArrow.getPosition().y, backArrow.getWidth()/3, backArrow.getHeight()/3);
        if(textureIndex < 2) {
            sb.draw(nextButton.getButton(), nextButton.getPosition().x, nextButton.getPosition().y);
        }
        sb.end();
    }

    public void checkNextButtonTouched() {
        if(nextButton.getBounds().contains(Gdx.input.getX(), Gdx.graphics.getHeight()-Gdx.input.getY())) {
            textureIndex++;
        }
    }
}
