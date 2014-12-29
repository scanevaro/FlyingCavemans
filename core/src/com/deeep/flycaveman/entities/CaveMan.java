package com.deeep.flycaveman.entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.WeldJoint;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.deeep.flycaveman.Core;
import com.deeep.flycaveman.classes.Assets;
import net.dermetfan.utils.libgdx.graphics.Box2DSprite;

/**
 * Created by scanevaro on 11/10/2014.
 */
public class CaveMan {
    private BodyDef bodyDef;
    public Body body;
    private FixtureDef fixtureDef;
    public Fixture fixture;
    private CircleShape shape;
    //    private PolygonShape shape;
    public WeldJoint bulletJoint;

    public CaveMan(com.deeep.flycaveman.classes.World world) {
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(11f, Core.BOX2D_VIRTUAL_HEIGHT / 3 + .3f);
//        bodyDef.fixedRotation = true;
        bodyDef.bullet = true;

        shape = new CircleShape();
        shape.setRadius(.6f);

        fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 2.5f;
        fixtureDef.friction = .25f;
        fixtureDef.restitution = .5f;

        body = world.box2dWorld.createBody(bodyDef);
        body.setUserData(new Box2DSprite(new TextureRegion(Assets.getAssets().getCavemanTexture())));

        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);

        shape.dispose();

//        armBody.setAwake(false);

        body.setActive(true);

        WeldJointDef weldJointDef = new WeldJointDef();
        weldJointDef.initialize(body, world.catapult.armBody, new Vector2(11, Core.BOX2D_VIRTUAL_HEIGHT / 3 + .3f));
        weldJointDef.collideConnected = false;

        bulletJoint = (WeldJoint) world.box2dWorld.createJoint(weldJointDef);
    }
}