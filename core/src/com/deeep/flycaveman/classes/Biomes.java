package com.deeep.flycaveman.classes;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.deeep.flycaveman.Assets;
import com.deeep.flycaveman.Core;

import java.util.HashMap;

/**
 * Created by Elmar on 3-3-2015.
 */
public class Biomes {
    public static final int backgroundWidth = (int) (Core.BOX2D_VIRTUAL_WIDTH + Core.BOX2D_VIRTUAL_WIDTH / 2);

    public static int JUNGLE_DESSERT = 1;
    public static int OCEAN_DESSERT = 2;
    public static int DESSERT = 10;
    public static int DESSERT_JUNGLE = 11;
    public static int OCEAN_JUNGLE = 12;
    public static int JUNGLE = 20;
    public static int JUNGLE_OCEAN = 21;
    public static int DESSERT_OCEAN = 22;
    public static int OCEAN = 30;

    public Background jungleDessertBackground;
    public Background oceanDessertBackground;
    public Background dessertBackground;
    public Background dessertJungleBackground;
    public Background oceanJungleBackground;
    public Background jungleBackground;
    public Background jungleOceanBackground;
    public Background dessertOceanBackground;
    public Background oceanBackground;

    public static int nextLayer[] = new int[4];

    public boolean isTransitioning() {
        return (nextLayer[0] % 10) != 0;
    }

    public int getCurrentBiome(boolean transition) {
        if (transition)
            return nextLayer[0];
        else
            return nextLayer[0] - nextLayer[0] % 10 + 10;

    }

    private Array<BiomeLayer> biomeLayer0 = new Array<BiomeLayer>();
    private Array<BiomeLayer> biomeLayer1 = new Array<BiomeLayer>();
    private Array<BiomeLayer> biomeLayer2 = new Array<BiomeLayer>();
    private Array<BiomeLayer> biomeLayer3 = new Array<BiomeLayer>();
    private Array<Array<BiomeLayer>> layers = new Array<Array<BiomeLayer>>();

    private HashMap<Integer, Background> integerBackgroundHashMap = new HashMap<Integer, Background>();

    public Biomes() {
        setNextTheme(DESSERT);
        jungleDessertBackground = new Background(JUNGLE_DESSERT);
        jungleDessertBackground.layer_1 = Assets.jungle_dessert_layer_1;
        jungleDessertBackground.layer_2 = Assets.jungle_dessert_layer_2;
        jungleDessertBackground.layer_3 = Assets.jungle_dessert_layer_3;
        jungleDessertBackground.layer_4 = Assets.jungle_dessert_layer_3;


        oceanDessertBackground = new Background(OCEAN_DESSERT);

        dessertBackground = new Background(DESSERT);
        dessertBackground.layer_1 = Assets.dessert_layer_1;
        dessertBackground.layer_2 = Assets.dessert_layer_2;
        dessertBackground.layer_3 = Assets.dessert_layer_3;
        dessertBackground.layer_4 = Assets.dessert_layer_3;

        dessertJungleBackground = new Background(DESSERT_JUNGLE);
        dessertJungleBackground.layer_1 = Assets.dessert_jungle_layer_1;
        dessertJungleBackground.layer_2 = Assets.dessert_jungle_layer_2;
        dessertJungleBackground.layer_3 = Assets.dessert_jungle_layer_3;
        dessertJungleBackground.layer_4 = Assets.dessert_jungle_layer_3;

        oceanJungleBackground = new Background(OCEAN_JUNGLE);

        jungleBackground = new Background(JUNGLE);
        jungleBackground.layer_1 = Assets.jungle_layer_1;
        jungleBackground.layer_2 = Assets.jungle_layer_2;
        jungleBackground.layer_3 = Assets.jungle_layer_3;
        jungleBackground.layer_4 = Assets.jungle_layer_4;
        jungleBackground.layerAmount = 4;

        jungleOceanBackground = new Background(JUNGLE_OCEAN);

        dessertOceanBackground = new Background(DESSERT_OCEAN);

        oceanBackground = new Background(OCEAN);

        integerBackgroundHashMap.put(JUNGLE_DESSERT, jungleDessertBackground);
        integerBackgroundHashMap.put(OCEAN_DESSERT, oceanDessertBackground);
        integerBackgroundHashMap.put(DESSERT, dessertBackground);
        integerBackgroundHashMap.put(DESSERT_JUNGLE, dessertJungleBackground);
        integerBackgroundHashMap.put(OCEAN_JUNGLE, oceanJungleBackground);
        integerBackgroundHashMap.put(JUNGLE, jungleBackground);
        integerBackgroundHashMap.put(JUNGLE_OCEAN, jungleOceanBackground);
        integerBackgroundHashMap.put(DESSERT_OCEAN, dessertOceanBackground);
        integerBackgroundHashMap.put(OCEAN, oceanBackground);

        for (int i = 1; i < 4; i++) {
            biomeLayer0.add(new BiomeLayer(4, i - 1, 0.6f));
            biomeLayer1.add(new BiomeLayer(3, i - 1, 0.5f));
            biomeLayer2.add(new BiomeLayer(2, i - 1, 0.4f));
            biomeLayer3.add(new BiomeLayer(1, i - 1, 0f));
        }

        layers.add(biomeLayer0);
        layers.add(biomeLayer1);
        layers.add(biomeLayer2);
        layers.add(biomeLayer3);

        forceTheme(DESSERT);    //TODO random
    }

