package com.deeep.flycaveman.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.*;
import com.deeep.flycaveman.Assets;
import com.deeep.flycaveman.input.GameInputProcessor;

/**
 * Created by scanevaro on 11/10/2014.
 */
public class CaveMan implements Entity {
    public boolean cheats = false;
    public final float startPosX = 12.6f;
    public final float startPosY = 4.0f;
    private final float restitution = 0.1f;
    public static final int STATE_HAPPY = 0;
    public static final int STATE_TIRED = 1;
    public static final int STATE_PAIN = 2;
    public static final int STATE_KO = 3;
    public static final int STATE_PASSION = 4;
    public static float spinachTime = -0.5f;
    private BodyDef bodyDef;
    public Body body;
    private FixtureDef fixtureDef;
    public Fixture fixture;
    private CircleShape shape;
    //    private PolygonShape shape;
    //public WeldJoint bulletJoint;
    public float size = .6f;
    public Sprite sprite;
    public float stamina;
    public static final float maxStamina = 5.0f;
    public float strength;
    public int coinStreak = 0;
    public float coinTimer = 2F;
    public final float COIN_PICKUP_INTERVAL = 2F;
    public static int wings;
    public static int springsJumps, staminaSize, steroids, springs, magnet, clench;
    public float stateTimeSprings;
    private float flapStateTime = 0;
    private float stateTime = 0;
    private int state;
    private com.deeep.flycaveman.world.World world;
    public static int coins;
    private float startFlapDistance;
    public float spinachStateTime;
    public float flapDistance;
    public int smacked, powerUpsPicked, coinsPicked;

