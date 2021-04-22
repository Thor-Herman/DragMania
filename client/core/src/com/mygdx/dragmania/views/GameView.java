package com.mygdx.dragmania.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.dragmania.models.Car;
import com.mygdx.dragmania.models.GameMap;
import com.mygdx.dragmania.models.GameModel;
import com.mygdx.dragmania.models.Pedestrian;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameView extends View {

    private int mapLength = 1000*10; // Finish line is at score 2000 (score = position/10)
    private int carTextureStartOffset = 225;

    private float screenWidth;
    private float screenHeight;

    private BitmapFont font;
    private BackArrow backArrow;

    private GameModel gameModel;
    private Car car;
    private GameMap gameMap;
    private Pedestrian pedestrian;


    protected GameView(ViewManager viewManager) {
        super(viewManager);
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        // Only for testing purposes
        List<Integer> pedestrianPlacements = new ArrayList<>(Arrays.asList(
                // 200*10
        ));
        List<Integer> policeTurn = new ArrayList<>(Arrays.asList(
                200*10,
                400*10,
                700*10
        ));
        List<Integer> policeFake = new ArrayList<>(Arrays.asList(
                50*10,
                250*10
        ));

        gameModel = new GameModel("player", pedestrianPlacements, policeTurn, policeFake, mapLength);
        gameMap = gameModel.getGameMap();
        car = gameModel.getCar();
        car.setScaledTexture(0.33);

        // Generating font
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("GovtAgentBB.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 50;
        font = generator.generateFont(parameter);
        generator.dispose();

        // Back arrow
        backArrow = new BackArrow(0,0);
    }

    @Override
    public void update(float dt) {
        // Update other classes depending on whether the player is touching and check if back arrow is touched
        boolean isTouched = Gdx.input.isTouched();
        if (isTouched) {
            checkBackTouched(backArrow);
        }
        gameModel.update(dt, isTouched);

        // Finish game if player crosses the finish line
        if(car.getPosition().y > gameMap.getMapLength()) {
            viewManager.push(new GameFinishedView(viewManager));
        }

        pedestrian = gameModel.getGameMap().getPedestrian();
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
        renderMidLines(sr);
        drawFinishLine(sr);
        renderTopSector(sr);
        sr.end();

        SpriteBatch sb = new SpriteBatch();
        sb.begin();
        drawTextures(sb);
        drawFonts(sb);
        sb.end();
    }

    public void renderBackground(ShapeRenderer sr) {
        sr.setColor(Color.DARK_GRAY);
        sr.begin(ShapeRenderer.ShapeType.Filled);
        sr.rect(0,0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    public void drawFinishLine(ShapeRenderer sr) {
        if(car.getPosition().y >= gameMap.getMapLength()-screenHeight) {
            float diff = mapLength-car.getPosition().y;
            sr.setColor(Color.WHITE);
            sr.rect(0, carTextureStartOffset + diff, screenWidth, 50);
        }
    }

    public void renderTopSector(ShapeRenderer sr) {
        sr.setColor(Color.valueOf("1c1c1c"));
        sr.rect(0, Gdx.graphics.getHeight()-400, Gdx.graphics.getWidth(), 400);
    }

    public void drawFonts(SpriteBatch sb) {
        font.draw(sb, "Your score: ", 100, 1975); // TODO: Use dynamic y value
        font.draw(sb, Integer.toString(gameModel.getPlayerScore()), 200, 1875);
        font.draw(sb, "Rivals score: ", 700, 1975);
        font.draw(sb, Integer.toString(gameModel.getOpponentScore()), 825, 1875);
    }

    public void renderMidLines(ShapeRenderer sr){
        int lineWidth = 20;
        int lineHeight = 200;
        int lineSpace = 50;

        sr.setColor(Color.YELLOW);

        for (int screenPos = -(lineHeight+lineSpace); screenPos <= 0; screenPos++) {
            if ((Math.floor(car.getPosition().y) + screenPos) % (lineHeight+lineSpace) == 0) {
                // Find out where to start drawing lines
                for (int lineStartPos = screenPos; lineStartPos < screenHeight; lineStartPos += (lineHeight+lineSpace)) {
                    // Draw the all visible lines
                    sr.rect(screenWidth/2-(lineWidth)/2, lineStartPos, lineWidth, lineHeight);
                }
                break;
            }
        }
    }

    public void drawTextures(SpriteBatch sb) {
        // Back arrow
        sb.draw(backArrow.getBackArrow(), backArrow.getPosition().x, backArrow.getPosition().y, backArrow.getWidth()/3, backArrow.getHeight()/3);

        // Policeman
        Texture policeTexture = gameModel.getGameMap().getPoliceman().getAnimation();
        sb.draw(policeTexture, screenWidth/2-policeTexture.getWidth(), screenHeight-400, policeTexture.getWidth()*2, policeTexture.getHeight()*2);

        // Car
        Texture carTexture = car.getTexture();
        sb.draw(carTexture, Gdx.graphics.getWidth()/2-car.getTexture().getWidth()/2, carTextureStartOffset, carTexture.getWidth(), carTexture.getHeight());

        // Pedestrian
        if (pedestrian != null) {
            Texture pedestrianTexture = pedestrian.getTexture();
            Vector2 pedestrianPosition = pedestrian.getPosition();
            int carTextureTop = carTextureStartOffset + carTexture.getHeight();
            float carPedestrianDistance = pedestrianPosition.y - car.getTopYPosition();
            sb.draw(pedestrianTexture, pedestrianPosition.x, carTextureTop + carPedestrianDistance, pedestrianTexture.getWidth(), pedestrianTexture.getHeight());
        }
    }
}
