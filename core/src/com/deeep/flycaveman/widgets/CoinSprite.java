package com.deeep.flycaveman.widgets;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.deeep.flycaveman.Assets;

/**
 * Created by scanevaro on 17/03/2015.
 */
public class CoinSprite extends Actor {
    Animation animation;
    private float stateTime;

    public CoinSprite() {
        animation = new Animation(0.2f, Assets.coin1, Assets.coin2, Assets.coin3, Assets.coin4, Assets.coin5,
                Assets.coin6);
        animation.setPlayMode(Animation.PlayMode.LOOP);

        stateTime = 0;
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        stateTime += delta;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.draw(animation.getKeyFrame(stateTime), getX(), getY(), getWidth(), getHeight());
    }
}