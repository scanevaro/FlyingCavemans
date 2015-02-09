package com.deeep.flycaveman.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.WeldJoint;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.badlogic.gdx.utils.Array;
import com.deeep.flycaveman.classes.Assets;

/**
 * Created by scanevaro on 11/10/2014.
 */
public class CaveMan implements Entity {
    private BodyDef bodyDef;
    public Body body;
    private FixtureDef fixtureDef;
    public Fixture fixture;
    private CircleShape shape;
    //    private PolygonShape shape;
    public WeldJoint bulletJoint;
    public float size = .6f;
    private Sprite sprite;
    private Array<Body> bodys;

    public float stamina;
    public float strength;

    public CaveMan(com.deeep.flycaveman.classes.World world) {
        bodys = new Array<Body>();

        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(11.1f, 6.5f);
//        bodyDef.fixedRotation = true;
        bodyDef.bullet = true;

        shape = new CircleShape();
        shape.setRadius(size);

        fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 2.5f;
        fixtureDef.friction = .25f;
        fixtureDef.restitution = .5f;

        body = world.box2dWorld.createBody(bodyDef);
        bodys.add(body);
        sprite = new Sprite(new TextureRegion(Assets.cavemanTexture));
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

        strength = 50;
        stamina = 0.5f;
    }

    public void draw(Batch batch) {
        for (Body body : bodys) {
            sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
            sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
            sprite.draw(batch);
        }
    }

    public void updateFlapping(float delta) {
        if (stamina > 0) {
            stamina -= delta;
            body.applyForce(strength, strength, body.getPosition().x, body.getPosition().y, true);
        }
    }
}