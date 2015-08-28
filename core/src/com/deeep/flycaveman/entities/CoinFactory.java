package com.deeep.flycaveman.entities;

import com.badlogic.gdx.utils.Array;
import com.deeep.flycaveman.world.World;

/**
 * Created by scanevaro on 27/08/2015.
 */
public class CoinFactory {
    private static Array<Coin> coins = new Array<Coin>();

    public static Coin poolCoin(float x, float y, World world) {
        for (int i = 0; i < coins.size; i++) return coins.removeIndex(i).setAlive(x, y);
        return new Coin(x, y, world);
    }

    public static void add(Coin coin) {
        coins.add(coin);
    }
}