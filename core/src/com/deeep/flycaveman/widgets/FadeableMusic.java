package com.deeep.flycaveman.widgets;

import com.badlogic.gdx.audio.Music;

/**
 * Created by Andreas on 3/3/2015.
 */

public class FadeableMusic {

    //The original music file. Can be grabbed with getMusicObject()
    public Music music;

    private float quotient, product, speed, maxVol;

    private boolean isFadingIn, isFadingOut;

    private String name;

    /**
     * The purpose of the class is to allow easy fade in and fade out effects by creating an extension of the original Music object from
     * the LIBGDX.Music library.
     */
    public FadeableMusic(Music music, String name){
        this.music = music;
        this.name = name;
    }

    public boolean isFadingOut(){
        return isFadingOut;
    }

    public boolean isFadingIn(){
        return isFadingIn;
    }

    public void fadeOut(float speed){
        if(music.getVolume() == 0 || isFadingIn) return;
        isFadingOut = true;
        quotient = 1F;
        this.speed = speed;
        maxVol = music.getVolume();
    }

    public void fadeIn(float speed, float maxVol){
        if(music.getVolume() != 0) return;
        isFadingIn = true;
        product = 0F;
        this.speed = speed;
    }

    public void update(float delta){
        if(isFadingOut || isFadingOut){
            music.setVolume(music.getVolume() * quotient);
            quotient -= speed * delta;
            if(music.getVolume() < 0.05){
                music.setVolume(0);
                isFadingOut = false;
            }
        }
        if(isFadingIn){
            music.setVolume(music.getVolume() * product);
            product += speed * delta;
            if(music.getVolume() >= maxVol){
                music.setVolume(maxVol);
                isFadingIn = false;
            }
        }
    }

    public Music getMusicObject(){
        return music;
    }

    public String toString(){
        return name;
    }

}