package com.deeep.flycaveman.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.*;
import com.deeep.flycaveman.Assets;
import com.deeep.flycaveman.world.World;

/**
 * Created by Elmar on 9-2-2015.
 */
public class PowerUp implements Entity {

    public static enum Type {
        MEAT(100),
        SPINACH(100),
        VODKA(-50),
        SODACAN(50),
        BEER(-50);
        int percentage;

        Type(int percentage) {
            this.percentage = percentage;
        }

        public int getPercentage() {
            return percentage;
        }
    }

    public final Type type;
    private Sprite sprite;
    private BodyDef bodyDef;
    public Body body;
    private FixtureDef fixtureDef;
    public Fixture fixture;
    private PolygonShape shape;
    private boolean dead = false;
    private final float powX = 0.6f, powY = 1.2f;
    private float realSizeX, realSizeY, textureSizeX, textureSizeY;

    public void die() {
        dead = true;
    }

    public void update(float deltaT) {
    }

    public boolean isDead() {
        return dead;
    }

    public PowerUp(Type type, World world, float x, float y) {
        this.type = type;
        switch (type) {
            case MEAT:
                sprite = new Sprite(Assets.meat);
                break;
            case SPINACH:
                sprite = new Sprite(Assets.spinach);
                break;
            case VODKA:
                sprite = new Sprite(Assets.vodka);
                break;
            case SODACAN:
                sprite = new Sprite(Assets.soda);
                break;
            case BEER:
                sprite = new Sprite(Assets.beer);
                break;
            default:
                sprite = new Sprite(Assets.vodka);
        }
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        shape = new PolygonShape();
        bodyDef.position.set(x, y);
        shape.setAsBox(powX, powY);
        fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;
        body = world.box2dWorld.createBody(bodyDef);
        switch (type) {
            case MEAT:
                sprite.setSize(powX * 2, powY * 2);
                realSizeX = powX;
                realSizeY = powY;
                break;
            case SPINACH:
                sprite.setSize(powX * 2, powY * 2);
                realSizeX = powX;
                realSizeY = powY;
                break;
            case VODKA:
                sprite.setSize(powX * 2, powY * 2);
                realSizeX = powX;
                realSizeY = powY;
                break;
            case SODACAN:
                sprite.setSize(powX * 2, powY * 2);
                realSizeX = powX;
                realSizeY = powY;
                break;
            case BEER:
                sprite.setSize(powX * 2, powY * 2);
                realSizeX = powX;
                realSizeY = powY;
                break;
            default:
                break;
        }
        body.setUserData(sprite);
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);
        shape.dispose();
        body.setAwake(false);
        textureSizeX = sprite.getRegionWidth();
        textureSizeY = sprite.getRegionHeight();
    }


    @Override
    public void draw(Batch batch) {
        switch (type) {
            case MEAT:
                sprite.setSize(sprite.getRegionWidth() * (realSizeX * 2) / textureSizeX,
                        sprite.getRegionHeight() * (realSizeY * 2) / textureSizeY);
                sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
                sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2,
                        body.getPosition().y - sprite.getHeight() / 2);
                break;
            case SPINACH:
                sprite.setSize(sprite.getRegionWidth() * (realSizeX * 2) / textureSizeX,
                        sprite.getRegionHeight() * (realSizeY * 2) / textureSizeY);
                sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
                sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2,
                        body.getPosition().y - sprite.getHeight() / 2);
                break;
            case VODKA:
                sprite.setSize(sprite.getRegionWidth() * (realSizeX * 2) / textureSizeX,
                        sprite.getRegionHeight() * (realSizeY * 2) / textureSizeY);
                sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
                sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2,
                        body.getPosition().y - sprite.getHeight() / 2);
                break;
            case SODACAN:
                sprite.setSize(sprite.getRegionWidth() * (realSizeX * 2) / textureSizeX,
                        sprite.getRegionHeight() * (realSizeY * 2) / textureSizeY);
                sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
                sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2,
                        body.getPosition().y - sprite.getHeight() / 2);
                break;
            case BEER:
                sprite.setSize(sprite.getRegionWidth() * (realSizeX * 2) / textureSizeX,
                        sprite.getRegionHeight() * (realSizeY * 2) / textureSizeY);
                sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
                sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2,
                        body.getPosition().y - sprite.getHeight() / 2);
                break;
            default:
                break;
        }
        sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
        sprite.draw(batch);
    }
}