package com.deeep.flycaveman.entities;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.deeep.flycaveman.Core;

/**
 * Created by scanevaro on 11/10/2014.
 */
public class Ground {
    private BodyDef bodyDef;
    public Body body;
    private FixtureDef fixtureDef;
    private Fixture fixture;
    private ChainShape shape;

    public Ground(World world) {
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(0, 1);

        shape = new ChainShape();
        shape.createChain(new Vector2[]{new Vector2(0, 0), new Vector2(Core.BOX2D_VIRTUAL_WIDTH, 0)});

        fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.friction = .1f;
        fixtureDef.restitution = 0f;

        body = world.createBody(bodyDef);
        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);

        shape.dispose();
    }
}
