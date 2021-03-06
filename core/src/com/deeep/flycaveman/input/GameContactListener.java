package com.deeep.flycaveman.input;

import com.badlogic.gdx.physics.box2d.*;
import com.deeep.flycaveman.Assets;
import com.deeep.flycaveman.Core;
import com.deeep.flycaveman.entities.*;
import com.deeep.flycaveman.screens.GameScreen;
import com.deeep.flycaveman.world.MusicController;
import com.deeep.flycaveman.world.World;

/**
 * Created by scanevaro on 14/10/2014.
 */
public class GameContactListener implements ContactListener {
    private Core game;
    private GameScreen screen;
    private com.deeep.flycaveman.world.World world;
    private float force;
    private final float smallEggForce = 10, sabretoothForce = 15, brachioForce = 20, quetzaForce = 18, argenForce = 20,
            toucanForce = 35, mosquitoForce = 16;

    public GameContactListener(Core game, World world) {
        this.game = game;
        screen = (GameScreen) game.screen;
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
                if (fixtureA.getUserData() != null) obstacle = (Obstacle) fixtureA.getUserData();
                else obstacle = (Obstacle) fixtureB.getUserData();
                switch (obstacle.type) {
                    case SMALL_EGG: /**SMALL_EGG*/
                        world.caveman.body.setLinearVelocity(world.caveman.body.getLinearVelocity().x,
                                smallEggForce + Math.abs(world.caveman.body.getLinearVelocity().y / 2));
                        break;
                    case BRACHIOSAURUS: /**BRACHIOSAURUS*/
                        String fix = "";
                        if (fixtureA.getBody().getUserData() != null) fix = (String) fixtureA.getBody().getUserData();
                        else if (fixtureB.getBody().getUserData() != null)
                            fix = (String) fixtureB.getBody().getUserData();
                        if (fix.equals("BodyHead"))
                            world.caveman.body.setLinearVelocity(
                                    brachioForce / 2 + world.caveman.body.getLinearVelocity().x,
                                    brachioForce + Math.abs(world.caveman.body.getLinearVelocity().y / 2));
                        else if (fix.equals("BodyTail")) world.caveman.body.setLinearVelocity(
                                brachioForce / 2.5f + world.caveman.body.getLinearVelocity().x,
                                brachioForce + Math.abs(world.caveman.body.getLinearVelocity().y / 2));
                        else world.caveman.body.setLinearVelocity(world.caveman.body.getLinearVelocity().x,
                                    brachioForce + Math.abs(world.caveman.body.getLinearVelocity().y / 2));
                        break;
                    case QUETZALCOATLUS: /**QUETZALCOATLUS*/
                        world.caveman.body.setLinearVelocity(world.caveman.body.getLinearVelocity().x,
                                quetzaForce + Math.abs(world.caveman.body.getLinearVelocity().y / 2));
                        break;
                    case ARGENTAVIS: /**ARGENTAVIS*/
                        world.caveman.body.setLinearVelocity(world.caveman.body.getLinearVelocity().x,
                                argenForce + Math.abs(world.caveman.body.getLinearVelocity().y / 2));
                        break;
                    case TOUCAN: /**TOUCAN*/
                        world.caveman.body.setLinearVelocity(world.caveman.body.getLinearVelocity().x,
                                toucanForce + Math.abs(world.caveman.body.getLinearVelocity().y / 2));
                        break;
                    case SABRETOOTH: /**SABRETOOTH*/
                        world.caveman.body.setLinearVelocity(world.caveman.body.getLinearVelocity().x,
                                sabretoothForce + Math.abs(world.caveman.body.getLinearVelocity().y / 2));
                        break;
                    case MOSQUITO: /**MOSQUITO*/
                        world.caveman.body.setLinearVelocity(world.caveman.body.getLinearVelocity().x,
                                mosquitoForce + Math.abs(world.caveman.body.getLinearVelocity().y / 2));
                        break;
                    case CARNIVORE: /**CARNIVORE PLANT*/
                        world.caveman.eaten();
                        break;
                }
                if (obstacle.type == Obstacle.Type.BRACHIOSAURUS) {
                    if (fixtureA.getBody().getUserData() != null) obstacle.hit(fixtureA.getBody());
                    else obstacle.hit(fixtureB.getBody());
                } else {
                    obstacle.hit();
                    Assets.hitEntity1.play();
                }
                world.caveman.smacked++;
            } else if (fixtureA.getUserData() instanceof Ground || fixtureB.getUserData() instanceof Ground) {
                if (!GameInputProcessor.catapulting) {
                    GameInputProcessor.touchingGround = true;
                    world.caveman.stateTime = 0;
                    GameInputProcessor.flying = false;
                    force = -1;
                    if ((world.caveman.fixture.getRestitution() / 2) <= 0.1f)
                        world.caveman.fixture.setRestitution(0.1f);
                    else world.caveman.fixture.setRestitution(world.caveman.fixture.getRestitution() / 2);
//                    if (Settings.soundEnabled) Assets.drag.play();
                }
            } else if (fixtureA.getUserData() instanceof PowerUp || fixtureB.getUserData() instanceof PowerUp) {
                PowerUp powerUp = null;
                if (fixtureA.getUserData() instanceof PowerUp) powerUp = (PowerUp) fixtureA.getUserData();
                else powerUp = (PowerUp) fixtureB.getUserData();
                world.caveman.stamina = Math.max(0, Math.min(world.caveman.maxStamina,
                        world.caveman.stamina + ((float) powerUp.type.getPercentage() / 100f) * world.caveman.maxStamina));
                if (powerUp.type == PowerUp.Type.SPINACH) world.caveman.grabSpinach();
                if (powerUp.type == PowerUp.Type.MEAT) Assets.eat1.play();
                else if (powerUp.type == PowerUp.Type.SODACAN) Assets.canOpen1.play();
                else if (powerUp.type == PowerUp.Type.SPINACH) {
                    Assets.burp3.play();
                    world.caveman.setState(CaveMan.STATE_PASSION);
                } else if (powerUp.type == PowerUp.Type.VODKA) {
                    Assets.slurp.play();
                    world.caveman.setState(CaveMan.STATE_PAIN);
                }
                powerUp.die();
                world.caveman.powerUpsPicked++;
                screen.pickupsWidget.show(powerUp.type);
            }
            /**Collision for coins*/
            else if (fixtureA.getUserData() instanceof Coin || fixtureB.getUserData() instanceof Coin) {
                world.caveman.coins++;
                switch (world.caveman.coinStreak) {
                    case 0:
                        MusicController.musicController.soundManager.playSound(Assets.coin1_sound);
                        break;
                    case 1:
                        MusicController.musicController.soundManager.playSound(Assets.coin2_sound);
                        break;
                    case 2:
                        MusicController.musicController.soundManager.playSound(Assets.coin3_sound);
                        break;
                    case 3:
                        MusicController.musicController.soundManager.playSound(Assets.coin4_sound);
                        break;
                }
                screen.pickupsWidget.show(world.caveman.coinStreak);
                world.caveman.coinStreak++;
                if (world.caveman.coinStreak > 3) world.caveman.coinStreak = 3;
                if (fixtureA.getUserData() instanceof Coin) ((Coin) fixtureA.getUserData()).die();
                if (fixtureB.getUserData() instanceof Coin) ((Coin) fixtureB.getUserData()).die();
                world.caveman.coinsPicked++;
            }
        }
    }

    @Override
    public void endContact(Contact contact) {
//        Assets.drag.stop();
        GameInputProcessor.touchingGround = false;
        GameInputProcessor.flying = true;
        final Fixture fixtureA = contact.getFixtureA();
        final Fixture fixtureB = contact.getFixtureB();
        if ((fixtureA.getUserData() != null && fixtureA.getUserData() instanceof Obstacle) ||
                (fixtureB.getUserData() != null && fixtureB.getUserData() instanceof Obstacle)) {
        } else if ((fixtureA.getUserData() instanceof CaveMan && fixtureB.getUserData() instanceof Ground) ||
                (fixtureB.getUserData() instanceof CaveMan && fixtureA.getUserData() instanceof Ground)) {
            if (!GameInputProcessor.catapulting) {
                if (world.caveman.springsJumps > 0) {
                    world.caveman.useSpring();
                    return;
                }
                Assets.hitGround1Sound.play();
                Assets.hitGround();
            }
        }
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
    }
}