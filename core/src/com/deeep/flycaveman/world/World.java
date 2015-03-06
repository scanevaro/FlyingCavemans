package com.deeep.flycaveman.world;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.deeep.flycaveman.Assets;
import com.deeep.flycaveman.Core;
import com.deeep.flycaveman.entities.*;
import com.deeep.flycaveman.input.GameContactListener;
import com.deeep.flycaveman.input.GameInputProcessor;
import com.deeep.flycaveman.screens.GameScreen;

import java.util.Random;

/**
 * Created by scanevaro on 12/10/2014.
 */
public class World extends Actor implements Disposable {
    private final float TIMESTEP = 1 / 60f;
    private final int VELOCITYITERATIONS = 8, POSITIONITERATIONS = 3;

    private Stage worldStage;
    private Stage stage;
    private ShapeRenderer shapeRenderer;
    private Vector2 sky;
    public com.badlogic.gdx.physics.box2d.World box2dWorld;
    private GameContactListener gameContactListener;
    private Random random;
    public Ground ground;
    public Catapult catapult;
    private Sprite darkness;
    public CaveMan caveman;
    private Box2DDebugRenderer debugRenderer;
    private int skyColorHeight = 0;
    private Color skyColor;
    private int sunColorHeight = 40;
    private Color sunColor;
    private int spaceColorHeight = 80;
    private Color spaceColor;
    private int finalSpaceColorHeight = 120;
    public float zoom = 1;

    private int i = 0;

    //private Sprite backgroundSprite;
    private float scrollTimer;
    private float obstaclesPosX;

    private float camPosX;
    private float camPosY;

    private boolean debug;
    public boolean flying;
    public boolean remove;
    private float shootStateTime;

    private Array<Entity> entities;
    private Space space;
    public boolean gameOver;
    public Area area;
    private ObstacleSpawner obstacleSpawner;
    public PowerUpSpawner powerUpSpawner;
    private CoinSpawner coinSpawner;
    private Coin coin;
    private Title title;

    public World(Stage worldStage, Stage stage, boolean debug) {
        this.worldStage = worldStage;
        this.stage = stage;
        area = new Area();
        entities = new Array<Entity>();
        skyColor = new Color(0, 0.5f, 0.8f, 1);
        sunColor = new Color(254f / 255f, 76f / 255f, 64f / 255f, 0f);//rgb(254, 76, 64)
        spaceColor = new Color(0, 0, 0, 1f);//
        shapeRenderer = new ShapeRenderer();
        space = new Space();
        scrollTimer = 0;

        box2dWorld = new com.badlogic.gdx.physics.box2d.World(new Vector2(0, -18.81f), true);
        box2dWorld.setContactListener(gameContactListener = new GameContactListener(this));

        ground = new Ground(box2dWorld);
        coinSpawner = new CoinSpawner();
        entities.add(catapult = new Catapult(box2dWorld, ground));

        obstacleSpawner = new ObstacleSpawner(this);
        powerUpSpawner = new PowerUpSpawner(this);

        entities.add(title = new Title());
        entities.add(caveman = new CaveMan(this));

        sky = new Vector2(caveman.body.getPosition().x - 11.1f, caveman.body.getPosition().y - 8);

        this.debug = debug;
        if (debug) debugRenderer = new Box2DDebugRenderer();

        darkness = new Sprite(Assets.darkSky);
        shootStateTime = 0;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setProjectionMatrix(worldStage.getCamera().combined);
        //System.out.println(worldStage.getCamera().position.x);
        batch.setColor(getColor().r, getColor().g, getColor().b, getColor().a * parentAlpha);

        batch.end();

        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.setTransformMatrix(batch.getTransformMatrix());

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(skyColor);
        //
        float percentage;
        Color bottomColor = shapeRenderer.getColor();
        Color topColor = shapeRenderer.getColor();
        if (caveman.body.getPosition().y > 60) {
            percentage = (caveman.body.getPosition().y - 60) / (30);
            bottomColor = toColor(percentage, sunColor, spaceColor);
            topColor = toColor(Math.min(percentage + 0.4f, 1), sunColor, spaceColor);
            //shapeRenderer.setColor(toColor(percentage, sunColor, spaceColor));
        } else if (caveman.body.getPosition().y > 30) {
            percentage = (caveman.body.getPosition().y - 30) / (30);
            //shapeRenderer.setColor(toColor(percentage, skyColor, sunColor));
            bottomColor = toColor(percentage, skyColor, sunColor);
            topColor = toColor(Math.min(percentage + 0.4f, 1), skyColor, sunColor);
        }
        //System.out.println(worldStage.getViewport().getCamera().position.x + ", " + worldStage.getCamera().position.y);
        //TODO this
        shapeRenderer.rect(
                GameScreen.gameCamera.position.x - ((GameScreen.gameCamera.zoom * GameScreen.gameCamera.viewportWidth) / 2),
                GameScreen.gameCamera.position.y - ((GameScreen.gameCamera.zoom * GameScreen.gameCamera.viewportHeight) / 2),
                0, 0,
                GameScreen.gameCamera.viewportWidth * GameScreen.gameCamera.zoom,
                GameScreen.gameCamera.viewportHeight * GameScreen.gameCamera.zoom,
                1, 1, 0,
                bottomColor, bottomColor, topColor, topColor);/*
        shapeRenderer.rect(
                worldStage.getCamera().position.x - worldStage.getCamera().viewportWidth / 2,
                worldStage.getCamera().position.y - worldStage.getCamera().viewportHeight / 2,
                0, 0,
                worldStage.getCamera().viewportWidth,
                worldStage.getCamera().viewportHeight,
                1, 1, 0,
                bottomColor, bottomColor, topColor, topColor);*/
        //shapeRenderer.rect(sky.x, sky.y - 32, Core.BOX2D_VIRTUAL_WIDTH + 32, Core.BOX2D_VIRTUAL_HEIGHT + 32);

        shapeRenderer.end();

        batch.begin();

        area.draw((SpriteBatch) batch);
        //backgroundSprite.draw(batch);

        batch.end();
        batch.begin();

        if (debug)
            debugRenderer.render(box2dWorld, worldStage.getCamera().combined);

        batch.end();
        batch.begin();

        space.draw((SpriteBatch) batch);
        {/**Draw Box2D Body Textures*/
            for (Entity entity : entities)
                entity.draw(batch);
        }
        obstacleSpawner.draw(batch);
        coinSpawner.render(batch);
        powerUpSpawner.draw((SpriteBatch) batch);
        //darkness.setAlpha(((Math.max(caveman.body.getPosition().y,50)-50)/100));
        //darkness.draw(batch);
        //draw stars
        batch.setProjectionMatrix(stage.getCamera().combined);
    }

