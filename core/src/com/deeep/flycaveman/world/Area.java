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

    private Biomes biomes;
    private int someCounter = 0;
    private SoundManager soundManager;

    private FadeableMusic currentMusic;
    private FadeableMusic nextMusic;

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
    }

    public void update(Body caveman) {
        //todo make this the camera, no1 gives a shit about the caveman5
        float x = caveman.getPosition().x;
        soundManager.update(Gdx.graphics.getDeltaTime());
        if (x - someCounter >= 500) {
            if (!biomes.isTransitioning()) {
                someCounter += 500;
                if (biomes.getCurrentBiome(true) == Biomes.DESSERT) {
                    biomes.setNextTheme(Biomes.DESSERT_JUNGLE);
                    System.out.println("To jungle! and beyond?");
                    soundManager.getMusic("DessertTheme").fadeOut(5f, 0);
                    soundManager.getMusic("JungleTheme").fadeIn(5f, 1);
                } else {
                    biomes.setNextTheme(Biomes.JUNGLE_DESSERT);
                    System.out.println("To dessert! and beyond!");
                    soundManager.getMusic("DessertTheme").fadeIn(5f, 1);
                    soundManager.getMusic("JungleTheme").fadeOut(5f, 0);
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
