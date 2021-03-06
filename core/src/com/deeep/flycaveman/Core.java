package com.deeep.flycaveman;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.deeep.flycaveman.screens.AbstractScreen;
import com.deeep.flycaveman.screens.SplashScreen;
import com.deeep.flycaveman.widgets.Dialogs;
import com.deeep.flycaveman.world.MusicController;
import com.deeep.flycaveman.world.World;

public class Core implements ApplicationListener {
    public static final float VIRTUAL_WIDTH = 960;
    public static final float VIRTUAL_HEIGHT = 540;
    public static final float BOX2D_VIRTUAL_WIDTH = VIRTUAL_WIDTH / /*30*/35;
    public static final float BOX2D_VIRTUAL_HEIGHT = VIRTUAL_HEIGHT / /*30*/35;

    //public static final int gameID = 55294;

    public static boolean dialogOpen;

    private SpriteBatch spriteBatch;
    public AbstractScreen screen;
    public Dialogs dialogs;
    //    public GJAPI gjapi;
    //    private Timer timer;
    public static float pixelsToUnit1100 = 1100 / 35;
    public static float pixelsToUnit1300 = 1300 / 35;
    public static float pixelsToUnit1500 = 1500 / 35;
    public static float pixelsToUnit50 = 50 / 35;
    public static float pixelsToUnit100 = 100 / 35;
    public static float pixelsToUnit150 = 150 / 35;
    FPSLogger logger;

    @Override
    public void create() {
        new Assets().load();


//        gjapi = new GJAPI("50cf34be8b7b46dd5075db924bef44e2", Core.gameID);
//
//        timer = new Timer();
//        timer.scheduleTask(new Timer.Task() {
//            @Override
//            public void run() {
//                if (gjapi.isActive()) {
//                    gjapi.pingSession();
//                }
//            }
//        }, 30, 30);

        /** Catch hardware back button*/
        Gdx.input.setCatchBackKey(true);
        dialogs = new Dialogs();

        spriteBatch = new SpriteBatch();
        setScreen(new SplashScreen(this));
        logger = new FPSLogger();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // This cryptic line clears the screen.
        if (MusicController.musicController != null)
            MusicController.musicController.updateSoundManager(Gdx.graphics.getDeltaTime());
        if (MusicController.musicController != null) MusicController.musicController.update(World.caveman);
        if (screen != null) screen.render(Gdx.graphics.getDeltaTime());
        logger.log();
    }

    @Override
    public void resize(int width, int height) {
        if (screen != null) screen.resize(width, height);
    }

    @Override
    public void pause() {
        if (screen != null) screen.pause();
    }

    @Override
    public void resume() {
        if (screen != null) screen.resume();
    }

    @Override
    public void dispose() {
        Assets.dispose();
        if (screen != null) screen.dispose();
    }

    public void setScreen(AbstractScreen screen) {
        if (this.screen != null) {
            this.screen.hide();
            this.screen.dispose();
        }
        this.screen = screen;
        if (this.screen != null) {
            this.screen.show();
            this.screen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
    }

    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }

    public static Vector2 pixelsToBoxUnit(Vector2 pixels) {
        pixels.x = pixels.x / 35;
        pixels.y = pixels.y / 35;
        return pixels;
    }

    public static float boxUnitToPixels(float units) {
        return units * 35f;
    }
}