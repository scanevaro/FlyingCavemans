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
        SMALL_EGG, BRACHIOSAURUS, QUETZALCOATLUS, ARGENTAVIS, TOUCAN, SABRETOOTH, MOSQUITO, CARNIVORE
    }

    private boolean dead = false;
    private BodyDef bodyDef;
    public Body body;
    private FixtureDef fixtureDef;
    private Fixture fixture;
    private PolygonShape shape;
    public int type;
    private final float smallEggSizeX = 0.65f, smallEggSizeY = 0.5f, quetzaSizeX = 2, quetzaSizeY = 2, brachioSizeX = 4,
            brachioSizeY = 3, sabretoothSize = 0.65f, mosquitoSize = 0.65f, carnivoreSizeX = 1.8f,
            carnivoreSizeY = 1.25f;
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
        setPositionAndShape(positionX, random, world);
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
        switch (type) {
            case 0: /**SMALL_EGG*/
                sprite = new Sprite(new TextureRegion(Assets.smallEggTexture/*.getKeyFrame(stateTime)*/));
                sprite.setSize(smallEggSizeX * 2, smallEggSizeY * 2);
                realSizeX = smallEggSizeX;
                realSizeY = smallEggSizeY;
                break;
            case 1: /**BRACHIOSAURUS*/
                sprite = new Sprite(new TextureRegion(Assets.brachioTexture/*.getKeyFrame(stateTime)*/));
                sprite.setSize(brachioSizeX * 2, brachioSizeY * 2);
                realSizeX = brachioSizeX;
                realSizeY = brachioSizeY;
                break;
            case 2: /**QUETZALCOATLUS*/
                sprite = new Sprite(new TextureRegion(Assets.quetzaTexture.getKeyFrame(0)));
                sprite.setSize(quetzaSizeX * 2, quetzaSizeY * 2);
                realSizeX = quetzaSizeX;
                realSizeY = quetzaSizeY;
                break;
            case 3: /**ARGENTAVIS*/
                sprite = new Sprite(new TextureRegion(Assets.argenTexture/*.getKeyFrame(stateTime)*/));
                sprite.setSize((quetzaSizeX + 0.5f) * 2, (quetzaSizeY + 0.5f) * 2);
                realSizeX = quetzaSizeX + 0.5f;
                realSizeY = quetzaSizeY + 0.5f;
                break;
            case 4: /**TOUCAN*/
                sprite = new Sprite(new TextureRegion(Assets.toucanTexture/*.getKeyFrame(stateTime)*/));
                sprite.setSize(smallEggSizeX * 2, smallEggSizeX * 2);
                realSizeX = smallEggSizeX;
                realSizeY = smallEggSizeX;
                break;
            case 5: /**SABRETOOTH*/
                sprite = new Sprite(new TextureRegion(Assets.sabertoothIdle.getKeyFrame(stateTime)));
                sprite.setSize(sabretoothSize * 2.25f, sabretoothSize * 2);
                realSizeX = sabretoothSize + 0.5f;
                realSizeY = sabretoothSize;
                break;
            case 6: /**MOSQUITO*/
                sprite = new Sprite(new TextureRegion(Assets.mosquitoTexture/*.getKeyFrame(stateTime)*/));
                sprite.setSize(mosquitoSize * 2, mosquitoSize * 2);
                realSizeX = mosquitoSize;
                realSizeY = mosquitoSize;
                break;
            case 7: /**CARNIVORE PLANT*/
                sprite = new Sprite(new TextureRegion(Assets.carnivoreIdle/*.getKeyFrame(stateTime)*/));
                sprite.setSize(carnivoreSizeX * 2, carnivoreSizeY * 2);
                realSizeX = carnivoreSizeX;
                realSizeY = carnivoreSizeY;
                break;
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
        getSpriteForType();
        setSpriteSize();
        setSpritePosition();
        sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
        sprite.draw(batch);
    }

    @Override
    public void update(float delta) {
        stateTime += delta;
        if (!hit && type == Type.QUETZALCOATLUS.ordinal() || type == Type.ARGENTAVIS.ordinal()
                || type == Type.TOUCAN.ordinal() || type == Type.MOSQUITO.ordinal()) {
            body.setTransform(body.getPosition().x - (delta * 6), body.getPosition().y, body.getAngle());
        } else if (hit && type == Type.QUETZALCOATLUS.ordinal() || type == Type.ARGENTAVIS.ordinal()
                || type == Type.TOUCAN.ordinal() || type == Type.MOSQUITO.ordinal()) {
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
            float rand = random.nextFloat();
            if (rand <= 0.75f && rand > 0.35f) type = Type.QUETZALCOATLUS.ordinal();
            else if (rand <= 0.35f) type = Type.MOSQUITO.ordinal();
            else type = Type.ARGENTAVIS.ordinal();
        } else if (typeRand >= 0.96f) type = Type.BRACHIOSAURUS.ordinal();
        else if (typeRand <= 0.5f && typeRand > 0.20f) type = Type.SMALL_EGG.ordinal();
        else if (typeRand <= 0.20f && typeRand > 0.1f) type = Type.SABRETOOTH.ordinal();
        else if (typeRand <= 0.10f && typeRand > 0.04f) type = Type.CARNIVORE.ordinal();
        else type = Type.TOUCAN.ordinal();
    }

    private void setPositionAndShape(float positionX, Random random, World world) {
        switch (type) {
            case 0: /**SMALL_EGG*/
                bodyDef.position.set(positionX, 1.35f);
                shape.setAsBox(smallEggSizeX / 2, smallEggSizeY - 0.15f);
                break;
            case 1: /**BRACHIOSAURUS*/
                bodyDef.position.set(positionX, 4f);
                shape.setAsBox(brachioSizeX / 3, brachioSizeY - 1.5f);
                break;
            case 2: /**QUETZALCOATLUS*/
                bodyDef.position.set(positionX, Math.max(5,
                        world.caveman.body.getPosition().y - 20 + random.nextFloat() * 60));
                shape.setAsBox(quetzaSizeX / 2 + 0.3f, quetzaSizeY / 2 - 0.6f);
                break;
            case 3: /**ARGENTAVIS*/
                bodyDef.position.set(positionX, Math.max(5,
                        world.caveman.body.getPosition().y - 20 + random.nextFloat() * 60));
                shape.setAsBox(quetzaSizeX / 2 + 0.5f, quetzaSizeY / 2 - 0.5f);
                break;
            case 4: /**TOUCAN*/
                bodyDef.position.set(positionX, Math.max(5,
                        world.caveman.body.getPosition().y - 20 + random.nextFloat() * 60));
                shape.setAsBox(smallEggSizeX - 0.2f, smallEggSizeX - 0.2f);
                break;
            case 5: /**SABRETOOTH*/
                bodyDef.position.set(positionX, 1.7f);
                shape.setAsBox(sabretoothSize + 0.15f, sabretoothSize - 0.22f);
                break;
            case 6: /**MOSQUITO*/
                bodyDef.position.set(positionX, Math.max(5,
                        world.caveman.body.getPosition().y - 20 + random.nextFloat() * 60));
                shape.setAsBox(mosquitoSize - 0.2f, mosquitoSize - 0.2f);
                break;
            case 7: /**CARNIVORE PLANT*/
                bodyDef.position.set(positionX, 1.95f);
                shape.setAsBox(carnivoreSizeX / 2, carnivoreSizeY - 0.9f);
                break;
        }
    }

    private void getSpriteForType() {
        switch (type) {
            case 0: /**SMALL_EGG*/
                if (!hit) sprite.setRegion(Assets.smallEggTexture);
                else sprite.setRegion(Assets.smallEggBroken);
                break;
            case 1: /**BRACHIOSAURUS*/
                if (!hit) sprite.setRegion(Assets.brachioTexture);
                else if (tempBody != null) {
                    String tempString = (String) tempBody.getUserData();
                    if (tempString != null && tempString.equals("BodyHead")) sprite.setRegion(Assets.brachioFrontPull);
                    else if (tempString != null && tempString.equals("BodyTail"))
                        sprite.setRegion(Assets.brachioBackPull);
                    else sprite.setRegion(Assets.brachioMidPull);
                }
                break;
            case 2: /**QUETZALCOATLUS*/
                if (!hit) sprite.setRegion(Assets.quetzaTexture.getKeyFrame(stateTime));
                else sprite.setRegion(Assets.quetzaHit.getKeyFrame(stateTime));
                break;
            case 3: /**ARGENTAVIS*/
                if (!hit) sprite.setRegion(Assets.argenTexture);
                else sprite.setRegion(Assets.argenHit);
                break;
            case 4: /**TOUCAN*/
                if (!hit) sprite.setRegion(Assets.toucanTexture);
                else sprite.setRegion(Assets.toucanHit);
                break;
            case 5: /**SABRETOOTH*/
                if (!hit) sprite.setRegion(Assets.sabertoothIdle.getKeyFrame(stateTime));
                else sprite.setRegion(Assets.sabertoothHit.getKeyFrame(stateTime));
                break;
            case 6: /**MOSQUITO*/
                if (!hit) sprite.setRegion(Assets.mosquitoTexture/*.getKeyFrame(stateTime)*/);
                else sprite.setRegion(Assets.mosquitoHit/*.getKeyFrame(stateTime)*/);
                break;
            case 7: /**CARNIVORE PLANT*/
                if (!hit) sprite.setRegion(Assets.carnivoreIdle/*.getKeyFrame(stateTime)*/);
                else sprite.setRegion(Assets.carnivoreEat/*.getKeyFrame(stateTime)*/);
                break;
        }
    }

    private void setSpriteSize() {
        sprite.setSize(sprite.getRegionWidth() * (realSizeX * 2) / textureSizeX,
                sprite.getRegionHeight() * (realSizeY * 2) / textureSizeY);
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
    }

    private void setSpritePosition() {
        switch (type) {
            case 0: /**SMALL_EGG*/
                sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2,
                        body.getPosition().y - sprite.getHeight() / 2);
                break;
            case 1: /**BRACHIOSAURUS*/
                sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2,
                        body.getPosition().y - sprite.getHeight() / 2);
                break;
            case 2: /**QUETZALCOATLUS*/
                sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2 + 0.5f,
                        body.getPosition().y - sprite.getHeight() / 2.5f);
                break;
            case 3: /**ARGENTAVIS*/
                sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2,
                        body.getPosition().y - sprite.getHeight() / 5);
                break;
            case 4: /**TOUCAN*/
                sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2,
                        body.getPosition().y - sprite.getHeight() / 2);
                break;
            case 5: /**SABRETOOTH*/
                sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2,
                        body.getPosition().y - sprite.getHeight() / 2);
                break;
            case 6: /**MOSQUITO*/
                sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2,
                        body.getPosition().y - sprite.getHeight() / 2);
                break;
            case 7: /**CARNIVORE PLANT*/
                sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2,
                        body.getPosition().y - sprite.getHeight() / 2);
                break;
        }
    }
}