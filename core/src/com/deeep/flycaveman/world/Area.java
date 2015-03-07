package com.deeep.flycaveman.world;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.deeep.flycaveman.Core;
import com.deeep.flycaveman.classes.Biomes;
import com.deeep.flycaveman.widgets.SoundManager;

/**
 * Created by Elmar on 8-2-2015.
 */
public class Area {
    private float x;
    private Biomes biomes;
    private int someCounter = 0;
    private SoundManager soundManager;

    public Area() {
        biomes = new Biomes();
        soundManager = new SoundManager();

        soundManager.playMusic(soundManager.getMusic("DessertTheme").getMusicObject(), true);
        soundManager.playMusic(soundManager.getMusic("JungleTheme").getMusicObject(), true);

        soundManager.silence();

        soundManager.playMusic(soundManager.getMusic("DessertTheme").getMusicObject(), true);
    }

    public void update(Body caveman) {
        //todo make this the camera, no1 gives a shit about the caveman5
        x = caveman.getPosition().x;
        if (x - someCounter >= 500) {
            if (!biomes.isTransitioning()) {
                someCounter += 500;
                if (biomes.getCurrentBiome(true) == Biomes.DESSERT) {
                    biomes.setNextTheme(Biomes.DESSERT_JUNGLE);
                    System.out.println("To jungle! and beyond?");
                    soundManager.silence();
                    soundManager.playMusic(soundManager.getMusic("JungleTheme").getMusicObject(), true);
                } else {
                    biomes.setNextTheme(Biomes.JUNGLE_DESSERT);
                    System.out.println("To dessert! and beyond!");
                    soundManager.silence();
                    soundManager.playMusic(soundManager.getMusic("DessertTheme").getMusicObject(), true);
                }
            } else {
                System.out.println("not so quickly aye?");
            }
        }
        biomes.update(x);
    }

    public void draw(SpriteBatch spriteBatch) {
        biomes.draw(spriteBatch);
    }
}
