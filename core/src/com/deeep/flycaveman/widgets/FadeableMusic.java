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

    private boolean isFadingIn, isFadingOut;

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

    public boolean isFadingOut() {
        return isFadingOut;
    }

    public boolean isFadingIn() {
        return isFadingIn;
    }

    public void fadeOut(float duration, float newVol) {
        if (isFadingIn) {
            System.out.println("Make up your mind! isFadingOut == 1");
            return;
        }
        float currentVol = volume;
        if (newVol > currentVol) {
            System.out.println("oi? fade out cant! newVol>oldVol");
            return;
        }
        isFadingOut = true;
        speed = Math.abs((newVol - currentVol) / duration);
        targetVolMin = newVol;
        System.out.println(music);
        System.out.println("fadeout at: " + speed + " target: " + targetVolMax + " isFadingIn: " + isFadingIn + " current vol: " + currentVol);
    }

    public void fadeIn(float duration, float newVol) {
        if (isFadingOut) {
            System.out.println("Make up your mind! isFadingOut == 1");
            return;
        }
        float currentVol = volume;
        if (newVol < currentVol) {
            System.out.println("oi? fade in cant! newVol<oldVol");
            return;
        }
        isFadingIn = true;
        speed = (newVol - currentVol) / duration;
        targetVolMax = newVol;
        System.out.println(music);
        System.out.println("fadein at: " + speed + " target: " + targetVolMax + " isFadingOut: " + isFadingOut + " current vol: " + currentVol);
    }

    public void update(float delta) {
        if (isFadingOut) {
            System.out.println("fading out");
            volume -= speed * delta;
            if (volume <= targetVolMin) {
                volume = targetVolMin;
                isFadingOut = false;
                System.out.println("done fading out");
            }
        }
        if (isFadingIn) {
            volume += speed * delta;
            if (volume >= targetVolMax) {
                volume = targetVolMax;
                isFadingIn = false;
                System.out.println("done fading in");
            }
        }
        music.setVolume(volume);
    }

    public Music getMusicObject() {
        return music;
    }

    public String toString() {
        return name;
    }

}