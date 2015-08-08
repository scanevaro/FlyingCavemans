package com.deeep.flycaveman.widgets;

/**
 * Created by Andreas on 3/3/2015.
 */

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
    public static FadeableMusic spaceNoise;
    public static FadeableMusic windMusic;
    public static FadeableMusic dessertMusic;
    public static FadeableMusic themeMusic;
    public static FadeableMusic oceanMusic;
    public static FadeableMusic introMusic;

    public static ArrayList<FadeableMusic> musicList;

    public SoundLibrary() {
//        this.shopMusic = new FadeableMusic(Assets.loadMusicFile("ShopTheme"), "ShopTheme", 0.2f);
//        this.jungleMusic = new FadeableMusic(Assets.loadMusicFile("JungleTheme"), "JungleTheme", 0.35f);
//        this.spaceMusic = new FadeableMusic(Assets.loadMusicFile("SpaceTheme"), "SpaceTheme", 0.3f);
//        this.dessertMusic = new FadeableMusic(Assets.loadMusicFile("DessertTheme"), "DessertTheme", 0.35f);
//        this.windMusic = new FadeableMusic(Assets.loadMusicFile("WindTheme"), "WindTheme", 1f);
//        this.themeMusic = new FadeableMusic(Assets.loadMusicFile("Theme"), "Theme", 0.5f);
//        this.oceanMusic = new FadeableMusic(Assets.loadMusicFile("OceanTheme"), "OceanTheme", 0.5f);
//        this.introMusic = new FadeableMusic(Assets.loadMusicFile("introTheme"), "introTheme", 0.5f);
//        this.spaceNoise = new FadeableMusic(Assets.loadMusicFile("WindTheme"), "SpaceNoise", 1f);
//        musicList = new ArrayList<FadeableMusic>();
//        musicList.add(shopMusic);
//        musicList.add(jungleMusic);
//        musicList.add(spaceMusic);
//        musicList.add(dessertMusic);
//        musicList.add(windMusic);
//        musicList.add(themeMusic);
//        musicList.add(oceanMusic);
//        musicList.add(introMusic);
//        musicList.add(spaceNoise);
    }

//    public static FadeableMusic getMusicFromString(String s) {
//        for (FadeableMusic m : musicList) {
//            if (s.equals(m.toString())) {
//                return m;
//            }
//        }
//        return null;
//    }
}
