package com.deeep.flycaveman.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.WeldJoint;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.deeep.flycaveman.classes.Assets;
import com.deeep.flycaveman.input.GameInputProcessor;

/**
 * Created by scanevaro on 11/10/2014.
 */
public class CaveMan implements Entity {
    public final float startPosX = 11.1f;
    public final float startPosY = 6.5f;
    private final float restitution = 0.1f;

    private BodyDef bodyDef;
    public Body body;
    private FixtureDef fixtureDef;
    public Fixture fixture;
    private CircleShape shape;
    //    private PolygonShape shape;
    public WeldJoint bulletJoint;
    public float size = .6f;
    public Sprite sprite;

    public float stamina;
    public static final float maxStamina = 5.0f;
    public float strength;

    public static boolean wingsPowerup;
    private float stateTime;
    public static boolean upgradeStamina;
    public static boolean addSteroids;
    public static boolean addShield;
    private int shields;
    public static boolean addSprings;
    public int springs;
    public float stateTimeSprings;
    public boolean flapping;
    public static float flapStatetime;

    public CaveMan(com.deeep.flycaveman.classes.World world) {
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

        if (!wingsPowerup) sprite = new Sprite(Assets.cavemanTexture);
        else sprite = new Sprite(Assets.cavemanWings.getKeyFrame(0));

        sprite.setSize(size * 2, size * 2);
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
        body.setUserData(sprite);

        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);

        shape.dispose();

//        armBody.setAwake(false);

        body.setActive(true);

        WeldJointDef weldJointDef = new WeldJointDef();
        weldJointDef.initialize(body, world.catapult.armBody, new Vector2(11, 7));
        weldJointDef.collideConnected = false;

        bulletJoint = (WeldJoint) world.box2dWorld.createJoint(weldJointDef);

        strength = 75;

        if (wingsPowerup) strength += 25;
        if (addSteroids) strength += 25;
        if (addShield) shields++;
        if (addSprings) springs++;
        stamina = 5.0f;
    }

    public void draw(Batch batch) {
        if (stateTimeSprings > 0)
            sprite.setRegion(Assets.cavemanSprings);
        else if (wingsPowerup)
            sprite.setRegion(Assets.cavemanWings.getKeyFrame(stateTime));
        else sprite.setRegion(Assets.cavemanTexture);

        sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
        sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
        sprite.draw(batch);

        if (shields > 0) batch.draw(Assets.shield, sprite.getX(), sprite.getY(), size * 2, size * 2);
    }

    public void update(float delta) {
        if (flapping)
            updateFlapping(delta);

        if (upgradeStamina && stamina < 0.5f)
            stamina += delta / 25;

        stateTimeSprings -= delta;
    }

    public void updateFlapping(float delta) {
        if (GameInputProcessor.touchingGround) return;

        if (/*flapStatetime == 0 && */stamina > 0) {
            /*flapStatetime -= delta;*/
            stamina -= /*1*/delta;

//            if (body.getLinearVelocity().y < 0)
//                body.setLinearVelocity(body.getLinearVelocity().x, 5 + Math.abs(body.getLinearVelocity().y / 2));
//            else body.setLinearVelocity(body.getLinearVelocity().x, 5 + body.getLinearVelocity().y);
            body.applyForceToCenter(20, 200, true);

            if (wingsPowerup) {
                stateTime += delta;
                sprite.setRegion(Assets.cavemanWings.getKeyFrame(stateTime));
            }

//            if (flapStatetime > 0.8f)
//                flapStatetime = 0;
        }
    }

    public void addWings() {
        CaveMan.wingsPowerup = true;
        strength += 25;
    }

    public void upgradeStamina() {
        CaveMan.upgradeStamina = true;
    }

    public void addSteroids() {
        CaveMan.addSteroids = true;

        strength += 25;
    }

    public void addShield() {
        CaveMan.addShield = true;

        shields++;
    }

    public void addSprings() {
        CaveMan.addSprings = true;

        springs++;
    }

    public void useSpring() {
        body.applyForce(500, 2500, body.getPosition().x, body.getPosition().y, true);
        Assets.boing.play();
        springs--;

        stateTimeSprings = 1.5f;
    }

    public void drop() {
        if (GameInputProcessor.touchingGround) return;

        body.applyForceToCenter(0, -10000, true);
    }

    public void hit() {
        if (shields > 0) shields--;
    }
}