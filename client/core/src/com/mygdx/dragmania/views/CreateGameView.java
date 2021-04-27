package com.mygdx.dragmania.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.mygdx.dragmania.controllers.LobbyController;
import com.mygdx.dragmania.controllers.ViewManager;

public class CreateGameView extends View {
    private BackArrow backArrow;
    private Button getPinButton;
    private Texture background;
    private BitmapFont font;
    private BitmapFont font2;
    private String pin;
    private LobbyController controller = LobbyController.getInstance();

    private float screenWidth;
    private float screenHeight;

    private static GlyphLayout glyphLayout1 = new GlyphLayout();
    private static GlyphLayout glyphLayout2 = new GlyphLayout();
    private SpriteBatch sb;

    public CreateGameView(ViewManager viewManager) {
        super(viewManager);
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
        backArrow = new BackArrow(0,0);
        getPinButton = new Button(200, 400, "textures/buttons/get_pin.png");
        background = new Texture("textures/backgrounds/background_plain.png");
        // Generate fonts
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/GovtAgentBB.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 200;
        font = generator.generateFont(parameter);
        parameter.size = 50;
        font2 = generator.generateFont(parameter);
        generator.dispose();
        pin = "";
        sb = new SpriteBatch();
    }

    @Override
    public void update(float dt) {
        handleInput();

    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()) {
            checkBackTouched();
            checkGetPinTouched();
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        sb.begin();
        sb.draw(background, 0, 0, screenWidth, screenHeight);
        sb.draw(backArrow.getBackArrow(), backArrow.getPosition().x, backArrow.getPosition().y, backArrow.getWidth()/3, backArrow.getHeight()/3);
        sb.draw(getPinButton.getButton(), screenWidth/2-(getPinButton.getWidth()/2), getPinButton.getPosition().y, getPinButton.getWidth(), getPinButton.getHeight());
        pin = Integer.toString(LobbyController.getInstance().getModel().getRoomCode());
        if(pin.length() == 4) {
            glyphLayout1.setText(font, pin);
            font.draw(sb, glyphLayout1, screenWidth/2-(glyphLayout1.width/2), (float) (screenHeight*0.7));
            glyphLayout2.setText(font2, "Send this pin to your buddy and wait here");
            font2.draw(sb, glyphLayout2, screenWidth/2-(glyphLayout2.width/2), (float) (screenHeight*0.6));
        }
        sb.end();
    }

    @Override
    public void checkBackTouched() {
        if(backArrow.getBounds().contains(Gdx.input.getX(), screenHeight-Gdx.input.getY())) {
            controller.resetModel();
            viewManager.pop();
            viewManager.push(new MainMenuView(viewManager));
        }
    }

    public void checkGetPinTouched() {
        if(getPinButton.getBounds().contains(Gdx.input.getX(), screenHeight-Gdx.input.getY())) {
            LobbyController.getInstance().resetModel();
            LobbyController.getInstance().createGame("test");
        }
    }

    public void dispose() {
        backArrow.dispose();
        background.dispose();
        font.dispose();
        font2.dispose();
        getPinButton.dispose();
    }
}
