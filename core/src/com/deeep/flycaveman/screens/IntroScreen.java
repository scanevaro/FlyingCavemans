package com.deeep.flycaveman.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.deeep.flycaveman.Core;
import com.deeep.flycaveman.entities.IntroActor;

/**
 * Created by scanevaro on 29/03/2015.
 */
public class IntroScreen extends AbstractScreen {
    private Core game;
    private IntroActor splashSprite;
    private Timer.Task timer;

    public IntroScreen(Core game) {
        this.game = game;
    }

    private void setActors() {
        splashSprite = new IntroActor();
    }

    private void configureActors() {
        splashSprite.setColor(1, 1, 1, 0);
    }

    private void setListeners() {
        splashSprite.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
            }
        });
    }

    private void setLayout() {
        stage.addActor(splashSprite);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0.1f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(Core.VIRTUAL_WIDTH, Core.VIRTUAL_HEIGHT));
        Gdx.input.setInputProcessor(stage);
        setActors();
        configureActors();
        setListeners();
        setLayout();

        Timer.schedule(timer = new Timer.Task() {
            @Override
            public void run() {
                game.setScreen(new GameScreen(game));
            }
        }, 5.5f);
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }
}