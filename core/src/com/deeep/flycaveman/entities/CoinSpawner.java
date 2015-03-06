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

    public static final float COIN_SPAWN_INTERVAL = 10F;

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
        System.out.println("rofxddwfwel");
        r = new Random();
        switch(id) {
            case 0:
                coins.add(new Coin(caveman.body.getPosition().x + Core.pixelsToBoxUnit(2100), caveman.body.getPosition().y + Core.pixelsToBoxUnit(-50), world));
                coins.add(new Coin(caveman.body.getPosition().x + Core.pixelsToBoxUnit(2300), caveman.body.getPosition().y + Core.pixelsToBoxUnit(0), world));
                coins.add(new Coin(caveman.body.getPosition().x + Core.pixelsToBoxUnit(2500), caveman.body.getPosition().y +Core.pixelsToBoxUnit(50) , world));
                break;
        }
    }

    public void render(Batch b) {
        for (Coin c: coins){
            System.out.println("rofl");
            c.draw(b);
        }
    }

    public void update(float delta, CaveMan caveman, World world) {
        coinSpawnTimer += delta;
        for (Coin c: coins){
            c.update(delta);
        }
        if(coinSpawnTimer > COIN_SPAWN_INTERVAL){
            spawnCoins(0, caveman, world);
            coinSpawnTimer = 0;
        }
        System.out.println("rofl " + coinSpawnTimer);
    }
}
