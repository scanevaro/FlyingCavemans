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
        new CoinFactory();
        random = new Random();
    }

    //TODO: coins shouldn't draw outside screen
    public void render(Batch b) {
        for (int i = 0; i < coins.size; i++) coins.get(i).draw(b);
    }

    public void update(float delta, CaveMan caveman, World world) {
        if (GameInputProcessor.flying) coinSpawnTimer += delta;
        for (int x = 0; x < coins.size; x++) {
            if (coins.get(x).body.getPosition().x + 30 < caveman.body.getPosition().x) coins.get(x).die();
            if (!coins.get(x).isAlive()) {
                CoinFactory.add(coins.removeIndex(x));
                x--;
            }
        }
        for (int i = 0; i < coins.size; i++) coins.get(i).update(delta);
        if (coinSpawnTimer > COIN_SPAWN_INTERVAL) {
            spawnRandomCoins(caveman, world);
            coinSpawnTimer = 0;
        }
    }

    public void spawnRandomCoins(CaveMan caveman, World world) {
        int ran = random.nextInt(COIN_PATTERN_COUNT);
        spawnCoins(ran, caveman, world);
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
                coins.add(CoinFactory.poolCoin(caveman.body.getPosition().x + 6 + Core.pixelsToUnit1100, caveman.body.getPosition().y + Core.pixelsToUnit50, world));
                coins.add(CoinFactory.poolCoin(caveman.body.getPosition().x + 6 + Core.pixelsToUnit1300, caveman.body.getPosition().y + Core.pixelsToUnit100, world));
                coins.add(CoinFactory.poolCoin(caveman.body.getPosition().x + 6 + Core.pixelsToUnit1500, caveman.body.getPosition().y + Core.pixelsToUnit150, world));
                break;
            case 1:
                coins.add(CoinFactory.poolCoin(caveman.body.getPosition().x + 6 + Core.pixelsToUnit1100, caveman.body.getPosition().y + Core.pixelsToUnit50, world));
                coins.add(CoinFactory.poolCoin(caveman.body.getPosition().x + 6 + Core.pixelsToUnit1100, caveman.body.getPosition().y + Core.pixelsToUnit150, world));
                coins.add(CoinFactory.poolCoin(caveman.body.getPosition().x + 6 + Core.pixelsToUnit1300, caveman.body.getPosition().y + Core.pixelsToUnit100, world));
                coins.add(CoinFactory.poolCoin(caveman.body.getPosition().x + 6 + Core.pixelsToUnit1500, caveman.body.getPosition().y + Core.pixelsToUnit100, world));
                break;
            case 2:
                coins.add(CoinFactory.poolCoin(caveman.body.getPosition().x + 6 + Core.pixelsToUnit1100, caveman.body.getPosition().y + Core.pixelsToUnit100, world));
                coins.add(CoinFactory.poolCoin(caveman.body.getPosition().x + 6 + Core.pixelsToUnit1300, caveman.body.getPosition().y + Core.pixelsToUnit50, world));
                coins.add(CoinFactory.poolCoin(caveman.body.getPosition().x + 6 + Core.pixelsToUnit1300, caveman.body.getPosition().y + Core.pixelsToUnit150, world));
                coins.add(CoinFactory.poolCoin(caveman.body.getPosition().x + 6 + Core.pixelsToUnit1500, caveman.body.getPosition().y + Core.pixelsToUnit100, world));
                break;
            case 3:
                coins.add(CoinFactory.poolCoin(caveman.body.getPosition().x + 6 + Core.pixelsToUnit1100, caveman.body.getPosition().y + Core.pixelsToUnit100, world));
                coins.add(CoinFactory.poolCoin(caveman.body.getPosition().x + 6 + Core.pixelsToUnit1300, caveman.body.getPosition().y + Core.pixelsToUnit100, world));
                coins.add(CoinFactory.poolCoin(caveman.body.getPosition().x + 6 + Core.pixelsToUnit1500, caveman.body.getPosition().y + Core.pixelsToUnit100, world));
                break;
        }
    }
}