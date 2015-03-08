package com.deeep.flycaveman.input;

import com.badlogic.gdx.physics.box2d.*;
import com.deeep.flycaveman.Assets;
import com.deeep.flycaveman.entities.*;
import com.deeep.flycaveman.world.World;

/**
 * Created by scanevaro on 14/10/2014.
 */
public class GameContactListener implements ContactListener {
    private com.deeep.flycaveman.world.World world;
    private float force;
    private final float smallEggForce = 10;
    private final float brachioForce = 20;
    private final float quetzaForce = 18;

    public GameContactListener(World world) {
        this.world = world;
    }

    public void update() {
        if (GameInputProcessor.touchingGround && world.caveman.body.getLinearVelocity().x > 0) {
            force -= world.caveman.body.getLinearVelocity().x / 2;
            world.caveman.body.applyForceToCenter(force, 0, true);
        }
    }

    @Override
    public void beginContact(Contact contact) {
        final Fixture fixtureA = contact.getFixtureA();
        final Fixture fixtureB = contact.getFixtureB();
        if (!world.caveman.cheats) {
            if (fixtureA.getUserData() instanceof Obstacle || fixtureB.getUserData() instanceof Obstacle) {
                Obstacle obstacle = null;
                if (fixtureA.getUserData() != null)
                    obstacle = (Obstacle) fixtureA.getUserData();
                else obstacle = (Obstacle) fixtureB.getUserData();

                switch (obstacle.type) {
                    case 0: //SMALL_EGG
                        world.caveman.body.setLinearVelocity(world.caveman.body.getLinearVelocity().x, smallEggForce + Math.abs(world.caveman.body.getLinearVelocity().y / 2));
                        break;
                    case 1: //BRACHIOSAURUS
                        world.caveman.body.setLinearVelocity(brachioForce / 2 + world.caveman.body.getLinearVelocity().x, brachioForce + Math.abs(world.caveman.body.getLinearVelocity().y / 2));
                        break;
                    case 2: //QUETZALCOATLUS
                        world.caveman.body.setLinearVelocity(world.caveman.body.getLinearVelocity().x, quetzaForce + Math.abs(world.caveman.body.getLinearVelocity().y / 2));
                        break;
                }

                obstacle.hit();

                Assets.hitEntity1.play();

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

                if (powerUp.type == PowerUp.Type.MEAT) {
                    Assets.eat1.play();
                } else if (powerUp.type == PowerUp.Type.SODACAN) {
                    Assets.canOpen1.play();
                } else if (powerUp.type == PowerUp.Type.SPINACH) {
                    Assets.burp3.play();

                    world.caveman.setState(CaveMan.STATE_PASSION);
                } else if (powerUp.type == PowerUp.Type.VODKA) {
                    Assets.slurp.play();

                    world.caveman.setState(CaveMan.STATE_PAIN);
                }

                powerUp.die();
            }
        }
        //if (fixtureA.getUserData() instanceof Coin || fixtureB.getUserData() instanceof Coin) {
        //    System.out.println("Coin babyyyy");
        //    world.caveman.coins ++;
        //    switch (world.caveman.coinStreak){
        //        case 0:
        //            world.area.soundManager.playSound(Assets.coin1_sound);
        //            break;
        //        case 1:
        //            world.area.soundManager.playSound(Assets.coin2_sound);
        //            break;
        //        case 2:
        //            world.area.soundManager.playSound(Assets.coin3_sound);
        //            break;
        //        case 3:
        //            world.area.soundManager.playSound(Assets.coin4_sound);
        //            break;
        //    }
        //    world.caveman.coinStreak ++;
        //    if(world.caveman.coinStreak > 3) world.caveman.coinStreak = 3;
        //}
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
            Assets.hitGround();
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
}