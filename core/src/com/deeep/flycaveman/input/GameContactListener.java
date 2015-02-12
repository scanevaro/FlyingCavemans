package com.deeep.flycaveman.input;

import com.badlogic.gdx.physics.box2d.*;
import com.deeep.flycaveman.classes.Assets;
import com.deeep.flycaveman.entities.CaveMan;
import com.deeep.flycaveman.entities.Ground;
import com.deeep.flycaveman.entities.Obstacle;
import com.deeep.flycaveman.entities.PowerUp;

/**
 * Created by scanevaro on 14/10/2014.
 */
public class GameContactListener implements ContactListener {
    private com.deeep.flycaveman.classes.World world;
    private float force;

    public GameContactListener(com.deeep.flycaveman.classes.World world) {
        this.world = world;
    }

    @Override
    public void beginContact(Contact contact) {
        final Fixture fixtureA = contact.getFixtureA();
        final Fixture fixtureB = contact.getFixtureB();

        if (fixtureA.getUserData() instanceof Obstacle || fixtureB.getUserData() instanceof Obstacle) {
            Obstacle obstacle = null;
            if (fixtureA.getUserData() != null)
                obstacle = (Obstacle) fixtureA.getUserData();
            else obstacle = (Obstacle) fixtureB.getUserData();

            switch (obstacle.type) {
                case 0: //SMALL_EGG
                    world.caveman.body.applyForce(1500, 1500, world.caveman.body.getPosition().x, world.caveman.body.getPosition().y, true);
                    break;
                case 1: //BRACHIOSAURUS
                    world.caveman.body.applyForce(5000, 5000, world.caveman.body.getPosition().x, world.caveman.body.getPosition().y, true);
                    break;
                case 2: //QUETZALCOATLUS
                    world.caveman.body.applyForce(2500, 2500, world.caveman.body.getPosition().x, world.caveman.body.getPosition().y, true);
                    break;
            }
        } else if (fixtureA.getUserData() instanceof Ground || fixtureB.getUserData() instanceof Ground) {
            GameInputProcessor.touchingGround = true;
            GameInputProcessor.flying = false;
            force = -1;
        } else if (fixtureA.getUserData() instanceof PowerUp || fixtureB.getUserData() instanceof PowerUp) {
            PowerUp powerUp = null;
            if (fixtureA.getUserData() instanceof PowerUp) {
                powerUp = (PowerUp) fixtureA.getUserData();
            } else {
                powerUp = (PowerUp) fixtureB.getUserData();
            }
            world.caveman.stamina = Math.max(0, Math.min(world.caveman.maxStamina, world.caveman.stamina + ((float) powerUp.type.getPercentage() / 100f) * world.caveman.maxStamina));
            if (powerUp.type == PowerUp.Type.SPINACH) {
                //extreme
            }
            powerUp.die();
        }
    }

    @Override
    public void endContact(Contact contact) {
        GameInputProcessor.touchingGround = false;
        GameInputProcessor.flying = true;

        final Fixture fixtureA = contact.getFixtureA();
        final Fixture fixtureB = contact.getFixtureB();

        if ((fixtureA.getUserData() != null && fixtureA.getUserData() instanceof Obstacle) || (fixtureB.getUserData() != null && fixtureB.getUserData() instanceof Obstacle)) {

        } else if ((fixtureA.getUserData() instanceof CaveMan && fixtureB.getUserData() instanceof Ground)
                || (fixtureB.getUserData() instanceof CaveMan && fixtureA.getUserData() instanceof Ground)) {

            if (world.caveman.springs > 0) {
                world.caveman.useSpring();
                return;
            }

            Assets.hitGround1Sound.play();
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    public void update() {
        if (GameInputProcessor.touchingGround && world.caveman.body.getLinearVelocity().x > 0) {
            force -= 2.0f;
            world.caveman.body.applyForce(force, 0, world.caveman.body.getPosition().x, world.caveman.body.getPosition().y, true);
        }
    }
}
