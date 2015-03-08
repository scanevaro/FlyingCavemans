package com.deeep.flycaveman.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.*;
import com.deeep.flycaveman.Assets;
import com.deeep.flycaveman.world.World;

/**
 * Created by Andreas on 2/11/2015.
 */
public class Coin {

    public float x, y;
    public BodyDef bodyDef;
    public Body body;
    public Fixture fixture;

    private float tick;
    private Sprite[] sprites;
    private CircleShape shape;
    private FixtureDef fixtureDef;

    private final float INTERVAL = 0.07F;

    public Coin(float x, float y, World world) {
        sprites = new Sprite[]{new Sprite(Assets.coin1), new Sprite(Assets.coin2), new Sprite(Assets.coin3), new Sprite(Assets.coin4), new Sprite(Assets.coin5), new Sprite(Assets.coin6)};
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x, y);

        shape = new CircleShape();
        shape.setRadius(1);

        fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 2.5f;
        fixtureDef.friction = .25f;
        fixtureDef.restitution = 0.5F;
        fixtureDef.isSensor = true;

        body = world.box2dWorld.createBody(bodyDef);
        for (Sprite s : sprites) {
            s.setSize(1.5F, 1.5F);
            s.setOrigin(s.getWidth() / 2, s.getHeight() / 2);
        }
        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);

        shape.dispose();

        body.setAwake(false);
    }

    public void update(float delta) {
        tick += delta;
    }

    public void draw(Batch b) {
        getCurrentSprite().setPosition(body.getPosition().x - sprites[1].getWidth() / 2, body.getPosition().y - sprites[1].getHeight() / 2);
        getCurrentSprite().setRotation(body.getAngle() * MathUtils.radiansToDegrees);
        getCurrentSprite().draw(b);
    }

    public Sprite getCurrentSprite() {
        //System.out.println(tick);
        if (tick < INTERVAL * 1) return sprites[0];
        if (tick < INTERVAL * 2) return sprites[1];
        if (tick < INTERVAL * 3) return sprites[2];
        if (tick < INTERVAL * 4) return sprites[3];
        if (tick < INTERVAL * 5) return sprites[4];
        if (tick < INTERVAL * 6 - 1) return sprites[5];
        else {
            tick = 0;
            return sprites[5];
        }
    }
}
