package com.deeep.flycaveman.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Array;
import com.deeep.flycaveman.Core;
import com.deeep.flycaveman.world.World;

import java.util.Random;

/**
 * Created by Elmar on 9-2-2015.
 */
public class ObstacleSpawner {
    private Array<Obstacle> entities;
    private Array<Obstacle> removals;
    private World world;
    private int maxPowerUps = 40;
    private Random random = new Random();

    public ObstacleSpawner(World world) {
        this.world = world;
        this.entities = new Array<Obstacle>();
        for (int i = 0; i < 8; i++) spawn(0);
        this.removals = new Array<Obstacle>();
    }

    public void update(float delta) {
        for (int y = 0; y < entities.size; y++) entities.get(y).update(delta);
        for (int z = 0; z < removals.size; z++) removals.get(z).update(delta);
        /**if (entities.size < maxPowerUps) if (entities.size < 8) spawnRandomRandom(world.caveman);*/
        for (int i = 0; i < entities.size; i++) {
            if (entities.get(i).body.getPosition().x + 30 < world.caveman.body.getPosition().x) entities.get(i).die();
            if (entities.get(i).isDead()) removals.add(entities.get(i));
        }
        for (int x = 0; x < removals.size; x++) /**world.box2dWorld.destroyBody(removals.get(x).body);*/
            removals.get(x).instantiate(world, random);
        /**entities.removeAll(removals, true);*/
        removals.clear();
    }

    public void spawnRandomRandom(CaveMan caveMan) {
        if (random.nextFloat() > 0.8f) {
            float x = caveMan.body.getPosition().x + Core.BOX2D_VIRTUAL_WIDTH + random.nextFloat() * 5;
            spawn(x);
        }
    }

    public void spawn(float x) {
        entities.add(new Obstacle(world, x, random));
    }

    public void draw(Batch batch) {
        for (int i = 0; i < entities.size; i++)
            entities.get(i).draw(batch);
    }
}