package com.deeep.flycaveman.input;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.deeep.flycaveman.Core;
import com.deeep.flycaveman.classes.World;
import com.deeep.flycaveman.screens.GameScreen;

/**
 * Created by scanevaro on 11/10/2014.
 */
public class GameInputProcessor implements InputProcessor {
    private final int IDLE = 0;
    private final int CATAPULTSET = 1;
    private final int FLYING = 2;
    private final int FLAPPING = 3;
    private final int DRAGGING = 4;
    private final int GAMEOVER = 5;
    private final int PAUSE = 6;
    private int gameState;

    private Core game;
    private World world;
    private boolean addForce;
    public static boolean flying;
    public static boolean touchingGround;
    private float force;

    private float strength;
    private float flyTime;

    public GameInputProcessor(Core game, World world) {
        this.game = game;
        this.world = world;

        flyTime = 0.5f;
        strength = 50;

        flying = false;
        touchingGround = false;

        gameState = IDLE;
    }

    public void update(float delta) {
        if (touchingGround)
            gameState = DRAGGING;
        if (flying && gameState != FLAPPING)
            gameState = FLYING;

        switch (gameState) {
            case IDLE:
                //TODO
                break;
            case CATAPULTSET:
                if (force <= 0)
                    addForce = true;
                else if (force > 1.2f)
                    addForce = false;

                if (addForce)
                    force += 0.02f;
                else force -= 0.02f;

                world.catapult.armJoint.setLimits(force, force);

                System.out.println(force);
                break;
            case FLYING:
                world.caveman.body.setAngularVelocity(-3);
                break;
            case FLAPPING:
                if (flyTime > 0) {
                    flyTime -= delta;
                    world.caveman.body.applyForce(strength, strength, world.caveman.body.getPosition().x, world.caveman.body.getPosition().y, true);

                    System.out.print(flyTime);
                }
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
            case FLYING:
                gameState = FLAPPING;
                break;
            case GAMEOVER:
                //TODO
                break;
            case PAUSE:
                //TODO
                break;
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        switch (gameState) {
            case CATAPULTSET:
                world.box2dWorld.destroyJoint(world.caveman.bulletJoint);
                world.catapult.armJoint.setLimits((float) Math.toRadians(9), (float) Math.toRadians(75));
                world.flying = true;
                world.remove = true;

                gameState = FLYING;
                break;
            case FLAPPING:
                gameState = FLYING;
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