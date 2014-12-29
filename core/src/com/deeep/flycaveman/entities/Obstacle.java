package com.deeep.flycaveman.entities;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;
import com.deeep.flycaveman.classes.Assets;
import net.dermetfan.utils.libgdx.graphics.Box2DSprite;

import java.util.Random;

/**
 * Created by scanevaro on 14/10/2014.
 */
public class Obstacle {
    public static enum Type {
        SMALL_EGG, BRACHIOSAURUS, QUETZALCOATLUS
    }

    private BodyDef bodyDef;
    public Body body;
    private FixtureDef fixtureDef;
    private Fixture fixture;
    private PolygonShape shape;
    public int type;

    public Obstacle(World world, float positionX, Random random) {
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
            shape.setAsBox(0.8f, 0.8f);
        } else if (type == Type.BRACHIOSAURUS.ordinal()) {
            bodyDef.position.set(positionX, 4f);
            shape.setAsBox(4, 3);
        } else if (type == Type.QUETZALCOATLUS.ordinal()) {
            bodyDef.position.set(positionX, random.nextInt(40 + random.nextInt(15)));
            shape.setAsBox(2, 2);
        }

        fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;

        body = world.createBody(bodyDef);
        if (type == Type.SMALL_EGG.ordinal())
            body.setUserData(new Box2DSprite(new TextureRegion(Assets.getAssets().getSmallEggTexture())));
        else if (type == Type.BRACHIOSAURUS.ordinal())
            body.setUserData(new Box2DSprite(new TextureRegion(Assets.getAssets().getBrachioTexture())));
        else if (type == Type.QUETZALCOATLUS.ordinal())
            body.setUserData(new Box2DSprite(new TextureRegion(Assets.getAssets().getQuetzaTexture())));

        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);

        shape.dispose();

        body.setAwake(false);
    }
}