package com.deeep.flycaveman.obstacles.types;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.deeep.flycaveman.obstacles.ObstacleBase;
import com.deeep.flycaveman.world.World;

/**
 * Created by Elmar on 6-6-2015.
 */
public class Mosquito extends ObstacleBase {
    public Mosquito(TextureRegion textureRegion, float spriteSizeX, float spriteSizeY, float sizeX, float sizeY, World world, Body body) {
        super(textureRegion, spriteSizeX, spriteSizeY, sizeX, sizeY, world, body);
    }
}
