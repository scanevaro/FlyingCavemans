package com.deeep.flycaveman.classes;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

/**
 * Created by Andreas on 2/25/2015.
 */
public class SoundManager {

    public SoundManager() {
    }

    /**
     * Plays a sound using the LIBGDX sound library
     * @param sound Sound to be played
     */
    public void playSound(Sound sound){
        sound.play();
    }

    /**
     * Loops a sound using the LIBGDX sound library
     * @param sound Sound to be looped
     */
    public void loopSound(Sound sound){
        sound.loop();
    }

    /**
     * Plays or loops a music file using the LIBGDX music library
     * @param music Music to be played/looped
     * @param loop Decides to loop or not
     */
    public void playMusic(Music music, boolean loop){
        if(loop){
            if(!music.isLooping()) music.setLooping(true);
        }
        music.setVolume(0.5f);
        if(!music.isPlaying()) music.play();
    }

    /**
     * Get music from the library with string as identifier
     * @param music identifier string
     * @return music object file
     */
    public Music getMusic(String music){
        return SoundLibrary.getInstance().getMusicFromString(music);
    }

    /**
     * Stops a sound from playing
     * @param sound object to stop
     */
    public void stopSound(Sound sound){
        sound.stop();
    }

    /**
     * Stops a music object from playing
     * @param music object to stop
     */
    public void stopMusic(Music music){
        music.stop();
    }
}

/**
 * Library class for handling the retrieval of sound files from the assets manager using strings as identifiers
 */
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