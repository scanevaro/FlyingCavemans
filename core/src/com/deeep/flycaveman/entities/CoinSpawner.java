package com.deeep.flycaveman.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.deeep.flycaveman.Core;
import com.deeep.flycaveman.world.World;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Andreas on 2/11/2015.
 */
public class CoinSpawner {

    public static final float COIN_SPAWN_INTERVAL = 5F;
    public static final int COIN_PATTERN_COUNT = 2;

    private ArrayList<Coin> coins;
    private float coinSpawnTimer;
    private Random r;

    public CoinSpawner() {
        coins = new ArrayList<Coin>();
    }


    /**
     * Method for adding a pattern of coins in
     * @param id id of pattern to be used
     * @param caveman caveman to grab positional data from
     */
    public void spawnCoins(int id, CaveMan caveman, World world){
        switch(id) {
            case 0:
                coins.add(new Coin(caveman.body.getPosition().x + Core.pixelsToBoxUnit(1100), caveman.body.getPosition().y + Core.pixelsToBoxUnit(50), world));
                coins.add(new Coin(caveman.body.getPosition().x + Core.pixelsToBoxUnit(1300), caveman.body.getPosition().y + Core.pixelsToBoxUnit(100), world));
                coins.add(new Coin(caveman.body.getPosition().x + Core.pixelsToBoxUnit(1500), caveman.body.getPosition().y +Core.pixelsToBoxUnit(150) , world));
                break;
            case 1:
                coins.add(new Coin(caveman.body.getPosition().x + Core.pixelsToBoxUnit(1100), caveman.body.getPosition().y + Core.pixelsToBoxUnit(50), world));
                coins.add(new Coin(caveman.body.getPosition().x + Core.pixelsToBoxUnit(1100), caveman.body.getPosition().y +Core.pixelsToBoxUnit(150) , world));
                coins.add(new Coin(caveman.body.getPosition().x + Core.pixelsToBoxUnit(1300), caveman.body.getPosition().y + Core.pixelsToBoxUnit(100), world));
                coins.add(new Coin(caveman.body.getPosition().x + Core.pixelsToBoxUnit(1500), caveman.body.getPosition().y + Core.pixelsToBoxUnit(100), world));
                break;
        }
    }

    //TODO: coins shouldn't draw outside screen
    public void render(Batch b) {
        for (Coin c: coins){
            c.draw(b);
        }
    }

    public void update(float delta, CaveMan caveman, World world) {
        coinSpawnTimer += delta;
        for (Coin c: coins){
            c.update(delta);
        }
        if(coinSpawnTimer > COIN_SPAWN_INTERVAL){
            spawnRandomCoins(0, caveman, world);
            coinSpawnTimer = 0;
        }
    }

    public void spawnRandomCoins(int id, CaveMan caveman, World world){
        r = new Random();
        int ran = r.nextInt(COIN_PATTERN_COUNT);
        spawnCoins(ran, caveman, world);
    }
}
