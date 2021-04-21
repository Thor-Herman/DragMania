package com.mygdx.dragmania.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.dragmania.models.Car;
import com.mygdx.dragmania.models.GameModel;
import com.mygdx.dragmania.models.Pedestrian;

import java.util.ArrayList;
import java.util.List;

public class GameView extends View{

    private Car car;
    private float screenWidth;
    private float screenHeight;
    private Texture policeMan;
    private GameModel gameModel;

    private List<Integer> pedestrianPlacements;
    private Pedestrian pedestrian;
    private ArrayList<Integer> policeturn;
    private ArrayList<Integer> policefake;

    private int[] midLineYPositions;
    private int finishLineYPos;
    private int finishLineOffset;

    private BitmapFont font;

    private BackArrow backArrow;

    protected GameView(ViewManager viewManager) {
        super(viewManager);
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        // Only for testing purposes
        pedestrianPlacements = new ArrayList<>();
        pedestrianPlacements.add(1100);
        pedestrianPlacements.add(1400);
        pedestrianPlacements.add(1700);
        pedestrianPlacements.add(2000);
        policeturn = new ArrayList<>();
        //policeturn.add(200);
        // policeturn.add(400);
        // policeturn.add(700);
        policefake = new ArrayList<>();
        policefake.add(50);
        policefake.add(250);

        gameModel = new GameModel("player", pedestrianPlacements, policeturn, policefake, 2000);
        car = gameModel.getCar();
        car.setScaledTexture(0.33);
        car.overrideHitBox(Gdx.graphics.getWidth()/2-car.getTexture().getWidth()/2, 225, car.getTexture().getWidth(), car.getTexture().getHeight());
        car.reposition(car.getPosition());

        // Generating font
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("GovtAgentBB.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 50;
        font = generator.generateFont(parameter);
        generator.dispose();

        // Setting midline positions initially
        midLineYPositions = new int[5];
        midLineYPositions[0] = 1800;
        midLineYPositions[1] = 1400;
        midLineYPositions[2] = 1000;
        midLineYPositions[3] = 600;
        midLineYPositions[4] = 200;
        backArrow = new BackArrow(0,0);
        // Initialize finish line to be under the screen
        finishLineYPos = -400;
        // Converting screen size to meters
        finishLineOffset = 12;
    }


    @Override
    public void update(float dt) {
        // Update other classes depending on wheter the player is touching and check if backarrow is touched
        boolean isTouched = Gdx.input.isTouched();
        if(isTouched) {
            checkBackTouched(backArrow);
        }
        gameModel.update(dt, isTouched);

        // Set finish line position if the player has come far enough
        if(car.getPosition().y >= gameModel.getGameMap().getMapLength()-finishLineOffset && finishLineYPos == -400) {
            finishLineYPos = 1900;
        }

        // Finish game if player crosses the finishline
        if(car.getPosition().y > gameModel.getGameMap().getMapLength()) {
            viewManager.push(new GameFinishedView(viewManager));
        }
    }

    @Override
    public void handleInput() {

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        ShapeRenderer sr = new ShapeRenderer();
        renderBackground(sr);
        moveLines();
        renderMidLines(sr);
        drawFinishLine(sr, finishLineYPos);
        renderTopSector(sr);
        sr.end();
        SpriteBatch sb = new SpriteBatch();
        sb.begin();
        drawTextures(sb);
        drawFonts(sb);
        sb.end();
    }

    public void renderMidLines(ShapeRenderer sr){
        drawMidLine(sr, midLineYPositions[0]);
        drawMidLine(sr, midLineYPositions[1]);
        drawMidLine(sr, midLineYPositions[2]);
        drawMidLine(sr, midLineYPositions[3]);
        drawMidLine(sr, midLineYPositions[4]);
    }

    public void renderBackground(ShapeRenderer sr) {
        sr.setColor(Color.DARK_GRAY);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.rect(0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public void drawMidLine(ShapeRenderer sr, int yPos) {
        int lineWidth = 20;
        int lineHeight = 200;
        sr.setColor(Color.YELLOW);
        sr.rect(screenWidth/2-(lineWidth)/2, yPos, lineWidth, lineHeight);
    }

    // Move midlines according to the car velocity
    public void moveMidlines(int offset) {
        if(offset > 0) {
            for(int i = 0; i < 5; i++) {
                midLineYPositions[i] = midLineYPositions[i] - offset;
            }
        }
    }

    public void drawFinishLine(ShapeRenderer sr, int yPos) {
        if(car.getPosition().y >= gameModel.getGameMap().getMapLength()-finishLineOffset) {
            sr.setColor(Color.WHITE);
            sr.rect(0, yPos, screenWidth, 50);
        }
    }

    // Only move finish line if player has come far enough
    public void moveFinishLine(int offset) {
        if(finishLineYPos > -400) {
            finishLineYPos -= offset;
        }
    }

    public void renderTopSector(ShapeRenderer sr) {
        sr.setColor(Color.valueOf("1c1c1c"));
        sr.rect(0, Gdx.graphics.getHeight()-400, Gdx.graphics.getWidth(), 400);
    }

    public void moveLines() {
        repositionLine();
        moveMidlines((int) car.getVelocity());
        moveFinishLine((int) car.getVelocity());
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
        Texture carTexture = car.getTexture();
        sb.draw(carTexture, Gdx.graphics.getWidth()/2-car.getTexture().getWidth()/2, 225, carTexture.getWidth(), carTexture.getHeight());
        Texture policeTexture = gameModel.getGameMap().getPoliceman().getAnimation();
        sb.draw(policeTexture, screenWidth/2-policeTexture.getWidth(), screenHeight-400, policeTexture.getWidth()*2, policeTexture.getHeight()*2);
        sb.draw(backArrow.getBackArrow(), backArrow.getPosition().x, backArrow.getPosition().y, backArrow.getWidth()/3, backArrow.getHeight()/3);
        this.pedestrian = gameModel.getGameMap().getPedestrian();
        if (this.pedestrian != null) {
            Texture pedestrianTexture = this.pedestrian.getTexture();
            Vector2 pedestrianPosition = this.pedestrian.getPosition();
            sb.draw(pedestrianTexture, pedestrianPosition.x, pedestrianPosition.y, pedestrianTexture.getWidth(), pedestrianTexture.getHeight());
        }
    }

    public void drawFonts(SpriteBatch sb) {
        font.draw(sb, "Your score: ", 100, 1975);
        font.draw(sb, Integer.toString((int) car.getPosition().y), 200, 1875);
        font.draw(sb, "Rivals score: ", 700, 1975);
        font.draw(sb, Integer.toString(gameModel.getOpponentScore()), 825, 1875);
    }
}
