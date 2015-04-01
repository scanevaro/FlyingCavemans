package com.deeep.flycaveman.world;

import com.deeep.flycaveman.Core;
import com.deeep.flycaveman.entities.CaveMan;
import com.deeep.flycaveman.input.GameInputProcessor;
import com.deeep.flycaveman.widgets.FadeableMusic;
import com.deeep.flycaveman.widgets.SoundManager;

/**
 * Created by Elmar on 31-3-2015.
 */
public class MusicController {
    public static MusicController musicController;
    private SoundManager soundManager;
    FadeableMusic currentMusic, nextMusic;
    FadeableMusic spaceMusic, spaceNoise;
    FadeableMusic windNoise;
    private float startFadeOutDist = Area.biomesLength * 0.8f;
    private float endFadeInDist = Area.biomesLength * 0.2f;
    private static final float transitionDuration = 2f;
    private boolean firstLaunch = false;

    enum State {
        THEME, TRANSIT_THEME, GAME, TRANSIT_GAME;
    }


    State state = State.THEME;

    public MusicController() {
        soundManager = new SoundManager();
        soundManager.playMusic(soundManager.getMusic("DessertTheme").getMusicObject(), true);
        soundManager.playMusic(soundManager.getMusic("JungleTheme").getMusicObject(), true);
        soundManager.playMusic(soundManager.getMusic("ShopTheme").getMusicObject(), true);
        soundManager.playMusic(soundManager.getMusic("SpaceTheme").getMusicObject(), true);
        soundManager.playMusic(soundManager.getMusic("WindTheme").getMusicObject(), true);
        soundManager.playMusic(soundManager.getMusic("Theme").getMusicObject(), true);
        soundManager.playMusic(soundManager.getMusic("OceanTheme").getMusicObject(), true);
        soundManager.silence();
        currentMusic = soundManager.getMusic("Theme");
        windNoise = soundManager.getMusic("WindTheme");
        spaceNoise = soundManager.getMusic("WindTheme");
        spaceMusic = soundManager.getMusic("SpaceTheme");

        currentMusic.fadeIn(2, 1);
    }

    public void updateSoundManager(float deltaT) {
        soundManager.update(deltaT);
    }

    public void update(CaveMan caveMan) {
        float distance = 0;
        float height = 0;
        if (caveMan != null) {
            distance = caveMan.body.getPosition().x;
            height = caveMan.body.getPosition().y;
        }
        switch (state) {
            case THEME:
                if (GameInputProcessor.flying && !Core.dialogOpen) {
                    state = State.TRANSIT_GAME;
                    nextMusic = soundManager.getMusic(Area.currentArea.getMusic());
                    crossFade(transitionDuration);
                }
                break;
            case TRANSIT_THEME:
                if (isDoneTransitioning()) {
                    doneTransitioning();
                    state = State.THEME;
                } else if (GameInputProcessor.flying && !Core.dialogOpen) {
                    state = State.TRANSIT_GAME;
                    nextMusic = soundManager.getMusic(Area.currentArea.getMusic());
                    crossFade(transitionDuration);
                }
                break;
            case GAME:
                float tempDistance = distance % Area.biomesLength;
                if (tempDistance > startFadeOutDist) {
                    firstLaunch = false;
                    currentMusic = soundManager.getMusic(Area.currentArea.getMusic());
                    float ratio = 1 - (tempDistance - startFadeOutDist) / (Area.biomesLength - startFadeOutDist);
                    float windRatio = 1 - ratio;
                    currentMusic.setVolume(ratio);
                    windNoise.setVolume(windRatio);
                } else if (tempDistance < endFadeInDist) {
                    if (!firstLaunch) {
                        currentMusic = soundManager.getMusic(Area.currentArea.getMusic());
                        float ratio = (1 - (endFadeInDist - tempDistance) / (endFadeInDist));
                        float windRatio = 1 - ratio;
                        currentMusic.setVolume(ratio);
                        windNoise.setVolume(windRatio);
                    }else{
                        currentMusic.setVolume(1);
                    }
                }
                //TODO
                //if (height > Area.FULL_SPACE) {
                //    //at this point nothing should happen, full space ahead!
                //    spaceNoise.setVolume(0);
                //} else if (height >= Area.HALF_SPACE) {
                //    float spaceRatio = (height-Area.HALF_SPACE)/(Area.FULL_SPACE - Area.HALF_SPACE);
                //    spaceMusic.setVolume(spaceRatio);
                //    spaceNoise.setVolume(Math.max(1-spaceRatio, windNoise.getVolume()));
                //    System.out.println("space: "+ spaceRatio + " noise: "+ (1-spaceRatio));
                //} else if (height >= Area.BEGIN_SPACE) {
                //    float spaceRatio = (height-Area.BEGIN_SPACE)/(Area.HALF_SPACE - Area.BEGIN_SPACE);
                //    currentMusic.setVolume((1-spaceRatio) * currentMusic.getVolume());
                //    spaceNoise.setVolume(Math.max(spaceRatio, windNoise.getVolume()));
                //    System.out.println("space: "+ spaceRatio + " noise: "+ (spaceRatio));
                //}


                if (Core.dialogOpen || World.gameOver) {
                    windNoise.fadeOut(transitionDuration, 0);
                    state = State.TRANSIT_THEME;
                    nextMusic = soundManager.getMusic("Theme");
                    crossFade(transitionDuration);
                }
                break;
            case TRANSIT_GAME:
                if (isDoneTransitioning()) {
                    doneTransitioning();
                    firstLaunch = true;
                    state = State.GAME;
                } else if (Core.dialogOpen || World.gameOver) {
                    doneTransitioning();
                    state = State.TRANSIT_THEME;
                    nextMusic = soundManager.getMusic("Theme");
                    crossFade(transitionDuration);
                }
                break;
        }
    }

    private void crossFade(float duration) {
        currentMusic.fadeOut(duration, 0);
        nextMusic.fadeIn(duration, 1);
    }

    private boolean isDoneTransitioning() {
        return !nextMusic.isFadingIn() && !currentMusic.isFadingOut();
    }

    private void doneTransitioning() {
        currentMusic = nextMusic;
    }
}
