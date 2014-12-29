package com.deeep.flycaveman;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.deeep.flycaveman.classes.Assets;
import com.deeep.flycaveman.screens.GameScreen;

public class Core implements ApplicationListener {
    public static final float VIRTUAL_WIDTH = 1280;
    public static final float VIRTUAL_HEIGHT = 720;
    public static final float BOX2D_VIRTUAL_WIDTH = VIRTUAL_WIDTH / 40;
    public static final float BOX2D_VIRTUAL_HEIGHT = VIRTUAL_HEIGHT / 40;
    public static final float VIRTUAL_ASPECT = BOX2D_VIRTUAL_WIDTH / BOX2D_VIRTUAL_HEIGHT;

    private OrthographicCamera camera;
    private SpriteBatch spriteBatch;
    private Screen screen;
    private Rectangle viewport;

    @Override
    public void create() {
        Assets.getAssets().load();

        camera = new OrthographicCamera(Core.BOX2D_VIRTUAL_WIDTH, Core.BOX2D_VIRTUAL_HEIGHT);
        camera.position.set(BOX2D_VIRTUAL_WIDTH / 2, BOX2D_VIRTUAL_HEIGHT / 2, 0);

        spriteBatch = new SpriteBatch();

        viewport = new Rectangle();

        setScreen(new GameScreen(this));
    }

    @Override
    public void render() {
        camera.update();

        Gdx.gl.glViewport((int) viewport.x, (int) viewport.y, (int) viewport.width, (int) viewport.height);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); // This cryptic line clears the screen.

        spriteBatch.setProjectionMatrix(camera.combined);

        if (screen != null) screen.render(Gdx.graphics.getDeltaTime());
    }

    @Override
    public void resize(int width, int height) {
        if (screen != null) screen.resize(width, height);

        float aspectRatio = (float) width / (float) height;
        float scale;

        Vector2 crop = new Vector2(0f, 0f);
        if (aspectRatio > VIRTUAL_ASPECT) {
            scale = (float) height / BOX2D_VIRTUAL_HEIGHT;
            crop.x = (width - BOX2D_VIRTUAL_WIDTH * scale) / 2f;
        } else if (aspectRatio < VIRTUAL_ASPECT) {
            scale = (float) width / BOX2D_VIRTUAL_WIDTH;
            crop.y = (height - BOX2D_VIRTUAL_HEIGHT * scale) / 2f;
        } else {
            scale = (float) width / BOX2D_VIRTUAL_WIDTH;
        }

        float w = BOX2D_VIRTUAL_WIDTH * scale;
        float h = BOX2D_VIRTUAL_HEIGHT * scale;

        viewport.set(crop.x, crop.y, w, h);
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

    public OrthographicCamera getCamera() {
        return camera;
    }

    public SpriteBatch getSpriteBatch() {
        return spriteBatch;
    }
}