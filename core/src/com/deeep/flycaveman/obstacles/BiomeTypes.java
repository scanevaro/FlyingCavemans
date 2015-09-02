package com.deeep.flycaveman.obstacles;

import com.deeep.flycaveman.world.Biomes;

/**
 * Created by Elmar on 6-6-2015.
 */
public enum BiomeTypes {
    DESSERT(Biomes.DESSERT, Biomes.JUNGLE_DESSERT), JUNGLE(Biomes.JUNGLE, Biomes.DESSERT_JUNGLE), OCEAN(Biomes.OCEAN), SPACE, ALL(-1);
    public int[] numbers;

    BiomeTypes(int... numbers) {
        this.numbers = numbers;
    }
}
