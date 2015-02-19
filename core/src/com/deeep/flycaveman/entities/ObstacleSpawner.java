package com.deeep.flycaveman.entities;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.deeep.flycaveman.Core;
import com.deeep.flycaveman.classes.World;

import java.util.Random;

/**
 * Created by Elmar on 9-2-2015.
 */
public class ObstacleSpawner {
    private Array<Entity> entities;
    private Array<PowerUp> removals;
    private World world;
    private int maxPowerUps = 80;
    private Random random = new Random();

    public ObstacleSpawner(World world) {
        this.world = world;
        this.entities = new Array<Entity>();
        this.removals = new Array<PowerUp>();
    }

    public void update(float deltaT) {
        if (entities.size < maxPowerUps) {
            if (entities.size < 8) {
                spawnRandomRandom(world.caveman);
            }
        }
        for (int i = 0; i < entities.size; i++) {
//            entities.get(i).update(deltaT);
//            if (entities.get(i).body.getPosition().x + 10 < world.caveman.body.getPosition().x) {
//                entities.get(i).die();
//            }
//            if (entities.get(i).isDead()) {
//                removals.add(entities.get(i));
//            }
        }
        for (PowerUp powerUp : removals) {
            world.box2dWorld.destroyBody(powerUp.body);
        }
        entities.removeAll(removals, true);
        removals.clear();
    }

    public void spawnRandomRandom(CaveMan caveMan) {
        PowerUp.Type tempType;
        float x = caveMan.body.getPosition().x + Core.BOX2D_VIRTUAL_WIDTH + 10 + entities.size * 100;

        float y = Math.max(5, caveMan.body.getPosition().y - 20 + random.nextFloat() * 60);
        switch (random.nextInt(4)) {
            case 0:
                tempType = PowerUp.Type.MEAT;
                break;
            case 1:
                tempType = PowerUp.Type.SODACAN;
                break;
            case 2:
                tempType = PowerUp.Type.SPINACH;
                break;
            case 3:
                tempType = PowerUp.Type.VODKA;
                break;
            default:
                tempType = PowerUp.Type.VODKA;
        }
        spawn(x, y, tempType);

    }

    public void spawn(float x, float y, PowerUp.Type type) {
        entities.add(new PowerUp(type, world, x, y));
    }

    public void draw(SpriteBatch spriteBatch) {
        for (int i = 0; i < entities.size; i++) {
            entities.get(i).draw(spriteBatch);
        }
    }
}
