package com.deeep.flycaveman.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.*;
import com.deeep.flycaveman.Assets;
import com.deeep.flycaveman.world.World;

import java.util.Random;

/**
 * Created by scanevaro on 14/10/2014.
 */
public class Obstacle implements Entity {
    public static enum Type {
        SMALL_EGG, BRACHIOSAURUS, QUETZALCOATLUS, ARGENTAVIS, TOUCAN
    }

    private boolean dead = false;
    private BodyDef bodyDef;
    public Body body;
    private FixtureDef fixtureDef;
    private Fixture fixture;
    private PolygonShape shape;
    public int type;
    public float smallEggSize = 0.8f, quetzaSizeX = 2, quetzaSizeY = 2, brachioSizeX = 4, brachioSizeY = 3;
    private Sprite sprite;

    public Obstacle(World world, float positionX, Random random) {
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        shape = new PolygonShape();
        positionX += random.nextInt(100);
        float typeRand = random.nextFloat();
        if (typeRand <= 0.95f && typeRand > 0.45f) {
            if (random.nextFloat() <= 0.75f) type = Type.QUETZALCOATLUS.ordinal();
            else type = Type.ARGENTAVIS.ordinal();
        } else if (typeRand >= 0.95f) type = Type.BRACHIOSAURUS.ordinal();
        else if (typeRand > 0.04f) type = Type.SMALL_EGG.ordinal();
        else type = Type.TOUCAN.ordinal();
        if (type == Type.SMALL_EGG.ordinal()) {
            bodyDef.position.set(positionX, 1.8f);
            shape.setAsBox(smallEggSize, smallEggSize);
        } else if (type == Type.BRACHIOSAURUS.ordinal()) {
            bodyDef.position.set(positionX, 4f);
            shape.setAsBox(brachioSizeX, brachioSizeY);
        } else if (type == Type.QUETZALCOATLUS.ordinal()) {
            bodyDef.position.set(positionX, Math.max(5, world.caveman.body.getPosition().y - 20 + random.nextFloat() * 60));
            shape.setAsBox(quetzaSizeX, quetzaSizeY);
        } else if (type == Type.ARGENTAVIS.ordinal()) {
            bodyDef.position.set(positionX, Math.max(5, world.caveman.body.getPosition().y - 20 + random.nextFloat() * 60));
            shape.setAsBox(quetzaSizeX + 0.5f, quetzaSizeY + 0.5f);
        } else if (type == Type.TOUCAN.ordinal()) {
            bodyDef.position.set(positionX, Math.max(5, world.caveman.body.getPosition().y - 20 + random.nextFloat() * 60));
            shape.setAsBox(smallEggSize, smallEggSize);
        }
        fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;
        body = world.box2dWorld.createBody(bodyDef);
        if (type == Type.SMALL_EGG.ordinal()) {
            sprite = new Sprite(new TextureRegion(Assets.smallEggTexture));
            sprite.setSize(smallEggSize * 2, smallEggSize * 2);
            body.setUserData(sprite);
        } else if (type == Type.BRACHIOSAURUS.ordinal()) {
            sprite = new Sprite(new TextureRegion(Assets.brachioTexture));
            sprite.setSize(brachioSizeX * 2, brachioSizeY * 2);
            body.setUserData(sprite);
        } else if (type == Type.QUETZALCOATLUS.ordinal()) {
            sprite = new Sprite(new TextureRegion(Assets.quetzaTexture));
            sprite.setSize(quetzaSizeX * 2, quetzaSizeY * 2);
            body.setUserData(sprite);
        } else if (type == Type.ARGENTAVIS.ordinal()) {
            sprite = new Sprite(new TextureRegion(Assets.argenTexture));
            sprite.setSize((quetzaSizeX + 0.5f) * 2, (quetzaSizeY + 0.5f) * 2);
            body.setUserData(sprite);
        } else if (type == Type.TOUCAN.ordinal()) {
            sprite = new Sprite(new TextureRegion(Assets.toucanTexture));
            sprite.setSize(smallEggSize * 2, smallEggSize * 2);
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
        sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
        sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
        sprite.draw(batch);
    }

    public void die() {
        dead = true;
    }

    public boolean isDead() {
        return dead;
    }

    public void hit() {
        if (type == Type.SMALL_EGG.ordinal()) sprite.setRegion(Assets.smallEggBroken);
        else if (type == Type.BRACHIOSAURUS.ordinal()) sprite.setRegion(Assets.brachioMidPull);
        else if (type == Type.QUETZALCOATLUS.ordinal()) sprite.setRegion(Assets.quetzaHit);
        else if (type == Type.ARGENTAVIS.ordinal()) sprite.setRegion(Assets.argenHit);
        else if (type == Type.TOUCAN.ordinal()) sprite.setRegion(Assets.toucanHit);
    }
}