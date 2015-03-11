package com.deeep.flycaveman.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.Array;

/**
 * Created by scanevaro on 09/12/2014.
 */
public class SplashActor extends Actor {
    private Animation animation;
    public float stateTime;

    public SplashActor() {
        Array<TextureAtlas.AtlasRegion> atlasRegions = new TextureAtlas(Gdx.files.internal("data/newLogo.pack")).getRegions();
        animation = new Animation(0.04f, atlasRegions);
        animation.setPlayMode(Animation.PlayMode.LOOP);

        setActions();

        stateTime = 0;
    }

    private void setActions() {
        SequenceAction secAction = new SequenceAction();
        secAction.addAction(Actions.fadeIn(0.5f));
//        secAction.addAction(Actions.delay(2.5f));
//        secAction.addAction(Actions.fadeOut(0.5f));
        addAction(secAction);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color color = new Color(getColor().r, getColor().g,
                getColor().b, getColor().a * parentAlpha);

        batch.setColor(color);
        Gdx.graphics.getGL20().glClearColor( 0, 0, 0, 1 );
        Gdx.graphics.getGL20().glClear( GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT );
        batch.draw(animation.getKeyFrame(stateTime), (Gdx.graphics.getWidth() / 2)-(animation.getKeyFrame(stateTime).getRegionWidth() /2), (Gdx.graphics.getHeight() / 2)-(animation.getKeyFrame(stateTime).getRegionHeight()/2));
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        stateTime += delta;
    }
}