    public void forceTheme(int theme) {
        for (Array<BiomeLayer> array : layers) {
            for (BiomeLayer biomeLayer : array) {
                biomeLayer.setTheme(theme);
            }
        }
    }

    public void setNextTheme(int nextTheme) {
        for (int i = 0; i < nextLayer.length; i++) {
            nextLayer[i] = nextTheme;
        }
    }

    // public void setNextTheme(Area.AREA nextTheme) {
    //     for (int i = 0; i < nextLayer.length; i++) {
    //         nextLayer[i] = nextTheme.transit(nextTheme)
    //     }
    // }

    public void update(float cavemanX, float cavemanY) {
        for (Array<BiomeLayer> array : layers) {
            for (BiomeLayer biomeLayer : array) {
                biomeLayer.update(cavemanX, cavemanY);
            }
        }
    }

    public void draw(SpriteBatch spriteBatch) {
        //int layerNr = 0;
        for (Array<BiomeLayer> array : layers) {
            //layerNr++;
            //System.out.println(layerNr);
            for (BiomeLayer biomeLayer : array) {
                if (biomeLayer.getLayer() <= integerBackgroundHashMap.get(biomeLayer.getTheme()).layerAmount)
                    try {
                        spriteBatch.draw(integerBackgroundHashMap.get(biomeLayer.getTheme()).getLayer(biomeLayer.getLayer()), biomeLayer.getX(), biomeLayer.getY()-1.25f, backgroundWidth, Core.BOX2D_VIRTUAL_HEIGHT);
                    } catch (NullPointerException e) {
                        System.out.println("-------------------------------------------------------------------------------");
                        System.out.println(biomeLayer);
                        System.out.println(biomeLayer.getLayer());
                        System.out.println(biomeLayer.getTheme());
                        System.out.println(biomeLayer.getX());
                        System.out.println(integerBackgroundHashMap.get(biomeLayer.getTheme()));
                        System.out.println(integerBackgroundHashMap.get(biomeLayer.getTheme()).getLayer(biomeLayer.getLayer()));
                        System.exit(1);
                    }
            }
        }
    }


    public class Background {
        public int theme;
        public int layerAmount = 3;
        public TextureRegion layer_1;
        public TextureRegion layer_2;
        public TextureRegion layer_3;
        public TextureRegion layer_4;

        public Background(int theme) {
            this.theme = theme;
        }

        public TextureRegion getLayer(int layer) {
            if (layer == 1) {
                return layer_1;
            } else if (layer == 2) {
                return layer_2;
            } else if (layer == 3) {
                return layer_3;
            } else {
                return layer_4;
            }
        }
    }
}
