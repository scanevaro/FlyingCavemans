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

    private ArrayList<Coin> coins;
    private float tick;
    private Random r;

    public CoinSpawner() {
        coins = new ArrayList<Coin>();
    }


    /**
     * Method for adding a pattern of coins in
     * @param id id of pattern to be used
     * @param cavemanX x pos of caveman
     * @param cavemanWidth width of caveman
     */
    public void spawnCoins(int id, float cavemanX, float cavemanWidth, World world){
        //sX is the first chord coins can spawn at. It's 1 more than the screen
        float sX =  cavemanX + cavemanWidth + Core.VIRTUAL_WIDTH / 2F + 1F;
        r = new Random();
        switch(id) {
            case 0:
                coins.add(new Coin(sX / 5F, 7F, world));
                 coins.add(new Coin((sX + 5F) / 5F, 7F, world));
                coins.add(new Coin((sX + 10F) / 5F, 7F, world));
                break;
            case 1:
                coins.add(new Coin(sX / 5F, 3F, world));
                coins.add(new Coin((sX + 5F) / 5F, 3F, world));
                coins.add(new Coin((sX + 10F) / 5F, 3F, world));
                break;
        }
    }

    public void render(Batch b) {
        for (Coin c: coins){
            c.draw(b);
        }
    }

    public void update(float delta, CaveMan caveman, World world) {
        for (Coin c: coins){
            c.update(delta);
        }
        if(caveman.body.getPosition().x > 50) spawnCoins(r.nextInt(1), caveman.body.getPosition().x, caveman.sprite.getWidth(), world);
    }
}
