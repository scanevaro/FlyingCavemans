package com.deeep.flycaveman.obstacles.types;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.deeep.flycaveman.Assets;
import com.deeep.flycaveman.obstacles.ObstacleBase;
import com.deeep.flycaveman.world.World;

/**
 * Created by Elmar on 6-6-2015.
 */
public class Quetzalcoatlus extends ObstacleBase {
    public Quetzalcoatlus(Animation animation, float spriteSizeX, float spriteSizeY, float sizeX, float sizeY, World world, Body body) {
        super(animation, spriteSizeX, spriteSizeY, sizeX, sizeY, world, body);
    }

    public Quetzalcoatlus(World world) {
        super(Assets.quetzaTexture, 1f/2 + 0.5f, 2, 2, 2, world, null);
    }
}
