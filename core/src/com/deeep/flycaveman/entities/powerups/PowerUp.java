package com.deeep.flycaveman.entities.powerups;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.deeep.flycaveman.world.World;

/**
 * Created by Andreas on 1/29/2015.
 */


public class PowerUp {

    private BodyDef bDef;
    private Body body;
    private Texture texture;
    private FixtureDef fDef;

    /**
     * @param type Type of bodydef
     * @param world World to be parented with
     */
    public PowerUp(int type, World world){

        //Body and Bodydef
        bDef = new BodyDef();
        bDef.position.set(160, 200);
        switch (type) {
            case 1:
                bDef.type = BodyDef.BodyType.DynamicBody;
            case 2:
                bDef.type = BodyDef.BodyType.StaticBody;
            case 3:
                bDef.type = BodyDef.BodyType.KinematicBody;
        }
        body = world.box2dWorld.createBody(bDef);

        //Fixture
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(50, 50);
        fDef = new FixtureDef();
        fDef.shape = shape;
        body.createFixture(fDef);

    }

}
