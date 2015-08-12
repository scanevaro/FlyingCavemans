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

    public enum Type {
        SMALL_EGG, BRACHIOSAURUS, QUETZALCOATLUS, ARGENTAVIS, TOUCAN, SABRETOOTH, MOSQUITO, CARNIVORE
    }

    private boolean dead = false;
    private BodyDef bodyDef;
    public Body body, body2, body3;
    private FixtureDef fixtureDef;
    private Fixture fixture;
    private PolygonShape shape;
    public Type type;
    private final float smallEggSizeX = 0.65f, smallEggSizeY = 0.5f, quetzaSizeX = 2, quetzaSizeY = 2, brachioSizeX = 4,
            brachioSizeY = 3, sabretoothSize = 0.65f, mosquitoSize = 0.65f, carnivoreSizeX = 1.8f,
            carnivoreSizeY = 1.25f;
    private float realSizeX, realSizeY, textureSizeX, textureSizeY;
    private Sprite sprite;
    private boolean hit;
    private float stateTime;
    private Body tempBody;
    private final float radiansToDegrees = MathUtils.radiansToDegrees;
    private final Random random;
    private final World world;

    public Obstacle(Type type, World world, float positionX, Random random) {
        this.world = world;
        this.random = random;
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        shape = new PolygonShape();
        positionX += random.nextInt(100);
        this.type = type;
        setPositionAndShape(positionX);
        fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;
        if (type == Type.BRACHIOSAURUS) {
            BodyDef bodyDef2 = new BodyDef();
            bodyDef2.type = BodyDef.BodyType.DynamicBody;
            PolygonShape shape2 = new PolygonShape();
            bodyDef2.position.set(positionX - 2.65f, 2.5f);
            shape2.setAsBox(brachioSizeX / 3, brachioSizeY / 2 + 1);
            FixtureDef fixtureDef2 = new FixtureDef();
            fixtureDef2.shape = shape2;
            fixtureDef2.isSensor = true;
            body2 = world.box2dWorld.createBody(bodyDef2);
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
            body3 = world.box2dWorld.createBody(bodyDef3);
            body3.setUserData("BodyTail");
            Fixture fixture3 = body3.createFixture(fixtureDef3);
            fixture3.setUserData(this);
            shape3.dispose();
            body3.setAwake(false);
        }
        body = world.box2dWorld.createBody(bodyDef);
        switch (type) {
            case SMALL_EGG: /**SMALL_EGG*/
                sprite = new Sprite(new TextureRegion(Assets.smallEggTexture/*.getKeyFrame(stateTime)*/));
                sprite.setSize(smallEggSizeX * 2, smallEggSizeY * 2);
                realSizeX = smallEggSizeX;
                realSizeY = smallEggSizeY;
                break;
            case BRACHIOSAURUS: /**BRACHIOSAURUS*/
                sprite = new Sprite(new TextureRegion(Assets.brachioTexture/*.getKeyFrame(stateTime)*/));
                sprite.setSize(brachioSizeX * 2, brachioSizeY * 2);
                realSizeX = brachioSizeX;
                realSizeY = brachioSizeY;
                break;
            case QUETZALCOATLUS: /**QUETZALCOATLUS*/
                sprite = new Sprite(new TextureRegion(Assets.quetzaTexture.getKeyFrame(0)));
                sprite.setSize(quetzaSizeX * 2, quetzaSizeY * 2);
                realSizeX = quetzaSizeX;
                realSizeY = quetzaSizeY;
                break;
            case ARGENTAVIS: /**ARGENTAVIS*/
                sprite = new Sprite(new TextureRegion(Assets.argenTexture/*.getKeyFrame(stateTime)*/));
                sprite.setSize((quetzaSizeX + 0.5f) * 2, (quetzaSizeY + 0.5f) * 2);
                realSizeX = quetzaSizeX + 0.5f;
                realSizeY = quetzaSizeY + 0.5f;
                break;
            case TOUCAN: /**TOUCAN*/
                sprite = new Sprite(new TextureRegion(Assets.toucanTexture/*.getKeyFrame(stateTime)*/));
                sprite.setSize(smallEggSizeX * 2, smallEggSizeX * 2);
                realSizeX = smallEggSizeX;
                realSizeY = smallEggSizeX;
                break;
            case SABRETOOTH: /**SABRETOOTH*/
                sprite = new Sprite(new TextureRegion(Assets.sabertoothIdle.getKeyFrame(stateTime)));
                sprite.setSize(sabretoothSize * 2.25f, sabretoothSize * 2);
                realSizeX = sabretoothSize + 0.5f;
                realSizeY = sabretoothSize;
                break;
            case MOSQUITO: /**MOSQUITO*/
                sprite = new Sprite(new TextureRegion(Assets.mosquitoTexture/*.getKeyFrame(stateTime)*/));
                sprite.setSize(mosquitoSize * 2, mosquitoSize * 2);
                realSizeX = mosquitoSize;
                realSizeY = mosquitoSize;
                break;
            case CARNIVORE: /**CARNIVORE PLANT*/
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
        body.setAwake(false);
    }

    @Override
    public void draw(Batch batch) {
        getSpriteForType();
        setSpriteSize();
        setSpritePosition();
        sprite.setRotation(body.getAngle() * radiansToDegrees);
        sprite.draw(batch);
    }

    @Override
    public void update(float delta) {
        stateTime += delta;
        if (!hit && type == Type.QUETZALCOATLUS || type == Type.ARGENTAVIS
                || type == Type.TOUCAN || type == Type.MOSQUITO) {
            body.setTransform(body.getPosition().x - (delta * 6), body.getPosition().y, body.getAngle());
        } else if (hit && type == Type.QUETZALCOATLUS || type == Type.ARGENTAVIS
                || type == Type.TOUCAN || type == Type.MOSQUITO) {
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

    private void setPositionAndShape(float positionX) {
        switch (type) {
            case SMALL_EGG: /**SMALL_EGG*/
                bodyDef.position.set(positionX, 1.35f);
                shape.setAsBox(smallEggSizeX / 2, smallEggSizeY - 0.15f);
                break;
            case BRACHIOSAURUS: /**BRACHIOSAURUS*/
                bodyDef.position.set(positionX, 4f);
                shape.setAsBox(brachioSizeX / 3, brachioSizeY - 1.5f);
                break;
            case QUETZALCOATLUS: /**QUETZALCOATLUS*/
                bodyDef.position.set(positionX, Math.max(5,
                        world.caveman.body.getPosition().y - 20 + random.nextFloat() * 60));
                shape.setAsBox(quetzaSizeX / 2 + 0.3f, quetzaSizeY / 2 - 0.6f);
                break;
            case ARGENTAVIS: /**ARGENTAVIS*/
                bodyDef.position.set(positionX, Math.max(5,
                        world.caveman.body.getPosition().y - 20 + random.nextFloat() * 60));
                shape.setAsBox(quetzaSizeX / 2 + 0.5f, quetzaSizeY / 2 - 0.5f);
                break;
            case TOUCAN: /**TOUCAN*/
                bodyDef.position.set(positionX, Math.max(5,
                        world.caveman.body.getPosition().y - 20 + random.nextFloat() * 60));
                shape.setAsBox(smallEggSizeX - 0.2f, smallEggSizeX - 0.2f);
                break;
            case SABRETOOTH: /**SABRETOOTH*/
                bodyDef.position.set(positionX, 1.7f);
                shape.setAsBox(sabretoothSize + 0.15f, sabretoothSize - 0.22f);
                break;
            case MOSQUITO: /**MOSQUITO*/
                bodyDef.position.set(positionX, Math.max(5,
                        world.caveman.body.getPosition().y - 20 + random.nextFloat() * 60));
                shape.setAsBox(mosquitoSize - 0.2f, mosquitoSize - 0.2f);
                break;
            case CARNIVORE: /**CARNIVORE PLANT*/
                bodyDef.position.set(positionX, 1.95f);
                shape.setAsBox(carnivoreSizeX / 2, carnivoreSizeY - 0.9f);
                break;
        }
    }

    private void getSpriteForType() {
        switch (type) {
            case SMALL_EGG: /**SMALL_EGG*/
                if (!hit) sprite.setRegion(Assets.smallEggTexture);
                else sprite.setRegion(Assets.smallEggBroken);
                break;
            case BRACHIOSAURUS: /**BRACHIOSAURUS*/
                if (!hit) sprite.setRegion(Assets.brachioTexture);
                else if (tempBody != null) {
                    String tempString = (String) tempBody.getUserData();
                    if (tempString != null && tempString.equals("BodyHead")) sprite.setRegion(Assets.brachioFrontPull);
                    else if (tempString != null && tempString.equals("BodyTail"))
                        sprite.setRegion(Assets.brachioBackPull);
                    else sprite.setRegion(Assets.brachioMidPull);
                }
                break;
            case QUETZALCOATLUS: /**QUETZALCOATLUS*/
                if (!hit) sprite.setRegion(Assets.quetzaTexture.getKeyFrame(stateTime));
                else sprite.setRegion(Assets.quetzaHit.getKeyFrame(stateTime));
                break;
            case ARGENTAVIS: /**ARGENTAVIS*/
                if (!hit) sprite.setRegion(Assets.argenTexture);
                else sprite.setRegion(Assets.argenHit);
                break;
            case TOUCAN: /**TOUCAN*/
                if (!hit) sprite.setRegion(Assets.toucanTexture);
                else sprite.setRegion(Assets.toucanHit);
                break;
            case SABRETOOTH: /**SABRETOOTH*/
                if (!hit) sprite.setRegion(Assets.sabertoothIdle.getKeyFrame(stateTime));
                else sprite.setRegion(Assets.sabertoothHit.getKeyFrame(stateTime));
                break;
            case MOSQUITO: /**MOSQUITO*/
                if (!hit) sprite.setRegion(Assets.mosquitoTexture/*.getKeyFrame(stateTime)*/);
                else sprite.setRegion(Assets.mosquitoHit/*.getKeyFrame(stateTime)*/);
                break;
            case CARNIVORE: /**CARNIVORE PLANT*/
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
            case SMALL_EGG: /**SMALL_EGG*/
                sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2,
                        body.getPosition().y - sprite.getHeight() / 2);
                break;
            case BRACHIOSAURUS: /**BRACHIOSAURUS*/
                sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2,
                        body.getPosition().y - sprite.getHeight() / 2);
                break;
            case QUETZALCOATLUS: /**QUETZALCOATLUS*/
                sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2 + 0.5f,
                        body.getPosition().y - sprite.getHeight() / 2.5f);
                break;
            case ARGENTAVIS: /**ARGENTAVIS*/
                sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2,
                        body.getPosition().y - sprite.getHeight() / 5);
                break;
            case TOUCAN: /**TOUCAN*/
                sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2,
                        body.getPosition().y - sprite.getHeight() / 2);
                break;
            case SABRETOOTH: /**SABRETOOTH*/
                sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2,
                        body.getPosition().y - sprite.getHeight() / 2);
                break;
            case MOSQUITO: /**MOSQUITO*/
                sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2,
                        body.getPosition().y - sprite.getHeight() / 2);
                break;
            case CARNIVORE: /**CARNIVORE PLANT*/
                sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2,
                        body.getPosition().y - sprite.getHeight() / 2);
                break;
        }
    }

    public Obstacle setAlive(float positionX) {
        dead = false;
        hit = false;
        body.setActive(true);
        if (type == Type.BRACHIOSAURUS) {
            body2.setActive(true);
            body3.setActive(true);
        }
        positionX += random.nextInt(100);
        resetPosition(positionX);
        return this;
    }

    private void resetPosition(float positionX) {
        switch (type) {
            case SMALL_EGG:
                body.setTransform(positionX, 1.35f, body.getAngle());
                break;
            case BRACHIOSAURUS:
                body.setTransform(positionX, 4f, body.getAngle());
                break;
            case QUETZALCOATLUS:
                body.setTransform(positionX, Math.max(5, world.caveman.body.getPosition().y - 20 + random.nextFloat() * 60), body.getAngle());
                break;
            case ARGENTAVIS:
                body.setTransform(positionX, Math.max(5, world.caveman.body.getPosition().y - 20 + random.nextFloat() * 60), body.getAngle());
                break;
            case TOUCAN:
                body.setTransform(positionX, Math.max(5, world.caveman.body.getPosition().y - 20 + random.nextFloat() * 60), body.getAngle());
                break;
            case SABRETOOTH: /**SABRETOOTH*/
                body.setTransform(positionX, 1.7f, body.getAngle());
                break;
            case MOSQUITO: /**MOSQUITO*/
                body.setTransform(positionX, Math.max(5, world.caveman.body.getPosition().y - 20 + random.nextFloat() * 60), body.getAngle());
                break;
            case CARNIVORE: /**CARNIVORE PLANT*/
                body.setTransform(positionX, 1.95f, body.getAngle());
                break;
        }
    }
}