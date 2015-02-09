package com.deeep.flycaveman.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.*;
import com.deeep.flycaveman.classes.Assets;

/**
 * Created by Elmar on 9-2-2015.
 */
public class PowerUp implements Entity {

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

    public PowerUp(Type type, World world, int x, int y) {
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
        bodyDef.position.set(x+13, y+5);
        shape.setAsBox(1, 1);
        fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;

        body = world.createBody(bodyDef);

        sprite.setSize(2, 2);
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
