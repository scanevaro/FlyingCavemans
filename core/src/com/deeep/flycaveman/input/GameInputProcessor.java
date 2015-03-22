package com.deeep.flycaveman.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;
import com.deeep.flycaveman.Core;
import com.deeep.flycaveman.screens.GameScreen;
import com.deeep.flycaveman.world.World;

/**
 * Created by scanevaro on 11/10/2014.
 */
public class GameInputProcessor implements InputProcessor {
    private final int IDLE = 0;
    private final int CATAPULTSET = 1;
    private final int FLYING = 2;
    //    private final int FLAPPING = 3;
    private final int DRAGGING = 4;
    private final int GAMEOVER = 5;
    private final int PAUSE = 6;
    private int gameState;

    private Core game;
    private World world;
    //private boolean addForce;
    public static boolean flying;
    public static boolean touchingGround;
    private Vector2 originalPos;
    private float gravity;
    //private float force;

    public GameInputProcessor(Core game, World world) {
        this.game = game;
        this.world = world;

        flying = false;
        touchingGround = false;
        originalPos = new Vector2(world.caveman.startPosX, world.caveman.startPosY);
        gameState = IDLE;
        gravity = world.caveman.body.getGravityScale();
        world.caveman.body.setGravityScale(0);
    }

    public void update(float delta) {
        if (touchingGround)
            gameState = DRAGGING;
        if (flying)
            gameState = FLYING;

        switch (gameState) {
            case IDLE:

                //TODO
                break;
            case CATAPULTSET:
                Vector2 mouseCords = new Vector2(Gdx.input.getX(), Gdx.input.getY());
                Vector2 localCords = world.screenToLocalCoordinates(mouseCords);
                localCords.y -= Core.boxUnitToPixels(world.caveman.sprite.getHeight() + world.caveman.sprite.getHeight() / 2);
                localCords.x += Core.boxUnitToPixels(world.caveman.sprite.getWidth() / 2);
                localCords = Core.pixelsToBoxUnit(localCords);
                world.caveman.body.setTransform(localCords, world.caveman.body.getAngle());

                break;
            case FLYING:
                world.caveman.body.setAngularVelocity(0/*-3*/);
                break;
            case DRAGGING:
                world.caveman.body.setAngularVelocity(0);
                break;
        }
    }

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        switch (gameState) {
            case IDLE:
                gameState = CATAPULTSET;
                break;
            case GAMEOVER:
                //TODO
                break;
            case PAUSE:
                //TODO
                break;
            case FLYING:
                world.caveman.flap();
                break;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        switch (gameState) {
            case CATAPULTSET:
                world.flying = true;
                world.remove = true;
                world.caveman.body.setGravityScale(gravity);
                Vector2 mouseCords = new Vector2(Gdx.input.getX(), Gdx.input.getY());
                Vector2 localCords = world.screenToLocalCoordinates(mouseCords);
                localCords.y -= Core.boxUnitToPixels(world.caveman.sprite.getHeight() + world.caveman.sprite.getHeight() / 2);
                localCords.x += Core.boxUnitToPixels(world.caveman.sprite.getWidth() / 2);
                localCords = Core.pixelsToBoxUnit(localCords);
                Vector2 difference = localCords.sub(originalPos);
                world.caveman.body.setLinearVelocity(difference.x * -2 * 5, difference.y * -4 * 5);
                gameState = FLYING;
                flying  = true;
                break;
        }
        return false;
    }

    @Override
    public boolean touchDragged(int x, int y, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.R) {
            game.setScreen(new GameScreen(game));
            return true;
        }
        if (keycode == Input.Keys.BACK) {
            game.dialogs.update(game.screen);
            return true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }
}