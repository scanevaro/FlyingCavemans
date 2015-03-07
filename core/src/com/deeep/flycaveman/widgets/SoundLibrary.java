package com.deeep.flycaveman.widgets;

/**
 * Created by Andreas on 3/3/2015.
 */

import com.deeep.flycaveman.Assets;

import java.util.ArrayList;

/**
 * Library class for handling the retrieval of sound files from the assets manager using strings as identifiers
 */
public class SoundLibrary {
    private static SoundLibrary soundLibrary;

    public static SoundLibrary getInstance() {
        if (soundLibrary == null)
            soundLibrary = new SoundLibrary();
        return soundLibrary;
    }

    public static FadeableMusic shopMusic;
    public static FadeableMusic jungleMusic;
    public static FadeableMusic spaceMusic;
    public static FadeableMusic windMusic;
    public static FadeableMusic dessertMusic;

    public static ArrayList<FadeableMusic> musicList;

    public SoundLibrary() {
        this.shopMusic = new FadeableMusic(Assets.loadMusicFile("ShopTheme"), "ShopTheme");
        this.jungleMusic = new FadeableMusic(Assets.loadMusicFile("JungleTheme"), "JungleTheme");
        this.spaceMusic = new FadeableMusic(Assets.loadMusicFile("SpaceTheme"), "SpaceTheme");
        this.dessertMusic = new FadeableMusic(Assets.loadMusicFile("DessertTheme"), "DessertTheme");
        this.windMusic = new FadeableMusic(Assets.loadMusicFile("WindTheme"), "WindTheme");
        musicList = new ArrayList<FadeableMusic>();
        musicList.add(shopMusic);
        musicList.add(jungleMusic);
        musicList.add(spaceMusic);
        musicList.add(dessertMusic);
        musicList.add(windMusic);
    }

    public static FadeableMusic getMusicFromString(String s) {
        for (FadeableMusic m : musicList) {
            if (s.equals(m.toString())) {
                return m;
            }
        }
        return null;
    }
}
