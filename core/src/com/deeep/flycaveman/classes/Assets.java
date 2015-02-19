package com.deeep.flycaveman.classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 10/28/13
 * Time: 4:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class Assets {

    private static Random rand;

    public static AssetManager assetManager;

    public static boolean loaded = false;

    public static Skin skin;

    public static BitmapFont font, fontBig;

    public static TextureAtlas items;
    public static Texture darkSky;
    public static TextureRegion cavemanTexture, cavemanSprings, brachioTexture, quetzaTexture,
            smallEggTexture, staminaBackground, staminaFill, staminaBar, staminaHand,
            backgroundTexture, backgroundTexture2, restartButton, catapultArmTexture,
            catapultBaseTexture, homeButton, shopButton, staminaplus, shield, steroids,
            wings, springs, coin1, coin2, coin3, coin4, coin5, coin6, flapUp, dropUp, pauseUp;
    public static Animation cavemanWings, cavemanFlap;
    public static TextureRegion vodka, meat, soda, spinach;
    public static Sound hitGround1Sound, boing, hurt1, hurt2, hurt3, slurp, eat1, canOpen1, burp3,
            hitEntity1;
    public static Music music;

    public Assets() {
        assetManager = new AssetManager();
        loaded = false;
    }

    public static void load() {
        rand = new Random();

        darkSky = new Texture(Gdx.files.internal("darkness.png"));
        assetManager.load("data/loading.pack", TextureAtlas.class);
        assetManager.finishLoading();

        loadFont();
        loadAtlas();
        loadSounds();
    }

    private static void loadFont() {
        assetManager.load("data/font32.fnt", BitmapFont.class);
    }

    private static void loadAtlas() {
        assetManager.load("data/items.atlas", TextureAtlas.class);
    }

    private static void loadSounds() {
        assetManager.load("data/sounds/hitGround1.mp3", Sound.class);
        assetManager.load("data/sounds/boing.mp3", Sound.class);
        assetManager.load("data/sounds/hurt1.mp3", Sound.class);
        assetManager.load("data/sounds/hurt2.mp3", Sound.class);
        assetManager.load("data/sounds/hurt3.mp3", Sound.class);
        assetManager.load("data/sounds/slurp.mp3", Sound.class);
        assetManager.load("data/sounds/eat1.mp3", Sound.class);
        assetManager.load("data/sounds/canOpen1.mp3", Sound.class);
        assetManager.load("data/sounds/burp3.mp3", Sound.class);
        assetManager.load("data/sounds/hitEntity1.mp3", Sound.class);

        assetManager.load("data/sounds/music/presenta.wav", Music.class);
    }

    public static void set() {
        setSkin();
        setAtlas();
        setTextures();
        setSounds();
    }

    private static void setSkin() {
        skin = new Skin();

        font = assetManager.get("data/font32.fnt");

        skin.add("default-font", font, BitmapFont.class);

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
        backgroundTexture2 = items.findRegion("background2");
        restartButton = items.findRegion("restart");
        catapultArmTexture = items.findRegion("catapultArm");
        catapultBaseTexture = items.findRegion("catapultBase");
        homeButton = items.findRegion("home");
        shopButton = items.findRegion("shop");
        vodka = items.findRegion("vodka");
        spinach = items.findRegion("spinach");
        meat = items.findRegion("meat");
        soda = items.findRegion("sodacan");
        staminaBackground = items.findRegion("staminabarBackground");
        staminaFill = items.findRegion("staminafill");
        staminaBar = items.findRegion("staminabar");
        staminaHand = items.findRegion("staminahand");
        staminaplus = items.findRegion("staminaplus");
        shield = items.findRegion("shield");
        steroids = items.findRegion("steroids");
        wings = items.findRegion("wings");
        springs = items.findRegion("springshoes");

        coin1 = items.findRegion("Coin1");
        coin2 = items.findRegion("Coin2");
        coin3 = items.findRegion("Coin3");
        coin4 = items.findRegion("Coin4");
        coin5 = items.findRegion("Coin5");
        coin6 = items.findRegion("Coin6");

        cavemanWings = new Animation(0.1f, items.findRegion("cavemanWings1"), items.findRegion("cavemanWings2"));
        cavemanWings.setPlayMode(Animation.PlayMode.LOOP);

        cavemanFlap = new Animation(0.1f, items.findRegion("cavemanFlap1"), items.findRegion("cavemanFlap2"));
        cavemanFlap.setPlayMode(Animation.PlayMode.LOOP);

        cavemanSprings = items.findRegion("springJump");
        flapUp = items.findRegion("flapUp");
        dropUp = items.findRegion("dropUp");
        pauseUp = items.findRegion("pause");
    }

    private static void setSounds() {
        music = assetManager.get("data/sounds/music/presenta.wav");
        music.setLooping(true);
        music.setVolume(0.5f);

        hitGround1Sound = assetManager.get("data/sounds/hitGround1.mp3");
        boing = assetManager.get("data/sounds/boing.mp3");
        hurt1 = assetManager.get("data/sounds/hurt1.mp3");
        hurt2 = assetManager.get("data/sounds/hurt2.mp3");
        hurt3 = assetManager.get("data/sounds/hurt3.mp3");
        slurp = assetManager.get("data/sounds/slurp.mp3");
        eat1 = assetManager.get("data/sounds/eat1.mp3");
        canOpen1 = assetManager.get("data/sounds/canOpen1.mp3");
        burp3 = assetManager.get("data/sounds/burp3.mp3");
        hitEntity1 = assetManager.get("data/sounds/hitEntity1.mp3");
    }

    public static void dispose() {
        skin.dispose();
        assetManager.dispose();
    }

    public static void hitGround() {
        float random = rand.nextFloat();
        if (random >= 0.65f)
            hurt1.play();
        else if (random < 0.65f && random > 0.45f)
            hurt2.play();
        else
            hurt3.play();
    }
}