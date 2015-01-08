package com.deeep.flycaveman;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.deeep.flycaveman.classes.Assets;
import com.deeep.flycaveman.screens.GameScreen;

public class Core implements ApplicationListener {
    public static final float VIRTUAL_WIDTH = 960;
    public static final float VIRTUAL_HEIGHT = 540;
    public static final float BOX2D_VIRTUAL_WIDTH = VIRTUAL_WIDTH / 10;
    public static final float BOX2D_VIRTUAL_HEIGHT = VIRTUAL_HEIGHT / 10;

    private SpriteBatch spriteBatch;
    private Screen screen;

    @Override
    public void create() {
        Assets.getAssets().load();

        spriteBatch = new SpriteBatch();
        setScreen(new GameScreen(this));
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // This cryptic line clears the screen.

        if (screen != null) screen.render(Gdx.graphics.getDeltaTime());
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
        Assets.getAssets().dispose();
        if (screen != null) screen.dispose();
    }

    public void setScreen(Screen screen) {
        if (this.screen != null) this.screen.hide();
        this.screen = screen;
        if (this.screen != null) {
            this.screen.show();
            this.screen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
    }

    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }
}