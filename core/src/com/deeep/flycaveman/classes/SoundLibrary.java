package com.deeep.flycaveman.classes;

/**
 * Created by Andreas on 3/3/2015.
 */

import java.util.ArrayList;

/**
 * Library class for handling the retrieval of sound files from the assets manager using strings as identifiers
 */
public class SoundLibrary {
    public static SoundLibrary getInstance(){
        return new SoundLibrary();
    }

    public static FadeableMusic shopMusic;
    public static FadeableMusic jungleMusic;
    public static FadeableMusic spaceMusic;

    public static ArrayList<FadeableMusic> musicList;

    public SoundLibrary(){
        this.shopMusic = new FadeableMusic(Assets.loadMusicFile("ShopTheme"));
        this.jungleMusic = new FadeableMusic(Assets.loadMusicFile("JungleTheme"));
        this.spaceMusic = new FadeableMusic(Assets.loadMusicFile("SpaceTheme"));
        musicList = new ArrayList<FadeableMusic>();
        musicList.add(shopMusic);
        musicList.add(jungleMusic);
        musicList.add(spaceMusic);
    }

    public static FadeableMusic getMusicFromString(String s){
        if(s == "ShopTheme") return shopMusic;
        if(s == "JungleTheme") return jungleMusic;
        if(s == "SpaceTheme") return spaceMusic;
        return null;
    }
}
