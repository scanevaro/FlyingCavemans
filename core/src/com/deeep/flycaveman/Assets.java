package com.deeep.flycaveman;

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

    public static BitmapFont font64, font32, font24Bold;

    public static TextureAtlas items;
    public static Texture darkSky;
    public static TextureRegion cloud1, cloud2, cloud3;
    public static TextureRegion cavemanTexture, cavemanSprings, brachioTexture, brachioBackPull, brachioFrontPull,
            brachioMidPull, quetzaTexture, smallEggTexture, smallEggBroken, staminaBackground, staminaFill, staminaBar,
            staminaHand, restartButton, catapultArmTexture, catapultBaseTexture, homeButton, shopButton, staminaplus,
            steroids, wings, springs, coin1, coin2, coin3, coin4, coin5, coin6, flapUp, dropUp, pauseUp, quetzaHit,
            mosquitoTexture, mosquitoHit, sabertooth1, sabertooth2, faceBackground, faceHappy, facePain, faceTired,
            facePassion, faceKO, title, touchNH, buttonBroken, button, dialog, buyButton, buyButtonDown, starBlack,
            starBright;
    public static Animation cavemanWings, cavemanFlap;

    public static TextureRegion dessert_layer_1, dessert_layer_2, dessert_layer_3;  //todo put this in an enum or so
    public static TextureRegion jungle_layer_1, jungle_layer_2, jungle_layer_3, jungle_layer_4;  //todo put this in an enum or so
    public static TextureRegion jungle_dessert_layer_1, jungle_dessert_layer_2, jungle_dessert_layer_3;  //todo put this in an enum or so
    public static TextureRegion jungle_ocean_layer_1, jungle_ocean_layer_2, jungle_ocean_layer_3;  //todo put this in an enum or so
    public static TextureRegion dessert_jungle_layer_1, dessert_jungle_layer_2, dessert_jungle_layer_3;  //todo put this in an enum or so
    public static TextureRegion ocean_layer_1, ocean_layer_2, ocean_layer_3;
    public static TextureRegion dessert_ocean_layer_1, dessert_ocean_layer_2, dessert_ocean_layer_3;
    public static TextureRegion vodka, meat, soda, spinach;
    public static Sound hitGround1Sound, boing, hurt1, hurt2, hurt3, slurp, eat1, canOpen1, burp3, hitEntity1, coin1_sound, coin2_sound, coin3_sound, coin4_sound, buttonConfirm;

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
        assetManager.load("data/fonts/font64.fnt", BitmapFont.class);
        assetManager.load("data/fonts/font32.fnt", BitmapFont.class);
        assetManager.load("data/fonts/font24Bold.fnt", BitmapFont.class);
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

        assetManager.load("data/sounds/music/JungleTheme.ogg", Music.class);
        assetManager.load("data/sounds/music/ShopTheme.ogg", Music.class);
        assetManager.load("data/sounds/music/SpaceTheme.ogg", Music.class);
        assetManager.load("data/sounds/music/DessertTheme.ogg", Music.class);

        assetManager.load("data/sounds/music/WindTheme.ogg", Music.class);
        assetManager.load("data/sounds/coin1.mp3", Sound.class);
        assetManager.load("data/sounds/coin2.mp3", Sound.class);
        assetManager.load("data/sounds/coin3.mp3", Sound.class);
        assetManager.load("data/sounds/coin4.mp3", Sound.class);

        assetManager.load("data/sounds/button_confirm.mp3", Sound.class);
    }

    public static void set() {
        setSkin();
        setAtlas();
        setTextures();
        setSounds();
    }

    private static void setSkin() {
        skin = new Skin();

        font64 = assetManager.get("data/fonts/font64.fnt");
        font32 = assetManager.get("data/fonts/font32.fnt");
        font24Bold = assetManager.get("data/fonts/font24Bold.fnt");

        skin.add("default-font", font64, BitmapFont.class);
        skin.add("small-font", font32, BitmapFont.class);
        skin.add("bold20", font24Bold, BitmapFont.class);

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
        brachioBackPull = items.findRegion("brachioBackPull");
        brachioFrontPull = items.findRegion("brachioFrontPull");
        brachioMidPull = items.findRegion("brachioMidPull");
        quetzaTexture = items.findRegion("QUETZA");
        quetzaHit = items.findRegion("QUETZA2");
        mosquitoTexture = items.findRegion("mosquito1");
        mosquitoHit = items.findRegion("mosquito2");
        sabertooth1 = items.findRegion("sabertooth1");
        sabertooth2 = items.findRegion("sabertooth2");
        smallEggTexture = items.findRegion("smallEgg");
        smallEggBroken = items.findRegion("smallEgg2");
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
        steroids = items.findRegion("steroids");
        wings = items.findRegion("wings");
        springs = items.findRegion("springshoes");
        faceBackground = items.findRegion("expresionsCircle");
        faceHappy = items.findRegion("happyface");
        facePain = items.findRegion("painface");
        faceTired = items.findRegion("tiredface");
        facePassion = items.findRegion("passionface");
        faceKO = items.findRegion("koface");
        title = items.findRegion("title");
        touchNH = items.findRegion("touchNH");
        buttonBroken = items.findRegion("broken_button");
        dialog = items.findRegion("dialog");
        buyButton = items.findRegion("buyButton");
        buyButtonDown = items.findRegion("buyButtonDown");
        starBlack = items.findRegion("starDark");
        starBright = items.findRegion("starShine");

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

        dessert_layer_1 = items.findRegion("desert_layer", 1);
        dessert_layer_2 = items.findRegion("desert_layer", 2);
        dessert_layer_3 = items.findRegion("desert_layer", 3);

        ocean_layer_1 = items.findRegion("ocean_layer", 1);
        ocean_layer_2 = items.findRegion("ocean_layer", 2);
        ocean_layer_3 = items.findRegion("ocean_layer", 3);

        jungle_layer_1 = items.findRegion("jungle_layer", 1);
        jungle_layer_2 = items.findRegion("jungle_layer", 4);
        jungle_layer_3 = items.findRegion("jungle_layer", 3);
        jungle_layer_4 = items.findRegion("jungle_layer", 2);

        jungle_ocean_layer_1 = items.findRegion("jungle_ocean", 1);
        jungle_ocean_layer_2 = items.findRegion("jungle_ocean", 2);
        jungle_ocean_layer_3 = items.findRegion("jungle_ocean", 3);

        jungle_dessert_layer_1 = items.findRegion("jungle_desert");
        jungle_dessert_layer_2 = items.findRegion("desert_layer", 2);
        jungle_dessert_layer_3 = items.findRegion("desert_layer", 3);

        dessert_jungle_layer_1 = items.findRegion("desert_jungle");
        dessert_jungle_layer_2 = items.findRegion("jungle_layer", 2);
        dessert_jungle_layer_3 = items.findRegion("jungle_layer", 3);

        button = items.findRegion("button");

        // ocean_layer_1, ocean_layer_2, ocean_layer_3;

        cloud1 = items.findRegion("cloud1");
        cloud2 = items.findRegion("cloud2");
        cloud3 = items.findRegion("cloud3");
    }

    private static void setSounds() {
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
        coin1_sound = assetManager.get("data/sounds/coin1.mp3");
        coin2_sound = assetManager.get("data/sounds/coin2.mp3");
        coin3_sound = assetManager.get("data/sounds/coin3.mp3");
        coin4_sound = assetManager.get("data/sounds/coin4.mp3");
        buttonConfirm = assetManager.get("data/sounds/button_confirm.mp3");
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

    public static Music loadMusicFile(String path) {
        return assetManager.get("data/sounds/music/" + path + ".ogg");
    }

    public static TextureRegion getCloud(int cloudId) {
        switch (cloudId) {
            case 1:
                return cloud1;
            case 2:
                return cloud2;
            case 3:
                return cloud3;
        }
        return cloud1;
    }
}