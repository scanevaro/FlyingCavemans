package com.deeep.flycaveman.classes;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.deeep.flycaveman.Core;
import com.deeep.flycaveman.entities.Catapult;
import com.deeep.flycaveman.entities.CaveMan;
import com.deeep.flycaveman.entities.Ground;
import com.deeep.flycaveman.entities.Obstacle;
import com.deeep.flycaveman.input.GameContactListener;
import net.dermetfan.utils.libgdx.graphics.Box2DSprite;

import java.util.Random;

/**
 * Created by scanevaro on 12/10/2014.
 */
public class World {
    private final float TIMESTEP = 1 / 60f;
    private final int VELOCITYITERATIONS = 8, POSITIONITERATIONS = 3;

    private OrthographicCamera camera;
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private Vector2 sky;
    public com.badlogic.gdx.physics.box2d.World box2dWorld;
    private GameContactListener gameContactListener;
    private Random random;
    public Ground ground;
    public Catapult catapult;
    private Obstacle[] obstacle;
    public CaveMan caveman;
    private Box2DDebugRenderer debugRenderer;

    private Sprite backgroundSprite;
    private float scrollTimer;
    private float obstaclesPosX;

    public boolean flying;
    public boolean remove;
    private float shootStateTime;

    public boolean gameOver;

    public World(Core game, boolean debug) {
        this.camera = game.getCamera();
        this.batch = game.getSpriteBatch();
        shapeRenderer = new ShapeRenderer();

        Texture backgroundTexture = Assets.getAssets().getBackgroundTexture();
        backgroundTexture.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        backgroundSprite = new Sprite(backgroundTexture, 0, 0, 1920, 720);
        backgroundSprite.setSize(Core.BOX2D_VIRTUAL_WIDTH + Core.BOX2D_VIRTUAL_WIDTH / 2, Core.BOX2D_VIRTUAL_HEIGHT);
        scrollTimer = 0;

        box2dWorld = new com.badlogic.gdx.physics.box2d.World(new Vector2(0, -9.81f), true);
        box2dWorld.setContactListener(gameContactListener = new GameContactListener(this));

        ground = new Ground(box2dWorld);

        catapult = new Catapult(box2dWorld, ground);

        obstacle = new Obstacle[5];
        obstaclesPosX = 0;
        random = new Random();
        for (int i = 0; i < obstacle.length; i++) {
            obstaclesPosX += 50 + random.nextInt(50);
            obstacle[i] = new Obstacle(box2dWorld, obstaclesPosX, random);
        }

        //TODO powerups (food, probably)

        caveman = new CaveMan(this);

        sky = new Vector2(caveman.body.getPosition().x - 10, caveman.body.getPosition().y - 8);

        if (debug)
            debugRenderer = new Box2DDebugRenderer();

        shootStateTime = 0;
    }

    public void draw() {
        shapeRenderer.setProjectionMatrix(camera.combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0, 0.5f, 0.8f, 1);
        shapeRenderer.rect(sky.x, sky.y - 32, Core.BOX2D_VIRTUAL_WIDTH + 32, Core.BOX2D_VIRTUAL_HEIGHT + 32);
        shapeRenderer.end();

        batch.begin();

        backgroundSprite.draw(batch);

        batch.end();
        batch.begin();
        //When this line is between spritebatch.begin and spritebatch.end. it makes the later calls to draw dont work
        debugRenderer.render(box2dWorld, camera.combined);

        batch.end();
        batch.begin();

        Box2DSprite.draw(batch, box2dWorld);

        batch.end();
    }

    public void update(float delta) {
        gameContactListener.update();
        updateSky();
        updateBackground();
        updateGround();
        updateObstacles();
//        updatePowerUps();
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

        checkGameOver();
    }

    private void updateSky() {
        if (caveman.body.getPosition().y < 8)
            sky.set(caveman.body.getPosition().x + (Core.BOX2D_VIRTUAL_WIDTH / 2 - Core.BOX2D_VIRTUAL_WIDTH / 3) - Core.BOX2D_VIRTUAL_WIDTH / 2, 8);
        else
            sky.set(caveman.body.getPosition().x + (Core.BOX2D_VIRTUAL_WIDTH / 2 - Core.BOX2D_VIRTUAL_WIDTH / 3) - Core.BOX2D_VIRTUAL_WIDTH / 2, caveman.body.getPosition().y - Core.BOX2D_VIRTUAL_HEIGHT / 3);
    }

    private void updateBackground() {
        if (caveman.body.getPosition().x > backgroundSprite.getX() + Core.BOX2D_VIRTUAL_WIDTH / 3)
            scrollTimer += (caveman.body.getPosition().x - backgroundSprite.getX() - Core.BOX2D_VIRTUAL_WIDTH / 3) / 50.005;

//        scrollTimer += delta - delta / 2;

        if (scrollTimer > 1.0f)
            scrollTimer = 0.0f;

        backgroundSprite.setU(scrollTimer);
        backgroundSprite.setU2(scrollTimer + 1);

        backgroundSprite.setPosition(caveman.body.getPosition().x + (Core.BOX2D_VIRTUAL_WIDTH / 2 - Core.BOX2D_VIRTUAL_WIDTH / 3) - Core.BOX2D_VIRTUAL_WIDTH / 2, 0);
    }

    private void updateGround() {
        if (flying && shootStateTime > 1)
            ground.body.setTransform(caveman.body.getPosition().x + (Core.BOX2D_VIRTUAL_WIDTH / 2 - Core.BOX2D_VIRTUAL_WIDTH / 3) - Core.BOX2D_VIRTUAL_WIDTH / 2, 1, 0);
    }

    private void updateObstacles() {
        for (int i = 0; i < obstacle.length; i++)
            if (obstacle[i].body.getPosition().x < caveman.body.getPosition().x - 32) {
                box2dWorld.destroyBody(obstacle[i].body);
                obstacle[i] = new Obstacle(box2dWorld, obstaclesPosX += 50 + obstaclesPosX / 8, random);
            }
    }

    private void updateWorld() {
        box2dWorld.step(TIMESTEP, VELOCITYITERATIONS, POSITIONITERATIONS);
    }

    private void checkGameOver() {
        if (caveman.body.getLinearVelocity().x <= 0)
            gameOver = true;
    }

    public boolean isGameOver() {
        return gameOver;
    }
}