package com.deeep.flycaveman.classes;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

/**
 * Created by Andreas on 2/25/2015.
 */
public class SoundManager {

    public SoundManager() {
    }

    public void playSound(Sound sound){
        sound.play();
    }

    public void loopSound(Sound sound){
        sound.loop();
    }

    public void playMusic(Music music, boolean loop){
        if(loop){
            if(!music.isLooping()) music.setLooping(true);
        }
        music.setVolume(0.5f);
        if(!music.isPlaying()) music.play();
    }

    public Music getMusic(String music){
        return SoundLibrary.getInstance().getMusicFromString(music);
    }

    public void stopSound(Sound sound){
        sound.stop();
    }

    public void stopMusic(Music music){
        music.stop();
    }
}

class SoundLibrary {
    public static SoundLibrary getInstance(){
        return new SoundLibrary();
    }

    public static Music shopMusic;
    public static Music jungleMusic;
    public static Music spaceMusic;

    public SoundLibrary(){
        this.shopMusic = Assets.loadMusicFile("ShopTheme");
        this.jungleMusic = Assets.loadMusicFile("JungleTheme");
        this.spaceMusic = Assets.loadMusicFile("SpaceTheme");
    }

    public static Music getMusicFromString(String s){
        if(s == "ShopTheme") return shopMusic;
        if(s == "JungleTheme") return jungleMusic;
        if(s == "SpaceTheme") return spaceMusic;
        return null;
    }
}