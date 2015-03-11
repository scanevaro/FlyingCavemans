package com.deeep.flycaveman.widgets;

import com.badlogic.gdx.audio.Music;

/**
 * Created by Andreas on 3/3/2015.
 */

public class FadeableMusic {

    //The original music file. Can be grabbed with getMusicObject()
    public Music music;

    private float volume = 0;

    private float speed, targetVolMax, targetVolMin;

    private float maxVolume = 1;

    private volatile boolean isFadingIn, isFadingOut;

    private String name;

    public FadeableMusic() {
    }

    /**
     * The purpose of the class is to allow easy fade in and fade out effects by creating an extension of the original Music object from
     * the LIBGDX.Music library.
     */
    public FadeableMusic(Music music, String name) {
        this.music = music;
        this.name = name;
        volume = music.getVolume();
        music.setVolume(0);
    }

    public FadeableMusic(Music music, String name, float maxVolume) {
        this.music = music;
        this.name = name;
        volume = music.getVolume();
        this.maxVolume = maxVolume;
        music.setVolume(0);
    }

    public float getVolume() {
        return volume;
    }

    public boolean isFadingOut() {
        return isFadingOut;
    }

    public boolean isFadingIn() {
        return isFadingIn;
    }

    public void fadeOut(float duration, float newVol) {
        this.isFadingOut = true;
        speed = Math.abs((newVol - volume) / duration);
        targetVolMin = newVol;
    }

    public void fadeIn(float duration, float newVol) {
        this.isFadingIn = true;
        speed = (newVol - volume) / duration;
        targetVolMax = newVol;
    }

    public void update(float delta) {
        if (isFadingOut) {
            volume -= speed * delta;
            if (volume <= targetVolMin) {
                volume = targetVolMin;
                isFadingOut = false;
            }
            music.setVolume(volume * maxVolume);
        }
        if (isFadingIn) {
            volume += speed * delta;
            if (volume >= targetVolMax) {
                volume = targetVolMax;
                isFadingIn = false;
            }
            music.setVolume(volume * maxVolume);
        }
    }

    public Music getMusicObject() {
        return music;
    }

    public String toString() {
        return name;
    }

    public void setVolume(float volume) {
        if (this.volume == volume) return;
        this.volume = volume;
        music.setVolume(volume * maxVolume);
    }
}