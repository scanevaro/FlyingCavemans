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
    //    private MouseJoint mouseJoint = null;
    //    private Body hitBody = null;
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

//    Vector3 testPoint = new Vector3();
//    QueryCallback callback = new QueryCallback() {
//        @Override
//        public boolean reportFixture(Fixture fixture) {
//            // if the hit point is inside the fixture of the armBody
//            // we report it
//            if (fixture.testPoint(testPoint.x, testPoint.y)) {
//                hitBody = fixture.getBody();
//                return false;
//            } else
//                return true;
//        }
//    };

    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
//        camera.unproject(testPoint.set(x, y, 0));
//        // ask the world which bodies are within the given
//        // bounding box around the mouse pointer
//        hitBody = null;
//        world.box2dWorld.QueryAABB(callback, testPoint.x - 0.0001f, testPoint.y - 0.0001f, testPoint.x + 0.0001f, testPoint.y + 0.0001f);
//
//        if (hitBody == groundBody) hitBody = null;
//
//        // ignore kinematic bodies, they don't work with the mouse joint
//        if (hitBody != null && hitBody.getType() == BodyDef.BodyType.KinematicBody) return false;
//
//        // if we hit something we create a new mouse joint
//        // and attach it to the hit armBody.
//        if (hitBody != null) {
//            MouseJointDef def = new MouseJointDef();
//            def.bodyA = groundBody;
//            def.bodyB = hitBody;
//            def.collideConnected = true;
//            def.target.set(testPoint.x, testPoint.y);
//            def.maxForce = 1000.0f * hitBody.getMass();
//
//            mouseJoint = (MouseJoint) world.box2dWorld.createJoint(def);
//            hitBody.setAwake(true);
//        }

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
//        if (mouseJoint != null) {
//            world.box2dWorld.destroyJoint(mouseJoint);
//            mouseJoint = null;
//        }

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

//    Vector2 target = new Vector2();

    @Override
    public boolean touchDragged(int x, int y, int pointer) {
//        if (mouseJoint != null) {
//            camera.unproject(testPoint.set(x, y, 0));
//            mouseJoint.setTarget(target.set(testPoint.x, testPoint.y));
//        }
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