    public CaveMan(com.deeep.flycaveman.world.World world) {
        this.world = world;
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(startPosX, startPosY);
//        bodyDef.fixedRotation = true;
        bodyDef.bullet = true;
        shape = new CircleShape();
        shape.setRadius(size);
        fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 2.5f;
        fixtureDef.friction = .25f;
        fixtureDef.restitution = restitution;
        body = world.box2dWorld.createBody(bodyDef);
        sprite = new Sprite(Assets.cavemanTexture);
        sprite.setSize(size * 2, size * 2);
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
        body.setUserData(sprite);
        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);
        shape.dispose();
        //armBody.setAwake(false);
        body.setActive(true);
        //WeldJointDef weldJointDef = new WeldJointDef();
        //weldJointDef.initialize(body, world.catapult.armBody, new Vector2(11, 7));
        //weldJointDef.collideConnected = false;
        //bulletJoint = (WeldJoint) world.box2dWorld.createJoint(weldJointDef);
        strength = 225;
        spinachStateTime = 1;
        {/**Buyable Items*/
            if (wings >= 1) strength += 25;
            if (wings >= 2) strength += 25;
            if (wings == 3) strength += 25;
            if (steroids >= 1) strength += 25;
            if (steroids >= 2) strength += 25;
            if (steroids == 3) strength += 25;
            if (springs >= 1) springsJumps++;
            if (springs >= 2) springsJumps++;
            if (springs == 3) springsJumps++;
            stamina = 5.0f;
            if (staminaSize >= 1) stamina++;
            if (staminaSize >= 2) stamina++;
            if (staminaSize >= 3) stamina++;
            flapStateTime = 0.5f;
            startFlapDistance = 0;
            flapDistance = 0;
            smacked = 0;
            powerUpsPicked = 0;
            coinsPicked = 0;
        }
    }

    public void draw(Batch batch) {
        if (stateTimeSprings > 0) sprite.setRegion(Assets.cavemanSprings.getKeyFrame(stateTimeSprings));
        else if (wings > 0) sprite.setRegion(Assets.cavemanWings.getKeyFrame(flapStateTime));
        else if (flapStateTime < 0.5f) sprite.setRegion(Assets.cavemanFlap.getKeyFrame(flapStateTime));
        else if (GameInputProcessor.flying) sprite.setRegion(Assets.cavemanFly.getKeyFrame(stateTime));
        else sprite.setRegion(Assets.cavemanTexture);
        sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
        sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
        sprite.draw(batch);
    }

    public void update(float delta) {
        stateTime += delta;
        if (coinStreak > 0) {
            coinTimer += delta;
            if (coinTimer >= COIN_PICKUP_INTERVAL) {
                coinStreak = 0;
                coinTimer = 0;
            }
        }
        body.getPosition().set(Gdx.input.getX(), Gdx.input.getY());
        updateFlapping(delta);
        stateTimeSprings -= delta;
        if (stamina > 1) state = STATE_HAPPY;
        else if (stamina <= 1) state = STATE_TIRED;
        if (spinachStateTime < 1) state = STATE_PASSION;
        if (GameInputProcessor.touchingGround) state = STATE_PAIN;
        if (world.isGameOver()) state = STATE_KO;
        if (Gdx.input.isKeyJustPressed(Input.Keys.C)) cheats = !cheats;
    }

    public void updateFlapping(float delta) {
        if (GameInputProcessor.touchingGround) return;
        if (flapStateTime < 0.5f) {
            flapStateTime += delta;
            if (spinachStateTime < 1) {
                spinachStateTime += delta;
                double force = 300 * Math.sqrt(Math.max(0, 0.25 - Math.pow(0.5f - flapStateTime, 2)));
                body.applyForceToCenter(5 * (flapStateTime / 0.5f), (float) force, true);
            } else if (strength > 0 && !cheats) {
                strength -= delta * 20;
                double force = strength * Math.sqrt(Math.max(0, 0.25 - Math.pow(0.5f - flapStateTime, 2)));
                body.applyForceToCenter(5 * (flapStateTime / 0.5f), (float) force, true);
            }
        } else if (strength < 300) strength += delta;
        if (flapStateTime >= 0.5f && startFlapDistance != 0) {
            flapDistance += body.getPosition().x - startFlapDistance;
            startFlapDistance = 0;
        }
    }

    public void useSpring() {
        body.applyForce(500, 2500, body.getPosition().x, body.getPosition().y, true);
        Assets.boing.play();
        springsJumps--;
        stateTimeSprings = 1.5f;
    }

    public void flap() {
        if (GameInputProcessor.touchingGround) return;
        if (body.getLinearVelocity().x != 0 || body.getLinearVelocity().y != 0) {
            if (spinachStateTime < 1 || stamina > 0) {
                if (body.getLinearVelocity().y < 0)
                    body.setLinearVelocity(body.getLinearVelocity().x, body.getLinearVelocity().y / 2);
                else body.setLinearVelocity(body.getLinearVelocity().x, body.getLinearVelocity().y);
                if (!cheats && spinachStateTime >= 1) stamina -= 1;
                flapStateTime = 0;
                startFlapDistance = body.getPosition().x;
                //todo add a way to increase the max flapstatetime
            }
        }
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public boolean addWings(int price) {
        if (wings < 3) wings++;
        else return false;
        coins -= price;
        return true;
    }

    public boolean addStamina(int price) {
        if (staminaSize < 3) staminaSize++;
        else return false;
        coins -= price;
        return true;
    }

    public boolean addSteroids(int price) {
        if (steroids < 3) steroids++;
        else return false;
        coins -= price;
        return true;
    }

    public boolean addSprings(int price) {
        if (springs < 3) springs++;
        else return false;
        coins -= price;
        return true;
    }

    public boolean addMagnet(int price) {
        if (CaveMan.magnet < 3) CaveMan.magnet++;
        else return false;
        coins -= price;
        return true;
    }

    public boolean addClench(int price) {
        if (CaveMan.clench < 3) CaveMan.clench++;
        else return false;
        coins -= price;
        return true;
    }

    public void grabSpinach() {
        spinachStateTime = spinachTime;
    }
}