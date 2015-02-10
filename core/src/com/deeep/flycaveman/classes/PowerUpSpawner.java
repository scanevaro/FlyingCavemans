package com.deeep.flycaveman.classes;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.deeep.flycaveman.Core;
import com.deeep.flycaveman.entities.CaveMan;
import com.deeep.flycaveman.entities.PowerUp;

import java.util.Random;

/**
 * Created by Elmar on 9-2-2015.
 */
public class PowerUpSpawner {
    private Array<PowerUp> powerUps;
    private Array<PowerUp> removals;
    private World world;
    private int maxPowerUps = 5;
    private Random random = new Random();

    public PowerUpSpawner(World world) {
        this.world = world;
        this.powerUps = new Array<PowerUp>();
        this.removals = new Array<PowerUp>();
    }

    public void update(float deltaT) {
        if (powerUps.size < maxPowerUps) {
            if (powerUps.size < 3) {
                spawnRandomRandom(world.caveman);
            }
        }
        for (int i = 0; i < powerUps.size; i++) {
            powerUps.get(i).update(deltaT);
            if (powerUps.get(i).body.getPosition().x + 10 < world.caveman.body.getPosition().x) {
                powerUps.get(i).die();
            }
            if (powerUps.get(i).isDead()) {
                removals.add(powerUps.get(i));
            }
        }
        for (PowerUp powerUp : removals) {
            world.box2dWorld.destroyBody(powerUp.body);
        }
        powerUps.removeAll(removals, true);
        removals.clear();
    }

    public void spawnRandomRandom(CaveMan caveMan) {
        PowerUp.Type tempType;
        float x = caveMan.body.getPosition().x + Core.BOX2D_VIRTUAL_WIDTH + 10 + powerUps.size * 100;

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
        powerUps.add(new PowerUp(type, world, x, y));
        System.out.println("spawn");
    }

    public void draw(SpriteBatch spriteBatch) {
        for (int i = 0; i < powerUps.size; i++) {
            powerUps.get(i).draw(spriteBatch);
        }
    }
}
