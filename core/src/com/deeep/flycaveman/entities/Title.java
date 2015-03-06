package com.deeep.flycaveman.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.*;
import com.deeep.flycaveman.Assets;

/**
 * Created by scanevaro on 06/03/2015.
 */
public class Title implements Entity {
    private float sizeX = 8, sizeY = 4.0f;

    private BodyDef bodyDef;
    public Body body;
    private FixtureDef fixtureDef;
    public Fixture fixture;
    private PolygonShape shape;
    public Sprite sprite;

    public Title(com.deeep.flycaveman.world.World world) {

        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(20, 9);

        shape = new PolygonShape();
        shape.setAsBox(sizeX, sizeY);

        fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;

        body = world.box2dWorld.createBody(bodyDef);
        body.setAwake(false);

        sprite = new Sprite(Assets.title);
        sprite.setSize(sizeX * 2, sizeY * 2);
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);

        fixture = body.createFixture(fixtureDef);

        shape.dispose();
    }

    @Override
    public void draw(Batch batch) {
        sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
        sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
        sprite.draw(batch);
    }
}