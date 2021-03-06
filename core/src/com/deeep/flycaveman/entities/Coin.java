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
    private final float radius = 0.65f;
    private final float INTERVAL = 0.07F;
    public boolean isMagnetic = false;
    private float tick;
    private Sprite[] sprites;
    private Sprite sprite;
    private CircleShape shape;
    private FixtureDef fixtureDef;
    private World world;
    private boolean alive;
    private double magVel;

    public Coin(float x, float y, World world) {
        this.world = world;
        sprites = new Sprite[]{new Sprite(Assets.coin1), new Sprite(Assets.coin2), new Sprite(Assets.coin3), new Sprite(Assets.coin4), new Sprite(Assets.coin5), new Sprite(Assets.coin6)};
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(x, y);
        shape = new CircleShape();
        shape.setRadius(radius);
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
        alive = true;
    }

    public void update(float delta) {
        if (distanceToCaveman() > 20) isMagnetic = false;
        else isMagnetic = true;
        isMagnetic = true;
        if (isMagnetic) {
            magVel += 1;
            if (isAheadOfCaveman()) x -= magVel;
            else x += magVel;
            if (isAboveCaveman()) y -= magVel;
            else y += magVel;
        } else magVel = 0;
        tick += delta;
    }

    public void draw(Batch b) {
        if (!isMagnetic) return;
        getCurrentSprite();
        sprite.setPosition(body.getPosition().x - sprites[1].getWidth() / 2, body.getPosition().y - sprites[1].getHeight() / 2);
        sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
        if (alive) sprite.draw(b);
    }

    public void getCurrentSprite() {
        if (tick < INTERVAL * 1) sprite = sprites[0];
        else if (tick < INTERVAL * 2) sprite = sprites[1];
        else if (tick < INTERVAL * 3) sprite = sprites[2];
        else if (tick < INTERVAL * 4) sprite = sprites[3];
        else if (tick < INTERVAL * 5) sprite = sprites[4];
        else if (tick < INTERVAL * 6 - 1) sprite = sprites[5];
        else {
            tick = 0;
            sprite = sprites[5];
        }
    }

    private boolean isAheadOfCaveman() {
        return world.caveman.body.getPosition().x <= this.x;
    }

    private boolean isAboveCaveman() {
        return world.caveman.body.getPosition().y <= this.y;
    }

    private double distanceToCaveman() {
        return Math.sqrt((world.caveman.body.getPosition().x - this.x) * (world.caveman.body.getPosition().x - this.x) +
                (world.caveman.body.getPosition().y - this.y) * (world.caveman.body.getPosition().y - this.y));
    }

    public void die() {
        alive = false;
    }

    public Coin setAlive(float x, float y) {
        alive = true;
        body.setTransform(x, y, body.getAngle());
        return this;
    }

    public boolean isAlive() {
        return alive;
    }
}