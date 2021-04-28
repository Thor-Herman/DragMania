package com.mygdx.dragmania.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.dragmania.controllers.GameController;
import com.mygdx.dragmania.controllers.ViewManager;
import com.mygdx.dragmania.models.GameModel;


public class GameView extends View {

    private float screenWidth;
    private float screenHeight;
    private GameModel gameModel;
    private Texture stopSign;
    private GameController controller;
    private Texture policeTexture;

    private float[] midLineYPositions;
    private float finishLineYPos;

    private int carPosition;

    private BitmapFont font;

    private BackArrow backArrow;
    private Texture midTexture;
    private Texture topSector;
    private Texture background;
    private Texture finishLine;
    private SpriteBatch sb;

    private float scaleConstant;

    private static final GlyphLayout glyphLayout1 = new GlyphLayout();
    private static final GlyphLayout glyphLayout2 = new GlyphLayout();
    private static final GlyphLayout glyphLayout3 = new GlyphLayout();
    private static final GlyphLayout glyphLayout4 = new GlyphLayout();

    public GameView(ViewManager viewManager) {
        super(viewManager);
        stopSign = new Texture("textures/policeman/stop.png");
        controller = GameController.getInstance();
        gameModel = controller.getModel();

        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        // Generating font
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/GovtAgentBB.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 50;
        font = generator.generateFont(parameter);
        generator.dispose();

        // Setting midline positions initially
        midLineYPositions = new float[5];
        midLineYPositions[0] = 1800;
        midLineYPositions[1] = 1400;
        midLineYPositions[2] = 1000;
        midLineYPositions[3] = 600;
        midLineYPositions[4] = 200;
        backArrow = new BackArrow(0,0);
        // Initialize finish line to be under the screen
        finishLineYPos = -400;

        carPosition = 0;

        // Determine to scale by width or height
        int standardWidth = 1080;
        scaleConstant = screenWidth/ standardWidth;
        int standardHeight = 2088;
        if(Math.abs(1-screenHeight/ standardHeight) > Math.abs(1-screenWidth/ standardWidth)) {
            scaleConstant = screenHeight/ standardHeight;
        }
        midTexture = new Texture(getPixmapRectangle(20, 200, Color.YELLOW));
        topSector = new Texture(getPixmapRectangle((int) screenWidth, (int) (screenHeight*0.8), Color.valueOf("1c1c1c")));
        background = new Texture(getPixmapRectangle((int) screenWidth, (int) screenHeight, Color.DARK_GRAY));
        finishLine = new Texture(getPixmapRectangle((int) screenWidth, 50, Color.WHITE));
        sb = new SpriteBatch();
    }

    @Override
    public void checkBackTouched() {
        if(backArrow.getBounds().contains(Gdx.input.getX(), Gdx.graphics.getHeight()-Gdx.input.getY())) {
            controller.leaveGame();
        }
    }

    public static Pixmap getPixmapRectangle(int width, int height, Color color){
        Pixmap pixmap=new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(color);
        pixmap.fillRectangle(0,0, pixmap.getWidth(), pixmap.getHeight());
        return pixmap;
    }

    @Override
    public void update(float dt) {
        // Update other classes depending on whether the player is touching and check if backarrow is touched
        if(Gdx.input.isTouched()) {
            checkBackTouched();
            controller.update(dt, true);
        }
        else {
            controller.update(dt, false);
        }

        carPosition = (int)gameModel.getCar().getPosition().y;

        // Set finish line position if the player has come far enough
        if(carPosition >= gameModel.getGameMap().getMapLength()-screenHeight/76 && finishLineYPos==-400) {
            finishLineYPos = (int) (screenHeight*0.9);
        }
    }

