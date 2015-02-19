package com.deeep.flycaveman.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.*;
import com.deeep.flycaveman.classes.Assets;
import com.deeep.flycaveman.classes.World;

/**
 * Created by Elmar on 9-2-2015.
 */
public class PowerUp implements Entity {

    private boolean dead = false;

    public void die() {
        dead = true;
    }

    public void update(float deltaT) {

    }

    public boolean isDead() {
        return dead;
    }

    public static enum Type {
        MEAT(100), SPINACH(100), VODKA(-50), SODACAN(50);
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
            default:
                sprite = new Sprite(Assets.vodka);
        }
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;

        shape = new PolygonShape();
        bodyDef.position.set(x, y);
//        shape.setAsBox(1, 1.5f);
        shape.setAsBox(0.6f, 1.2f);
        fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;

        body = world.box2dWorld.createBody(bodyDef);

//        sprite.setSize(2, 3);
        sprite.setSize(1.2f, 2.4f);
        body.setUserData(sprite);

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
}
