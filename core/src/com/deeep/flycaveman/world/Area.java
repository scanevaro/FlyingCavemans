package com.deeep.flycaveman.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.deeep.flycaveman.widgets.FadeableMusic;
import com.deeep.flycaveman.widgets.SoundManager;

/**
 * Created by Elmar on 8-2-2015.
 */
public class Area {
    public static final int BEGIN_SPACE = 60;
    public static final int HALF_SPACE = 80;
    public static final int FULL_SPACE = 120;
    public static final int MAX_SPACE = 160; // for lowest gravity
    public enum AREA {
        DESSERT(Biomes.DESSERT, "DessertTheme"), JUNGLE(Biomes.DESSERT, "JungleTheme"), OCEAN(Biomes.OCEAN, "OceanTheme");//"OceanTheme");

        private int biomesCode;
        private String music; // todo make use of FadeableMusic class

        AREA(int biomesCode, String music) {
            this.biomesCode = biomesCode;
            this.music = music;
        }

        public static void test() {
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
    public SoundManager soundManager;
    private AREA currentArea;
    private AREA nextArea;
    private FadeableMusic currentMusic;
    private FadeableMusic nextMusic;
    private FadeableMusic windMusic;
    private FadeableMusic spaceMusic;
    private boolean firstStart = true;
    private boolean windStart = false;
    private Weather weather;

    public static float biomesLength = 1000;

    private boolean inSpace = false;

    private int previousX = -1;

    private float currentMusicSoundLevel = 0;
    private float nextMusicSoundLevel = 0;
    private float spaceFade = 0;

    private boolean themeSongPlaying = true;

    public Area() {
        biomes = new Biomes();
        soundManager = new SoundManager();

        soundManager.playMusic(soundManager.getMusic("DessertTheme").getMusicObject(), true);
        soundManager.playMusic(soundManager.getMusic("JungleTheme").getMusicObject(), true);
        soundManager.playMusic(soundManager.getMusic("ShopTheme").getMusicObject(), true);
        soundManager.playMusic(soundManager.getMusic("SpaceTheme").getMusicObject(), true);
        soundManager.playMusic(soundManager.getMusic("WindTheme").getMusicObject(), true);
        soundManager.playMusic(soundManager.getMusic("Theme").getMusicObject(), true);
        soundManager.playMusic(soundManager.getMusic("OceanTheme").getMusicObject(), true);

        soundManager.silence();
        firstStart = true;
        currentMusicSoundLevel = 1;
        currentArea = AREA.DESSERT;
        nextArea = getRandomArea(currentArea);
        //currentMusic = soundManager.getMusic("Theme");
        currentMusic = soundManager.getMusic(currentArea.music);
        nextMusic = soundManager.getMusic(nextArea.music);
        windMusic = soundManager.getMusic("WindTheme");
        spaceMusic = soundManager.getMusic("SpaceTheme");
        windMusic.setVolume(0);
        spaceMusic.setVolume(0);
        currentMusic.setVolume(0);
        currentMusic.fadeIn(5f, 1);
        nextMusicSoundLevel = 0;
        weather = new Weather();
        System.out.println("created");
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

        if (nextMusic != null) {
            if (nextMusicSoundLevel >= 0.95f) {
                this.currentMusic = nextMusic;
            }
        }
        if (currentMusicSoundLevel <= 0) {
            float nextVolume = biomePosition / (biomesLength * 0.2f);
            nextMusicSoundLevel = nextVolume;
        }
        if (biomePosition >= (biomesLength * 0.8f)) {   //larger than 80%
            float fade = 1 - (biomePosition - biomesLength * 0.8f) / (biomesLength * 0.2f);
            currentMusicSoundLevel = fade;
            if (camera.x > 100) //not on the catapult we dont want to hear this. TODO make this nicer
                windMusic.setVolume(1 - fade);
            windStart = true;
        } else {
            float fade = 1 - (biomePosition - biomesLength * 0.1f) / (biomesLength * 0.1f);
            if (fade > 0)
                if (windStart)
                    windMusic.setVolume(fade);
        }


        if (biomePosition >= biomesLength) {
            currentMusicSoundLevel = 0;
            biomes.setNextTheme(currentArea.transit(currentArea, nextArea));
            currentArea = nextArea;
            nextMusic = soundManager.getMusic(nextArea.music);
            nextArea = getRandomArea(currentArea);
            someCounter += biomesLength;
        }
        if (!firstStart) {
            currentMusic.setVolume(Math.min(spaceFade, currentMusicSoundLevel));
            nextMusic.setVolume(Math.min(spaceFade, nextMusicSoundLevel));
        } else {
            if (currentMusic.getVolume() >= 0.9f) {
                firstStart = false;
            }
            windMusic.setVolume(0);
        }
        soundManager.update(Gdx.graphics.getDeltaTime());
        updateSpaceMusic(camera);
        biomes.update(camera.x, camera.y);
        //40 - 80
    }

    public void updateSpaceMusic(Vector3 camera) {
        //80 = 0;
        //40 = 0;
        //60 = 1;
        /*
        caveman.y - 40/60. if>1, 1-%fade
         */
        if (camera.y > BEGIN_SPACE) {
            float fadeWind = 0;
            float fadeSpace = 0;
            if (camera.y < FULL_SPACE) {
                fadeWind = ((camera.y - BEGIN_SPACE) / (HALF_SPACE - BEGIN_SPACE));
                if (camera.y < HALF_SPACE) {
                    spaceFade = (Math.max(1 - fadeWind, 0.01f));
                } else {
                    spaceFade = 0;
                    currentMusic.setVolume(0.01f);
                }
                if (fadeWind > 1) {
                    fadeSpace = fadeWind - 1;
                    fadeWind = 1 - fadeWind;
                    spaceMusic.setVolume(fadeSpace);
                }
                windMusic.setVolume(fadeWind);
            } else {
                inSpace = true;
                spaceFade = 0;
                spaceMusic.setVolume(1);
            }
        } else {
            spaceMusic.setVolume(0);
            spaceFade = 1;
        }
    }

    public void draw(SpriteBatch spriteBatch) {
        //biomes.draw(spriteBatch);
        for (int i = 0; i < 4; i++) {
            weather.draw(spriteBatch, i);
            biomes.draw(spriteBatch, i);
        }
    }
}
