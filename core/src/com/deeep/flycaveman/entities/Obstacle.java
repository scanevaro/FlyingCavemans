package com.deeep.flycaveman.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.deeep.flycaveman.classes.Assets;

import java.util.Random;

/**
 * Created by scanevaro on 14/10/2014.
 */
public class Obstacle implements Entity {

    public static enum Type {
        SMALL_EGG, BRACHIOSAURUS, QUETZALCOATLUS
    }

    private BodyDef bodyDef;
    public Body body;
    private FixtureDef fixtureDef;
    private Fixture fixture;
    private PolygonShape shape;
    public int type;
    public float smallEggSize = 0.8f, quetzaSizeX = 2, quetzaSizeY = 2, brachioSizeX = 4, brachioSizeY = 3;

    private Sprite sprite;
    private Array<Body> bodys;

    public Obstacle(World world, float positionX, Random random) {
        bodys = new Array<Body>();

        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;

        shape = new PolygonShape();

        positionX += random.nextInt(100);

        float typeRand = random.nextFloat();

        if (typeRand <= 0.95f && typeRand > 0.45f)
            type = Type.QUETZALCOATLUS.ordinal();
        else if (typeRand >= 0.95f)
            type = Type.BRACHIOSAURUS.ordinal();
        else
            type = Type.SMALL_EGG.ordinal();

        if (type == Type.SMALL_EGG.ordinal()) {
            bodyDef.position.set(positionX, 1.8f);
            shape.setAsBox(smallEggSize, smallEggSize);
        } else if (type == Type.BRACHIOSAURUS.ordinal()) {
            bodyDef.position.set(positionX, 4f);
            shape.setAsBox(brachioSizeX, brachioSizeY);
        } else if (type == Type.QUETZALCOATLUS.ordinal()) {
            bodyDef.position.set(positionX, random.nextInt(40 + random.nextInt(15)));
            shape.setAsBox(quetzaSizeX, quetzaSizeY);
        }

        fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;

        bodys.add(body = world.createBody(bodyDef));

        if (type == Type.SMALL_EGG.ordinal()) {
            sprite = new Sprite(new TextureRegion(Assets.getAssets().getSmallEggTexture()));
            sprite.setSize(smallEggSize * 2, smallEggSize * 2);
            body.setUserData(sprite);
        } else if (type == Type.BRACHIOSAURUS.ordinal()) {
            sprite = new Sprite(new TextureRegion(Assets.getAssets().getBrachioTexture()));
            sprite.setSize(brachioSizeX * 2, brachioSizeY * 2);
            body.setUserData(sprite);
        } else if (type == Type.QUETZALCOATLUS.ordinal()) {
            sprite = new Sprite(new TextureRegion(Assets.getAssets().getQuetzaTexture()));
            sprite.setSize(quetzaSizeX * 2, quetzaSizeY * 2);
            body.setUserData(sprite);
        }

        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);

        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);

        shape.dispose();

        body.setAwake(false);
    }

    @Override
    public void draw(Batch batch) {
        for (Body body : bodys) {
            sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
            sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
            sprite.draw(batch);
        }
    }
}