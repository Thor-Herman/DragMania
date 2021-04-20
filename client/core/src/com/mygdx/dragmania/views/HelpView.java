package com.mygdx.dragmania.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.dragmania.DragMania;

public class HelpView extends View {

    private BackArrow backArrow;

    public HelpView(ViewManager viewManager) {
        super(viewManager);
        backArrow = new BackArrow(0,0);
    }


    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()) {
            //System.out.println(Gdx.input.getY());
            //System.out.println(Gdx.input.getX());
            checkBackTouched(backArrow);
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        SpriteBatch sb = new SpriteBatch();
        sb.begin();
        sb.draw(backArrow.getBackArrow(), backArrow.getPosition().x, backArrow.getPosition().y, backArrow.getWidth()/3, backArrow.getHeight()/3);
        sb.end();
    }
}
