package com.deeep.flycaveman.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.deeep.flycaveman.classes.Biomes;
import com.deeep.flycaveman.widgets.FadeableMusic;
import com.deeep.flycaveman.widgets.SoundManager;

/**
 * Created by Elmar on 8-2-2015.
 */
public class Area {
    public enum AREA {
        DESSERT(Biomes.DESSERT, "DessertTheme"), JUNGLE(Biomes.DESSERT, "JungleTheme"), OCEAN(Biomes.OCEAN, "OceanTheme");

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


    public static float biomesLength = 500;

    private boolean inSpace = false;

    private float currentMusicSoundLevel = 0;
    private float nextMusicSoundLevel = 0;
    private float spaceFade = 0;

    public Area() {
        biomes = new Biomes();
        soundManager = new SoundManager();

        soundManager.playMusic(soundManager.getMusic("DessertTheme").getMusicObject(), true);
        soundManager.playMusic(soundManager.getMusic("JungleTheme").getMusicObject(), true);
        soundManager.playMusic(soundManager.getMusic("ShopTheme").getMusicObject(), true);
        soundManager.playMusic(soundManager.getMusic("SpaceTheme").getMusicObject(), true);
        soundManager.playMusic(soundManager.getMusic("WindTheme").getMusicObject(), true);

        soundManager.silence();
        soundManager.getMusic("DessertTheme").fadeIn(5, 1f);
        currentMusicSoundLevel = 1;
        currentArea = AREA.DESSERT;
        nextArea = getRandomArea(currentArea);
        currentMusic = soundManager.getMusic(currentArea.music);
        nextMusic = soundManager.getMusic(nextArea.music);
        windMusic = soundManager.getMusic("WindTheme");
        spaceMusic = soundManager.getMusic("SpaceTheme");
        windMusic.setVolume(0);
        spaceMusic.setVolume(0);
        nextMusicSoundLevel = 0;
    }

    public AREA getRandomArea(AREA current) {
        if (current == AREA.DESSERT) {
            return AREA.JUNGLE;
        }
        return AREA.DESSERT;
    }

    public void update(Body caveman) {
        //todo make this the camera, no1 gives a shit about the caveman5
        float biomePosition = caveman.getPosition().x - someCounter;

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
            if (caveman.getPosition().x > 100) //not on the catapult we dont want to hear this. TODO make this nicer
                windMusic.setVolume(1 - fade);
        } else {
            float fade = 1 - (biomePosition - biomesLength * 0.1f) / (biomesLength * 0.1f);
            if (fade > 0)
                if (caveman.getPosition().x > 100) //not on the catapult we dont want to hear this. TODO make this nicer
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
        currentMusic.setVolume(Math.min(spaceFade, currentMusicSoundLevel));
        nextMusic.setVolume(Math.min(spaceFade, nextMusicSoundLevel));

        soundManager.update(Gdx.graphics.getDeltaTime());
        updateSpaceMusic(caveman);
        biomes.update(caveman.getPosition().x, caveman.getPosition().y);
        //40 - 80
    }

    public void updateSpaceMusic(Body caveman) {
        //80 = 0;
        //40 = 0;
        //60 = 1;
        /*
        caveman.y - 40/60. if>1, 1-%fade
         */
        if (caveman.getPosition().y > 40) {
            float fadeWind = 0;
            float fadeSpace = 0;
            if (caveman.getPosition().y < 80) {
                fadeWind = ((caveman.getPosition().y - 40) / 20);
                if (caveman.getPosition().y < 60) {
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
        biomes.draw(spriteBatch);
    }
}