    @Override
    public void handleInput() {}

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        sb.begin();
        renderBackground(sb);
        moveLines();
        renderMidLines(sb);
        drawFinishLine(sb);
        renderTopSector(sb);
        drawTextures(sb);
        drawFonts(sb);
        sb.end();
    }


    public void renderMidLines(SpriteBatch sb){
        drawMidLine(sb, midLineYPositions[0]);
        drawMidLine(sb, midLineYPositions[1]);
        drawMidLine(sb, midLineYPositions[2]);
        drawMidLine(sb, midLineYPositions[3]);
        drawMidLine(sb, midLineYPositions[4]);
    }

    public void renderBackground(SpriteBatch sb) {
        sb.draw(background, 0, 0);
    }

    public void drawMidLine(SpriteBatch sb, float yPos) {
        sb.draw(midTexture, screenWidth/2-(midTexture.getWidth())/2, yPos);
    }

    // Move midlines according to the car velocity
    public void moveMidlines(float offset) {
        if(offset > 0) {
            for(int i = 0; i < 5; i++) {
                midLineYPositions[i] = midLineYPositions[i] - offset;
            }
        }
    }

    public void drawFinishLine(SpriteBatch sb) {
        if(finishLineYPos > -400) {
            sb.draw(finishLine, 0, finishLineYPos);
        }
    }

    // Only move finish line if player has come far enough
    public void moveFinishLine(float offset) {
        if(finishLineYPos > -400) {
            if (scaleConstant > 0.99 && scaleConstant < 1.01) {
                finishLineYPos -= offset;
            }
            else {
                finishLineYPos -= (offset*(scaleConstant)*0.9);
            }
        }
    }

    public void renderTopSector(SpriteBatch sb) {
        sb.draw(topSector, 0, (float) (screenHeight*0.8));
    }

    public void moveLines() {
        repositionLine();
        moveMidlines(gameModel.getCar().getVelocity());
        moveFinishLine(gameModel.getCar().getVelocity());
    }

    public void repositionLine() {
        if (midLineYPositions[4] < -200) {
            midLineYPositions[0] = 1800;
            midLineYPositions[1] = 1400;
            midLineYPositions[2] = 1000;
            midLineYPositions[3] = 600;
            midLineYPositions[4] = 200;
        }
    }

    public void drawTextures(SpriteBatch sb) {
        sb.draw(gameModel.getCar().getTexture(), (float) (screenWidth/2-gameModel.getCar().getTexture().getWidth()/(5*scaleConstant)), 225, (int)(gameModel.getCar().getTexture().getWidth()/(2.5*scaleConstant)), (int)(gameModel.getCar().getTexture().getHeight()/(2.5*scaleConstant)));
        policeTexture = gameModel.getGameMap().getPoliceman().getAnimation();
        sb.draw(policeTexture, screenWidth/2-(policeTexture.getWidth()*scaleConstant), (float) (screenHeight*0.8), policeTexture.getWidth()*scaleConstant*2, policeTexture.getHeight()*scaleConstant*2);
        sb.draw(backArrow.getBackArrow(), backArrow.getPosition().x, backArrow.getPosition().y, backArrow.getWidth()/3, backArrow.getHeight()/3);
        if (!gameModel.getCar().getAllowedToDrive()) {
            sb.draw(stopSign, (float) (screenWidth/2-(stopSign.getWidth()/(2*1.5))), (float) (screenHeight*0.6), (float) (stopSign.getWidth()/1.5), (float) (stopSign.getHeight()/1.5));
        }
    }

    public void drawFonts(SpriteBatch sb) {
        glyphLayout1.setText(font, "Your score:");
        font.draw(sb, glyphLayout1, (float) (screenWidth*0.1), (float) (screenHeight*0.95));
        glyphLayout2.setText(font, Integer.toString(carPosition));
        font.draw(sb, glyphLayout2, (float) (screenWidth*0.1) + (glyphLayout1.width/2)-(glyphLayout2.width/2), (float) (screenHeight*0.9));

        glyphLayout3.setText(font, "Rival's score:");
        font.draw(sb, glyphLayout3, (float) (screenWidth*0.65), (float) (screenHeight*0.95));
        glyphLayout4.setText(font, Integer.toString(gameModel.getOpponentScore()));
        font.draw(sb, glyphLayout4, (float) (screenWidth*0.65) + (glyphLayout3.width/2) - (glyphLayout4.width/2), (float) (screenHeight*0.9));
    }

    public void dispose() {
        stopSign.dispose();
        font.dispose();
        gameModel.getCar().dispose();
        policeTexture.dispose();
        midTexture.dispose();
        finishLine.dispose();
        topSector.dispose();
        background.dispose();
    }
}
