package com.deeep.flycaveman.classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 10/28/13
 * Time: 4:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class Assets {
    /**
     * instance for singleton
     */
    private static Assets assets;
    /**
     * Just a check to be sure that the assets aren't loaded multiple times
     */
    private static boolean loaded = false;
    /**
     * The atlases containing all the images
     */
    private TextureAtlas textureAtlas;

    /**
     * Skin for menus and buttons
     */
    private Skin skin;

    /**
     * Logo for SplashScreen
     */
    private Texture logo;

    /**
     * Game Title
     */
    private Texture title;


    /**
     * Logger instance
     */
//    private Logger logger = Logger.getInstance();


    private TextureRegion cavemanTexture;
    private TextureRegion brachioTexture;
    private TextureRegion quetzaTexture;
    private TextureRegion smallEggTexture;
    private Texture backgroundTexture;
    private Texture restartButton;
    private Texture catapultArmTexture;
    private Texture catapultBaseTexture;
    private Sound hitGround1Sound;
    private Music music;

    /**
     * Simple singleton
     *
     * @return assets instance
     */
    public static Assets getAssets() {
        if (assets == null) {
            assets = new Assets();
        }
        return assets;
    }

    /**
     * function to load everything. Nothing special. TODO add more resources here( sound music etc)
     */
    public void load() {
        if (!loaded) {
//            textureAtlas = new TextureAtlas(Gdx.files.internal("images/TextureAtlas.txt"));
            skin = new Skin(Gdx.files.internal("data/uiskin.json"));
//            logo = new Texture(Gdx.files.internal("data/logo.png"));
//            title = new Texture(Gdx.files.internal("data/title.png"));
//            logger.system(Assets.class, "All assets have been loaded");
            cavemanTexture = new TextureRegion(new Texture(Gdx.files.internal("data/caveman.png")));
            brachioTexture = new TextureRegion(new Texture(Gdx.files.internal("data/BRACHIOSAURUS.png")));
            quetzaTexture = new TextureRegion(new Texture(Gdx.files.internal("data/QUETZA.png")));
            smallEggTexture = new TextureRegion(new Texture(Gdx.files.internal("data/smallEgg.png")));
            backgroundTexture = new Texture(Gdx.files.internal("data/background.png"));
            restartButton = new Texture(Gdx.files.internal("data/restart.png"));
            hitGround1Sound = Gdx.audio.newSound(Gdx.files.internal("data/sounds/hitGround1.mp3"));

            music = Gdx.audio.newMusic(Gdx.files.internal("data/sounds/music/presenta.mp3"));
            music.setLooping(true);
            music.setVolume(0.5f);

            catapultArmTexture = new Texture(Gdx.files.internal("data/catapultArm.png"));
            catapultBaseTexture = new Texture(Gdx.files.internal("data/catapultBase.png"));

            loaded = true;
        }
    }

    /**
     * Returns an texture region based on the name given. Get images using this function!
     *
     * @param name see TextureAtlas.txt
     * @return the texture region connected to the name
     */
    public TextureRegion getRegion(String name) {
        TextureRegion textureRegion = textureAtlas.findRegion(name);
        if (textureRegion == null) {
//            logger.error(Assets.class, "Unkown texture requested: " + name);
        }
        return textureAtlas.findRegion(name);
    }

    public Skin getSkin() {
        return skin;
    }

    public Texture getLogo() {
        return logo;
    }

    public Texture getTitle() {
        return title;
    }

    public void dispose() {
//        textureAtlas.dispose();
        skin.dispose();
    }

    public TextureRegion getSmallEggTexture() {
        return smallEggTexture;
    }

    public TextureRegion getQuetzaTexture() {
        return quetzaTexture;
    }

    public TextureRegion getBrachioTexture() {
        return brachioTexture;
    }

    public TextureRegion getCavemanTexture() {
        return cavemanTexture;
    }

    public Texture getBackgroundTexture() {
        return backgroundTexture;
    }

    public Texture getRestartButton() {
        return restartButton;
    }

    public Sound getHitGround1Sound() {
        return hitGround1Sound;
    }

    public Music getMusic() {
        return music;
    }

    public Texture getCatapultArmTexture() {
        return catapultArmTexture;
    }

    public Texture getCatapultBaseTexture() {
        return catapultBaseTexture;
    }
}