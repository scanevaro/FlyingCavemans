package com.deeep.flycaveman.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Array;
import com.deeep.flycaveman.Core;
import com.deeep.flycaveman.input.GameInputProcessor;
import com.deeep.flycaveman.world.World;

import java.util.Random;

/**
 * Created by Andreas on 2/11/2015.
 */
public class CoinSpawner {

    public static final float COIN_SPAWN_INTERVAL = 2.5F;
    public static final int COIN_PATTERN_COUNT = 4;

    private Array<Coin> coins;
    private float coinSpawnTimer;
    private Random random;

    public CoinSpawner() {
        coins = new Array<Coin>();
        random = new Random();
    }


    /**
     * Method for adding a pattern of coins in
     *
     * @param id      id of pattern to be used
     * @param caveman caveman to grab positional data from
     */
    public void spawnCoins(int id, CaveMan caveman, World world) {
        switch (id) {
            case 0:
                coins.add(new Coin(caveman.body.getPosition().x + 6 + Core.pixelsToBoxUnit(1100), caveman.body.getPosition().y + Core.pixelsToBoxUnit(50), world));
                coins.add(new Coin(caveman.body.getPosition().x + 6 + Core.pixelsToBoxUnit(1300), caveman.body.getPosition().y + Core.pixelsToBoxUnit(100), world));
                coins.add(new Coin(caveman.body.getPosition().x + 6 + Core.pixelsToBoxUnit(1500), caveman.body.getPosition().y + Core.pixelsToBoxUnit(150), world));
                break;
            case 1:
                coins.add(new Coin(caveman.body.getPosition().x + 6 + Core.pixelsToBoxUnit(1100), caveman.body.getPosition().y + Core.pixelsToBoxUnit(50), world));
                coins.add(new Coin(caveman.body.getPosition().x + 6 + Core.pixelsToBoxUnit(1100), caveman.body.getPosition().y + Core.pixelsToBoxUnit(150), world));
                coins.add(new Coin(caveman.body.getPosition().x + 6 + Core.pixelsToBoxUnit(1300), caveman.body.getPosition().y + Core.pixelsToBoxUnit(100), world));
                coins.add(new Coin(caveman.body.getPosition().x + 6 + Core.pixelsToBoxUnit(1500), caveman.body.getPosition().y + Core.pixelsToBoxUnit(100), world));
                break;
            case 2:
                coins.add(new Coin(caveman.body.getPosition().x + 6 + Core.pixelsToBoxUnit(1100), caveman.body.getPosition().y + Core.pixelsToBoxUnit(100), world));
                coins.add(new Coin(caveman.body.getPosition().x + 6 + Core.pixelsToBoxUnit(1300), caveman.body.getPosition().y + Core.pixelsToBoxUnit(50), world));
                coins.add(new Coin(caveman.body.getPosition().x + 6 + Core.pixelsToBoxUnit(1300), caveman.body.getPosition().y + Core.pixelsToBoxUnit(150), world));
                coins.add(new Coin(caveman.body.getPosition().x + 6 + Core.pixelsToBoxUnit(1500), caveman.body.getPosition().y + Core.pixelsToBoxUnit(100), world));
                break;
            case 3:
                coins.add(new Coin(caveman.body.getPosition().x + 6 + Core.pixelsToBoxUnit(1100), caveman.body.getPosition().y + Core.pixelsToBoxUnit(100), world));
                coins.add(new Coin(caveman.body.getPosition().x + 6 + Core.pixelsToBoxUnit(1300), caveman.body.getPosition().y + Core.pixelsToBoxUnit(100), world));
                coins.add(new Coin(caveman.body.getPosition().x + 6 + Core.pixelsToBoxUnit(1500), caveman.body.getPosition().y + Core.pixelsToBoxUnit(100), world));
                break;
        }
    }

    //TODO: coins shouldn't draw outside screen
    public void render(Batch b) {
        for (int i = 0; i < coins.size; i++) coins.get(i).draw(b);
    }

    public void update(float delta, CaveMan caveman, World world) {
        if (GameInputProcessor.flying) coinSpawnTimer += delta;
        for (int i = 0; i < coins.size; i++) coins.get(i).update(delta);
        if (coinSpawnTimer > COIN_SPAWN_INTERVAL) {
            spawnRandomCoins(0, caveman, world);
            coinSpawnTimer = 0;
        }
    }

    public void spawnRandomCoins(int id, CaveMan caveman, World world) {
        int ran = random.nextInt(COIN_PATTERN_COUNT);
        spawnCoins(ran, caveman, world);
    }

    public void remove(Coin coin) {
        coins.removeValue(coin, false);
    }
}
