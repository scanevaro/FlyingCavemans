package com.deeep.flycaveman.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.deeep.flycaveman.Assets;

/**
 * Created by scanevaro on 29/10/2014.
 */
public class Catapult implements Entity {
    private BodyDef armBodyDef;
    public Body armBody;
    private FixtureDef armFixtureDef;
    private Fixture armFixture;
    private PolygonShape armShape;

    private BodyDef baseBodyDef;
    public Body baseBody;
    private FixtureDef baseFixtureDef;
    private Fixture baseFixture;
    private PolygonShape baseShape;

    public RevoluteJoint armJoint;
    private RevoluteJointDef armJointDef;

    private World world;
    private Ground ground;

    public Sprite baseSprite;
    public Sprite armSprite;

    private float sizeBaseX = 2.5f, sizeBaseY = 0.5f, sizeArmX = 0.8f, sizeArmY = 3f;

    public Catapult(World world, Ground ground) {
        this.world = world;
        this.ground = ground;

        createArm();
        createBase();
        createJoint();
    }

    private void createArm() {
        armBodyDef = new BodyDef();
        armBodyDef.type = BodyDef.BodyType.DynamicBody;
        armBodyDef.linearDamping = 1;
        armBodyDef.angularDamping = 1;
        armBodyDef.position.set(10, 4.2f);

        armShape = new PolygonShape();
        armShape.setAsBox(sizeArmX, sizeArmY);

        armFixtureDef = new FixtureDef();
        armFixtureDef.shape = armShape;
        armFixtureDef.density = 0.3F;

        armBody = world.createBody(armBodyDef);
        armSprite = new Sprite(Assets.catapultArmTexture);
        armSprite.setSize(sizeArmX * 2, sizeArmY * 2);
        armSprite.setOrigin(armSprite.getWidth() / 2, armSprite.getHeight() / 2);
        armBody.setUserData(armSprite);

        armFixture = armBody.createFixture(armFixtureDef);
        armFixture.setUserData(this);

        armShape.dispose();
    }

    private void createBase() {
        baseBodyDef = new BodyDef();
        baseBodyDef.type = BodyDef.BodyType.StaticBody;
        baseBodyDef.linearDamping = 1;
        baseBodyDef.angularDamping = 1;
        baseBodyDef.position.set(10.5f, 1.5f);

        baseShape = new PolygonShape();
        baseShape.setAsBox(sizeBaseX, sizeBaseY);

        baseFixtureDef = new FixtureDef();
        baseFixtureDef.shape = baseShape;
        baseFixtureDef.density = 0.3F;
        baseFixtureDef.isSensor = true;

        baseBody = world.createBody(baseBodyDef);
        baseSprite = new Sprite(new TextureRegion(Assets.catapultBaseTexture));
        baseSprite.setSize(sizeBaseX * 2, sizeBaseY * 2);
        baseSprite.setOrigin(baseSprite.getWidth() / 2, baseSprite.getHeight() / 2);
        baseBody.setUserData(baseSprite);

        baseFixture = baseBody.createFixture(baseFixtureDef);
        baseFixture.setUserData(this);

        baseShape.dispose();
    }

    private void createJoint() {
        armJointDef = new RevoluteJointDef();
        armJointDef.initialize(ground.body, armBody, new Vector2(10, 1));
        armJointDef.enableMotor = true;
        armJointDef.enableLimit = true;
        armJointDef.motorSpeed = -10/*-1260*/;
        armJointDef.lowerAngle = (float) Math.toRadians(9);
        armJointDef.upperAngle = (float) Math.toRadians(75);
        armJointDef.maxMotorTorque = 3500;

        armJoint = (RevoluteJoint) world.createJoint(armJointDef);
    }

    @Override
    public void draw(Batch batch) {
        armSprite.setPosition(armBody.getPosition().x - armSprite.getWidth() / 2, armBody.getPosition().y - armSprite.getHeight() / 2);
        armSprite.setRotation(armBody.getAngle() * MathUtils.radiansToDegrees);
        armSprite.draw(batch);

        baseSprite.setPosition(baseBody.getPosition().x - baseSprite.getWidth() / 2, baseBody.getPosition().y - baseSprite.getHeight() / 2);
        baseSprite.setRotation(baseBody.getAngle() * MathUtils.radiansToDegrees);
        baseSprite.draw(batch);
    }

    @Override
    public void update(float delta) {
    }
}