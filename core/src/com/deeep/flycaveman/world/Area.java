package com.deeep.flycaveman.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Elmar on 8-2-2015.
 */
public class Area {
    public static final int BEGIN_SPACE = 220;   //fadeout music
    public static final int HALF_SPACE = 360;    //fade in space music
    public static final int FULL_SPACE = 440; //music and colouring
    public static final int MAX_SPACE = 520; // for lowest gravity

    public enum AREA {
        DESSERT(Biomes.DESSERT, "DessertTheme"), JUNGLE(Biomes.DESSERT, "JungleTheme"), OCEAN(Biomes.OCEAN, "OceanTheme");//"OceanTheme");

        private int biomesCode;
        private String music; // todo make use of FadeableMusic class

        AREA(int biomesCode, String music) {
            this.biomesCode = biomesCode;
            this.music = music;
        }

        public String getMusic() {
            return music;
        }

        public int transit(AREA from, AREA to) {
            switch (from) { //todo from == this
                case DESSERT:
                    switch (to) {
                        case JUNGLE:
                            return Biomes.DESSERT_JUNGLE;
                        case OCEAN:
                            return Biomes.DESSERT_OCEAN;
                    }
                    break;
                case JUNGLE:
                    switch (to) {
                        case DESSERT:
                            return Biomes.JUNGLE_DESSERT;
                        case OCEAN:
                            return Biomes.JUNGLE_OCEAN;
                    }
                    break;
                case OCEAN:
                    switch (to) {
                        case DESSERT:
                            return Biomes.OCEAN_DESSERT;
                        case JUNGLE:
                            return Biomes.OCEAN_JUNGLE;
                    }
            }
            return biomesCode;
        }
    }

    private Biomes biomes;
    private int someCounter = 0;
    public static AREA currentArea;
    public static AREA nextArea;
    private Weather weather;

    public static float biomesLength = 1000;

    private boolean themeSongPlaying = true;
    public static boolean songPlaying = false;

    public Area() {
        biomes = new Biomes();
        currentArea = AREA.DESSERT;
        nextArea = getRandomArea(currentArea);

        weather = new Weather();
    }

    public AREA getRandomArea(AREA current) {
        if (current == AREA.DESSERT) {
            return AREA.OCEAN;
        } else if (current == AREA.OCEAN) {
            return AREA.JUNGLE;
        } else if (current == AREA.JUNGLE) {
            return AREA.DESSERT;
        }
        return AREA.DESSERT;
    }

    public void update(Vector3 camera) {
        weather.update(camera);
        float biomePosition = camera.x - someCounter;
        if (biomePosition >= biomesLength) {
            biomes.setNextTheme(currentArea.transit(currentArea, nextArea));
            currentArea = nextArea;
            nextArea = getRandomArea(currentArea);
            someCounter += biomesLength;
        }
        biomes.update(camera.x, camera.y);
        //40 - 80
    }


    public void draw(SpriteBatch spriteBatch) {
        //biomes.draw(spriteBatch);
        for (int i = 0; i < 4; i++) {
            weather.draw(spriteBatch, i);
            biomes.draw(spriteBatch, i);
        }
    }
}
