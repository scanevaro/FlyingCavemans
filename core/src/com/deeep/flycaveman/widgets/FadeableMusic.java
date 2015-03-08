package com.deeep.flycaveman.widgets;

import com.badlogic.gdx.audio.Music;

/**
 * Created by Andreas on 3/3/2015.
 */

public class FadeableMusic {

    //The original music file. Can be grabbed with getMusicObject()
    public Music music;

    private float volume = 0;

    private float quotient, product, speed, targetVolMax, targetVolMin;

    private volatile boolean isFadingIn, isFadingOut;

    private String name;

    /**
     * The purpose of the class is to allow easy fade in and fade out effects by creating an extension of the original Music object from
     * the LIBGDX.Music library.
     */
    public FadeableMusic(Music music, String name) {
        this.music = music;
        this.name = name;
        volume = music.getVolume();
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
        System.out.println(name + " fadeout at: " + speed + " target: " + targetVolMax + "[" + isFadingIn + "|" + isFadingOut + "]" + " current vol: " + volume);
    }

    public void fadeIn(float duration, float newVol) {
        this.isFadingIn = true;
        speed = (newVol - volume) / duration;
        targetVolMax = newVol;
        System.out.println(name + " fadein at: " + speed + " target: " + targetVolMax + "[" + isFadingIn + "|" + isFadingOut + "]" + " current vol: " + volume);
    }

    public void update(float delta) {
        if (isFadingOut) {
            System.out.println("fading out");
            volume -= speed * delta;
            if (volume <= targetVolMin) {
                volume = targetVolMin;
                isFadingOut = false;
                System.out.println("    | Done fading out since");
                System.out.println("    | " + volume + " <= " + targetVolMin);
            }
            music.setVolume(volume);
        }
        if (isFadingIn) {
            volume += speed * delta;
            if (volume >= targetVolMax) {
                volume = targetVolMax;
                isFadingIn = false;
                System.out.println("    | Done fading in since");
                System.out.println("    | " + volume + " >= " + targetVolMin);
            }
            music.setVolume(volume);
        }

    }

    public Music getMusicObject() {
        return music;
    }

    public String toString() {
        return name;
    }

}