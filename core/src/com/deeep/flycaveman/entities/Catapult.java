package com.deeep.flycaveman.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.deeep.flycaveman.classes.Assets;

/**
 * Created by scanevaro on 29/10/2014.
 */
public class Catapult extends Entity{
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

    private float sizeBaseX = 2.5f, sizeBaseY = 0.5f, sizeArmX = 0.8f, sizeArmY = 3f;

    public Catapult(World world, Ground ground) {
        this.world = world;
        this.ground = ground;

        createArm();
        createBase();
        createJoint();
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
        Sprite sprite = new Sprite(new TextureRegion(Assets.getAssets().getCatapultBaseTexture()));
        sprite.setSize(sizeBaseX, sizeBaseY);
        baseBody.setUserData(sprite);

        baseFixture = baseBody.createFixture(baseFixtureDef);
        baseFixture.setUserData(this);

        baseShape.dispose();
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
        Sprite sprite = new Sprite(new TextureRegion(Assets.getAssets().getCatapultArmTexture()));
        sprite.setSize(sizeArmX, sizeArmY);
        armBody.setUserData(sprite);

        armFixture = armBody.createFixture(armFixtureDef);
        armFixture.setUserData(this);

        armShape.dispose();
    }

    private void createJoint() {
        armJointDef = new RevoluteJointDef();
        armJointDef.initialize(ground.body, armBody, new Vector2(10, 1));
        armJointDef.enableMotor = true;
        armJointDef.enableLimit = true;
        armJointDef.motorSpeed = -10/*-1260*/;
        armJointDef.lowerAngle = (float) Math.toRadians(9);
        armJointDef.upperAngle = (float) Math.toRadians(75);
        armJointDef.maxMotorTorque = 9999;

        armJoint = (RevoluteJoint) world.createJoint(armJointDef);
    }
}