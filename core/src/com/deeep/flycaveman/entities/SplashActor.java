package com.deeep.flycaveman.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import com.deeep.flycaveman.Core;

/**
 * Created by scanevaro on 09/12/2014.
 */
public class SplashActor extends Actor {
    private Animation animation;
    public float stateTime;

    public SplashActor() {
        Array<TextureAtlas.AtlasRegion> atlasRegions = new TextureAtlas(Gdx.files.internal("data/newLogo.pack")).getRegions();
        animation = new Animation(0.70f, atlasRegions);
        setActions();
        stateTime = 0;
        setSize(Core.VIRTUAL_WIDTH, Core.VIRTUAL_HEIGHT);
    }

    private void setActions() {
        addAction(Actions.fadeIn(0.5f));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(getColor().r, getColor().g, getColor().b, getColor().a * parentAlpha);
        Gdx.graphics.getGL20().glClearColor(0, 0, 0, 1);
        Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        batch.draw((TextureRegion)animation.getKeyFrame(stateTime * 10), Core.VIRTUAL_WIDTH / 2 - 192 / 2,
                Core.VIRTUAL_HEIGHT / 2 - 192 / 2);
    }

    public void act(float delta, float stateTime) {
        this.stateTime = stateTime;
    }
}