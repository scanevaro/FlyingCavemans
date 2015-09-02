package com.deeep.flycaveman.obstacles;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.*;
import com.deeep.flycaveman.entities.Entity;
import com.deeep.flycaveman.world.World;

/**
 * Created by Elmar on 6-6-2015.
 */
public abstract class ObstacleBase implements Entity {
    protected Sprite sprite;
    private boolean dead = false;
    private BodyDef bodyDef;
    public Body body;
    private FixtureDef fixtureDef;
    private Fixture fixture;
    private PolygonShape shape;
    private boolean hit;
    private float sizeX, sizeY;
    private Animation animation;

    public ObstacleBase(Animation animation, float spriteSizeX, float spriteSizeY, float sizeX, float sizeY, World world, Body body) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.animation = animation;
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;
        if (body == null) {
            body = world.box2dWorld.createBody(bodyDef);
        } else {
            //object pooling
        }
        sprite = new Sprite(animation.getKeyFrame(0));
        sprite.setSize(spriteSizeX, spriteSizeY);
        sprite.setSize(sprite.getRegionWidth() * (sizeX * 2) / sprite.getRegionWidth(),
                sprite.getRegionHeight() * (sizeY * 2) / sprite.getRegionWidth());
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
        fixture = body.createFixture(fixtureDef);
        fixture.setUserData(this);
        shape.dispose();
        body.setAwake(false);
    }

    protected Sprite setSprite(Sprite sprite) {
        sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
        return sprite;
    }

    @Override
    public void draw(Batch batch) {
        sprite = setSprite(sprite);
        sprite.setRotation(body.getAngle() * MathUtils.radiansToDegrees);
        sprite.draw(batch);
    }

    @Override
    public void update(float delta) {

    }

    public void setPosition(float x, float y) {
        body.setTransform(x, y, 0);
        sprite.setPosition(body.getPosition().x - sprite.getWidth() / 2, body.getPosition().y - sprite.getHeight() / 2);
    }
}