    public void update(float delta) {
        coinSpawner.update(delta, caveman, this);
        gameContactListener.update();
        updateCamera();
        updateSky();
        space.update(delta, worldStage.getCamera().position);
        updateBackground();
        updateGround();
        updateObstacles();
        caveman.update(delta);
        powerUpSpawner.update(delta);
        /** updatePowerUps(); */
        updateWorld();

        if (flying)
            shootStateTime += delta;

        if (shootStateTime > 1 && remove) {
            if (caveman.body.getPosition().x > Core.BOX2D_VIRTUAL_WIDTH) {
                box2dWorld.destroyBody(catapult.armBody);
                box2dWorld.destroyBody(catapult.baseBody);
                remove = false;
            }
        }
        area.update(caveman.body);
        checkGameOver();
    }

    private Color toColor(float percentage, Color cur, Color target) {
        // 200 -> 150 @ 10%
        // 50   * 0.1
        // 5
        // 200 - 5 -> 195
        // 150 -> 200 @ 50%
        // -50 * 0.5f
        // -25
        //
        float tempR = percentage * (cur.r - target.r);
        float tempG = percentage * (cur.g - target.g);
        float tempB = percentage * (cur.b - target.b);
        Color returnColor = new Color(cur.r - tempR, cur.g - tempG, cur.b - tempB, 1);
        return returnColor;
    }

    private void updateCamera() {
        /**camera.position.set(relative to caveman X position, relative to caveman Y position, 0);*/
    }

    private void updateSky() {
        if (caveman.body.getPosition().y < 8)
            sky.set(caveman.body.getPosition().x + (Core.BOX2D_VIRTUAL_WIDTH / 2 - Core.BOX2D_VIRTUAL_WIDTH / 3) - Core.BOX2D_VIRTUAL_WIDTH / 2 - 2, 8);
        else
            sky.set(caveman.body.getPosition().x + (Core.BOX2D_VIRTUAL_WIDTH / 2 - Core.BOX2D_VIRTUAL_WIDTH / 3) - Core.BOX2D_VIRTUAL_WIDTH / 2 - 2, caveman.body.getPosition().y - Core.BOX2D_VIRTUAL_HEIGHT / 3);
    }

    private void updateBackground() {
        //if (caveman.body.getPosition().x > backgroundSprite.getX() + Core.BOX2D_VIRTUAL_WIDTH / 3)
        //    scrollTimer += (caveman.body.getPosition().x - backgroundSprite.getX() - Core.BOX2D_VIRTUAL_WIDTH / 3) / 50.005;

//        scrollTimer += delta - delta / 2;

        if (scrollTimer > 1.0f)
            scrollTimer = 0.0f;

//        backgroundSprite.setU(scrollTimer);
//        backgroundSprite.setU2(scrollTimer + 1);

//        backgroundSprite.setPosition(caveman.body.getPosition().x + (Core.BOX2D_VIRTUAL_WIDTH / 2 - Core.BOX2D_VIRTUAL_WIDTH / 3) - Core.BOX2D_VIRTUAL_WIDTH / 2, 0);
    }

    private void updateGround() {
        if (flying && shootStateTime > 1)
            ground.body.setTransform(caveman.body.getPosition().x + (Core.BOX2D_VIRTUAL_WIDTH / 2 - Core.BOX2D_VIRTUAL_WIDTH / 3) - Core.BOX2D_VIRTUAL_WIDTH / 2, 1, 0);
    }

    private void updateObstacles() {
        obstacleSpawner.update();
    }


    private void updateWorld() {
        box2dWorld.step(TIMESTEP, VELOCITYITERATIONS, POSITIONITERATIONS);
    }

    private void checkGameOver() {
        if (caveman.body.getLinearVelocity().x <= 0 && GameInputProcessor.touchingGround)
            gameOver = true;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    @Override
    public void dispose() {
        box2dWorld.dispose();
        shapeRenderer.dispose();
        worldStage.dispose();
        stage.dispose();
    }
}