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
        SMALL_EGG, BRACHIOSAURUS, QUETZALCOATLUS, ARGENTAVIS, TOUCAN, SABRETOOTH
    }

    private boolean dead = false;
    private BodyDef bodyDef;
    public Body body;
    private FixtureDef fixtureDef;
    private Fixture fixture;
    private PolygonShape shape;
    public int type;
    private final float smallEggSizeX = 0.5f, smallEggSizeY = 0.5f, quetzaSizeX = 2, quetzaSizeY = 2, brachioSizeX = 4,
            brachioSizeY = 3, sabretoothSize = 0.5f;
    private float realSizeX, realSizeY, textureSizeX, textureSizeY;
    private Sprite sprite;
    private boolean hit;
    private float stateTime;
    private Body tempBody;

    public Obstacle(World world, float positionX, Random random) {
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        shape = new PolygonShape();
        positionX += random.nextInt(100);
        setType(random);
        setPosition(positionX, random, world);
        fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;
        if (type == Type.BRACHIOSAURUS.ordinal()) {
            BodyDef bodyDef2 = new BodyDef();
            bodyDef2.type = BodyDef.BodyType.DynamicBody;
            PolygonShape shape2 = new PolygonShape();
            bodyDef2.position.set(positionX - 2.65f, 2.5f);
            shape2.setAsBox(brachioSizeX / 3, brachioSizeY / 2 + 1);
            FixtureDef fixtureDef2 = new FixtureDef();
            fixtureDef2.shape = shape2;
            fixtureDef2.isSensor = true;
            Body body2 = world.box2dWorld.createBody(bodyDef2);
            body2.setUserData("BodyHead");
            Fixture fixture2 = body2.createFixture(fixtureDef2);
            fixture2.setUserData(this);
            shape2.dispose();
            body2.setAwake(false);

            BodyDef bodyDef3 = new BodyDef();
            bodyDef3.type = BodyDef.BodyType.DynamicBody;
            PolygonShape shape3 = new PolygonShape();
            bodyDef3.position.set(positionX + 2.6f, 2.5f);
            shape3.setAsBox(brachioSizeX / 3, brachioSizeY / 2 + 1);
            FixtureDef fixtureDef3 = new FixtureDef();
            fixtureDef3.shape = shape3;
            fixtureDef3.isSensor = true;
            Body body3 = world.box2dWorld.createBody(bodyDef3);
            body3.setUserData("BodyTail");
            Fixture fixture3 = body3.createFixture(fixtureDef3);
            fixture3.setUserData(this);
            shape3.dispose();
            body3.setAwake(false);
        }
        body = world.box2dWorld.createBody(bodyDef);
        if (type == Type.SMALL_EGG.ordinal()) {
            sprite = new Sprite(new TextureRegion(Assets.smallEggTexture));
            sprite.setSize(smallEggSizeX * 2, smallEggSizeY * 2);
            realSizeX = smallEggSizeX;
            realSizeY = smallEggSizeY;
        } else if (type == Type.BRACHIOSAURUS.ordinal()) {
            sprite = new Sprite(new TextureRegion(Assets.brachioTexture));
            sprite.setSize(brachioSizeX * 2, brachioSizeY * 2);
            realSizeX = brachioSizeX;
            realSizeY = brachioSizeY;
        } else if (type == Type.QUETZALCOATLUS.ordinal()) {
            sprite = new Sprite(new TextureRegion(Assets.quetzaTexture.getKeyFrame(0)));
            sprite.setSize(quetzaSizeX * 2, quetzaSizeY * 2);
            realSizeX = quetzaSizeX;
            realSizeY = quetzaSizeY;
        } else if (type == Type.ARGENTAVIS.ordinal()) {
            sprite = new Sprite(new TextureRegion(Assets.argenTexture));
            sprite.setSize((quetzaSizeX + 0.5f) * 2, (quetzaSizeY + 0.5f) * 2);
            realSizeX = quetzaSizeX + 0.5f;
            realSizeY = quetzaSizeY + 0.5f;
        } else if (type == Type.TOUCAN.ordinal()) {
            sprite = new Sprite(new TextureRegion(Assets.toucanTexture));
            sprite.setSize(smallEggSizeX * 2, smallEggSizeX * 2);
            realSizeX = smallEggSizeX;
            realSizeY = smallEggSizeX;
        } else if (type == Type.SABRETOOTH.ordinal()) {
            sprite = new Sprite(new TextureRegion(Assets.sabertoothIdle.getKeyFrame(stateTime)));
            sprite.setSize(sabretoothSize * 2, sabretoothSize * 2);
            realSizeX = sabretoothSize;
            realSizeY = sabretoothSize;
        }
        textureSizeX = sprite.getRegionWidth();
        textureSizeY = sprite.getRegionHeight();
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);
        shape.dispose();
        body.setAwake(false);
    }

    @Override
    public void draw(Batch batch) {
        if (type == Type.SMALL_EGG.ordinal()) {
            if (!hit) sprite.setRegion(Assets.smallEggTexture);
            else sprite.setRegion(Assets.smallEggBroken);
        } else if (type == Type.BRACHIOSAURUS.ordinal()) {
            if (!hit) sprite.setRegion(Assets.brachioTexture);
            else if (tempBody != null) {
                String tempString = (String) tempBody.getUserData();
                if (tempString != null && tempString.equals("BodyHead")) sprite.setRegion(Assets.brachioFrontPull);
                else if (tempString != null && tempString.equals("BodyTail")) sprite.setRegion(Assets.brachioBackPull);
                else sprite.setRegion(Assets.brachioMidPull);
            }
        } else if (type == Type.QUETZALCOATLUS.ordinal()) {
            if (!hit) sprite.setRegion(Assets.quetzaTexture.getKeyFrame(stateTime));
            else sprite.setRegion(Assets.quetzaHit.getKeyFrame(stateTime));
        } else if (type == Type.ARGENTAVIS.ordinal()) {
            if (!hit) sprite.setRegion(Assets.argenTexture);
            else sprite.setRegion(Assets.argenHit);
        } else if (type == Type.TOUCAN.ordinal()) {
            if (!hit) sprite.setRegion(Assets.toucanTexture);
            else sprite.setRegion(Assets.toucanHit);
        } else if (type == Type.SABRETOOTH.ordinal()) {
            if (!hit) sprite.setRegion(Assets.sabertoothIdle.getKeyFrame(stateTime));
            else sprite.setRegion(Assets.sabertoothHit.getKeyFrame(stateTime));
        }
        {/**Sprite realSizeX*/
            float sizeX = sprite.getRegionWidth() * (realSizeX * 2) / textureSizeX;
            float sizeY = sprite.getRegionHeight() * (realSizeY * 2) / textureSizeY;
            sprite.setSize(sizeX, sizeY);
            sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
        }
        {/**Sprite Position*/
            if (type == Type.SMALL_EGG.ordinal())
                sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2,
                        body.getPosition().y - sprite.getHeight() / 2);
            else if (type == Type.BRACHIOSAURUS.ordinal())
                sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2,
                        body.getPosition().y - sprite.getHeight() / 2);
            else if (type == Type.QUETZALCOATLUS.ordinal())
                sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2 + 0.5f,
                        body.getPosition().y - sprite.getHeight() / 2.5f);
            else if (type == Type.ARGENTAVIS.ordinal())
                sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2,
                        body.getPosition().y - sprite.getHeight() / 5);
            else if (type == Type.TOUCAN.ordinal())
                sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2,
                        body.getPosition().y - sprite.getHeight() / 2);
            else if (type == Type.SABRETOOTH.ordinal())
                sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2,
                        body.getPosition().y - sprite.getHeight() / 2);
        }
        sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
        sprite.draw(batch);
    }

    @Override
    public void update(float delta) {
        stateTime += delta;
        if (!hit && type == Type.QUETZALCOATLUS.ordinal()
                || type == Type.ARGENTAVIS.ordinal()
                || type == Type.TOUCAN.ordinal()) {
            body.setTransform(body.getPosition().x - (delta * 6), body.getPosition().y, body.getAngle());
        } else if (hit && type == Type.QUETZALCOATLUS.ordinal()
                || type == Type.ARGENTAVIS.ordinal()
                || type == Type.TOUCAN.ordinal()) {
            body.setTransform(body.getPosition().x, body.getPosition().y - (delta * 10), body.getAngle());
        }
        if (hit) body.setActive(false);
    }

    public void die() {
        dead = true;
    }

    public boolean isDead() {
        return dead;
    }

    public void hit() {
        hit = true;
    }

    public void hit(Body body) {
        this.tempBody = body;
        hit = true;
    }

    private void setType(Random random) {
        float typeRand = random.nextFloat();
        if (typeRand <= 0.96f && typeRand > 0.5f) {
            if (random.nextFloat() <= 0.75f) type = Type.QUETZALCOATLUS.ordinal();
            else type = Type.ARGENTAVIS.ordinal();
        } else if (typeRand >= 0.96f) type = Type.BRACHIOSAURUS.ordinal();
        else if (typeRand <= 0.5f && typeRand > 0.25f) type = Type.SABRETOOTH.ordinal();
        else if (typeRand <= 0.25f && typeRand > 0.04f) type = Type.SMALL_EGG.ordinal();
        else type = Type.TOUCAN.ordinal();
    }

    private void setPosition(float positionX, Random random, World world) {
        if (type == Type.SMALL_EGG.ordinal()) {
            bodyDef.position.set(positionX, 1.65f);
            shape.setAsBox(smallEggSizeX / 2, smallEggSizeY - 0.1f);
        } else if (type == Type.BRACHIOSAURUS.ordinal()) {
            bodyDef.position.set(positionX, 4f);
            shape.setAsBox(brachioSizeX / 3, brachioSizeY - 0.5f);
        } else if (type == Type.QUETZALCOATLUS.ordinal()) {
            bodyDef.position.set(positionX, Math.max(5,
                    world.caveman.body.getPosition().y - 20 + random.nextFloat() * 60));
            shape.setAsBox(quetzaSizeX / 2 + 0.3f, quetzaSizeY / 2 - 0.6f);
        } else if (type == Type.ARGENTAVIS.ordinal()) {
            bodyDef.position.set(positionX, Math.max(5,
                    world.caveman.body.getPosition().y - 20 + random.nextFloat() * 60));
            shape.setAsBox(quetzaSizeX / 2 + 0.5f, quetzaSizeY / 2 - 0.5f);
        } else if (type == Type.TOUCAN.ordinal()) {
            bodyDef.position.set(positionX, Math.max(5,
                    world.caveman.body.getPosition().y - 20 + random.nextFloat() * 60));
            shape.setAsBox(smallEggSizeX, smallEggSizeX);
        } else if (type == Type.SABRETOOTH.ordinal()) {
            bodyDef.position.set(positionX, 1.5f);
            shape.setAsBox(sabretoothSize, sabretoothSize);
        }
    }
}