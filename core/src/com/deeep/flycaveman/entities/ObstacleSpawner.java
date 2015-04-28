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
        this.removals = new Array<Obstacle>();
    }

    public void update(float delta) {
        for (Obstacle obstacle : entities) obstacle.update(delta);
        for (Obstacle obstacle : removals) obstacle.update(delta);
        if (entities.size < maxPowerUps) if (entities.size < 8) spawnRandomRandom(world.caveman);
        for (int i = 0; i < entities.size; i++) {
            if (entities.get(i).body.getPosition().x + 30 < world.caveman.body.getPosition().x) entities.get(i).die();
            if (entities.get(i).isDead()) removals.add(entities.get(i));
        }
        for (Obstacle obstacle : removals) world.box2dWorld.destroyBody(obstacle.body);
        entities.removeAll(removals, true);
        removals.clear();
    }

    public void spawnRandomRandom(CaveMan caveMan) {
        float x = caveMan.body.getPosition().x + Core.BOX2D_VIRTUAL_WIDTH + random.nextFloat() * 5;
        spawn(x);
    }

    public void spawn(float x) {
        entities.add(new Obstacle(world, x, random));
    }

    public void draw(Batch batch) {
        for (int i = 0; i < entities.size; i++)
            entities.get(i).draw(batch);
    }
}