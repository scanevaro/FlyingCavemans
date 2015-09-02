package com.deeep.flycaveman.world;

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
    private Array<PowerUp> idleEntities;
    private Array<PowerUp> removals;
    private World world;
    private int maxPowerUps = 3;
    private Random random = new Random();

    public PowerUpSpawner(World world) {
        this.world = world;
        this.powerUps = new Array<PowerUp>();
        this.idleEntities = new Array<PowerUp>();
        this.removals = new Array<PowerUp>();
    }

    public void update(float delta) {
        for (int y = 0; y < powerUps.size; y++) powerUps.get(y).update(delta);
        for (int z = 0; z < removals.size; z++) removals.get(z).update(delta);
        if (powerUps.size < maxPowerUps) if (powerUps.size < 8) spawnRandomRandom(world.caveman);
        for (int i = 0; i < powerUps.size; i++) {
            if (powerUps.get(i).body.getPosition().x + 30 < world.caveman.body.getPosition().x) powerUps.get(i).die();
            if (powerUps.get(i).isDead()) removals.add(powerUps.get(i));
        }
        for (int x = 0; x < removals.size; x++) idleEntities.add(removals.get(x));
        powerUps.removeAll(removals, true);
        removals.clear();
    }

    public void spawnRandomRandom(CaveMan caveMan) {
        PowerUp.Type tempType;
        float x = caveMan.body.getPosition().x + Core.BOX2D_VIRTUAL_WIDTH + 5 + random.nextFloat() * 60;

        float y = Math.max(5, caveMan.body.getPosition().y - 20 + random.nextFloat() * 60);
        switch (random.nextInt(5)) {
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
            case 4:
                tempType = PowerUp.Type.BEER;
                break;
            default:
                tempType = PowerUp.Type.VODKA;
        }
        spawn(x, y, tempType);

    }

    public void spawn(float x, float y, PowerUp.Type type) {
        int i;
        for (i = 0; i < idleEntities.size; i++)
            if (idleEntities.get(i).type == type && idleEntities.get(i).isDead()) {
                powerUps.add(idleEntities.removeIndex(i).setAlive(x, y));
                return;
            }
        if (i == idleEntities.size) powerUps.add(new PowerUp(type, world, x, y));
    }

    public void draw(SpriteBatch spriteBatch) {
        for (int i = 0; i < powerUps.size; i++) powerUps.get(i).draw(spriteBatch);
    }
}