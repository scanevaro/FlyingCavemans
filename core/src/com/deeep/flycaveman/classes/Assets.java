package com.deeep.flycaveman.classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 10/28/13
 * Time: 4:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class Assets {

    public static AssetManager assetManager;

    public static boolean loaded = false;

    public static Skin skin;

    public static BitmapFont font, fontBig;

    public static TextureAtlas items;
    public static TextureRegion cavemanTexture, brachioTexture, quetzaTexture, smallEggTexture,
            backgroundTexture, restartButton, catapultArmTexture, catapultBaseTexture;
    public static Sound hitGround1Sound;
    public static Music music;

    public Assets() {
        assetManager = new AssetManager();
        loaded = false;
    }

    public static void load() {
        assetManager.load("data/loading.pack", TextureAtlas.class);
        assetManager.finishLoading();

        loadAtlas();
        loadSounds();
    }

    private static void loadAtlas() {
        assetManager.load("data/items.atlas", TextureAtlas.class);
    }

    private static void loadSounds() {
        assetManager.load("data/sounds/hitGround1.mp3", Sound.class);
        assetManager.load("data/sounds/music/presenta.mp3", Music.class);
    }

    public static void set() {
        setSkin();
        setAtlas();
        setTextures();
        setSounds();
    }

    private static void setSkin() {
        skin = new Skin();

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("data/comic.ttf"));
        font = generator.generateFont(40);
        fontBig = generator.generateFont(65);

        skin.add("default-font", font, BitmapFont.class);
        skin.add("big-font", fontBig, BitmapFont.class);

        generator.dispose();

        FileHandle fileHandle = Gdx.files.internal("data/items.json");
        FileHandle atlasFile = fileHandle.sibling("items.atlas");
        if (atlasFile.exists()) {
            skin.addRegions(new TextureAtlas(atlasFile));
        }
        skin.load(fileHandle);
    }

    private static void setAtlas() {
        items = assetManager.get("data/items.atlas");
    }

    private static void setTextures() {
        cavemanTexture = items.findRegion("caveman");
        brachioTexture = items.findRegion("BRACHIOSAURUS");
        quetzaTexture = items.findRegion("QUETZA");
        smallEggTexture = items.findRegion("smallEgg");
        backgroundTexture = items.findRegion("background");
        restartButton = items.findRegion("restart");
        catapultArmTexture = items.findRegion("catapultArm");
        catapultBaseTexture = items.findRegion("catapultBase");
    }

    private static void setSounds() {
        music = assetManager.get("data/sounds/music/presenta.mp3");
        music.setLooping(true);
        music.setVolume(0.5f);

        hitGround1Sound = assetManager.get("data/sounds/hitGround1.mp3");
    }

    public static void dispose() {
        skin.dispose();
        assetManager.dispose();
    }
}