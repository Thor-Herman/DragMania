package com.mygdx.dragmania.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.mygdx.dragmania.views.buttons.GetPinButton;

public class CreateGameView extends View {
    private BackArrow backArrow;
    private GetPinButton getPinButton;
    private Texture background;
    private BitmapFont font;
    private String pin;

    private float screenWidth;
    private float screenHeight;

    private static GlyphLayout glyphLayout = new GlyphLayout();

    public CreateGameView(ViewManager viewManager) {
        super(viewManager);
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
        backArrow = new BackArrow(0,0);
        getPinButton = new GetPinButton(200, 400);
        background = new Texture("background_plain.png");
        // Generate font
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("GovtAgentBB.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 200;
        font = generator.generateFont(parameter);
        //generator.dispose();
        pin = "";
    }

    @Override
    public void update(float dt) {
        handleInput();

    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()) {
            checkBackTouched(backArrow);
            checkGetPinTouched();
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
        sb.draw(getPinButton.getButton(), screenWidth/2-(getPinButton.getWidth()/2), getPinButton.getPosition().y, getPinButton.getWidth(), getPinButton.getHeight());
        if(pin != "") {
            glyphLayout.setText(font, pin);
            font.draw(sb, glyphLayout, screenWidth/2-(glyphLayout.width/2), (float) (screenHeight*0.7));
        }
        sb.end();
    }

    public void checkGetPinTouched() {
        if(getPinButton.getBounds().contains(Gdx.input.getX(), Gdx.graphics.getHeight()-Gdx.input.getY())) {
            // Get from controller
            pin = "0000";
        }
    }
}
