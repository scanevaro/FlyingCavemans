package com.deeep.flycaveman.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.deeep.flycaveman.Assets;
import com.deeep.flycaveman.Core;

/**
 * Created by scanevaro on 29/03/2015.
 */
public class IntroActor extends Actor {
    private Image im1, im2, im3, im4, im5;

    public IntroActor() {
        im1 = new Image(Assets.intro1);
        im1.setSize(Core.VIRTUAL_WIDTH, Core.VIRTUAL_HEIGHT);
        im1.setColor(1, 1, 1, 0);
        im1.addAction(new SequenceAction(Actions.fadeIn(0.25f), Actions.delay(1.0f), Actions.fadeOut(0.25f)));
        im2 = new Image(Assets.intro2);
        im2.setSize(Core.VIRTUAL_WIDTH, Core.VIRTUAL_HEIGHT);
        im2.setColor(1, 1, 1, 0);
        im2.addAction(new SequenceAction(Actions.delay(0.75f), Actions.fadeIn(0.50f), Actions.delay(1.0f), Actions.fadeOut(0.25f)));
        im3 = new Image(Assets.intro3);
        im3.setSize(Core.VIRTUAL_WIDTH, Core.VIRTUAL_HEIGHT);
        im3.setColor(1, 1, 1, 0);
        im3.addAction(new SequenceAction(Actions.delay(2.0f), Actions.fadeIn(0.25f), Actions.delay(1.0f), Actions.fadeOut(0.25f)));
        im4 = new Image(Assets.intro4);
        im4.setSize(Core.VIRTUAL_WIDTH, Core.VIRTUAL_HEIGHT);
        im4.setColor(1, 1, 1, 0);
        im4.addAction(new SequenceAction(Actions.delay(3.0f), Actions.fadeIn(0.25f), Actions.delay(1.0f), Actions.fadeOut(0.25f)));
        im5 = new Image(Assets.intro5);
        im5.setSize(Core.VIRTUAL_WIDTH, Core.VIRTUAL_HEIGHT);
        im5.setColor(1, 1, 1, 0);
        im5.addAction(new SequenceAction(Actions.delay(4.0f), Actions.fadeIn(0.25f), Actions.delay(1.0f), Actions.fadeOut(0.25f)));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        im1.draw(batch, parentAlpha);
        im2.draw(batch, parentAlpha);
        im3.draw(batch, parentAlpha);
        im4.draw(batch, parentAlpha);
        im5.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        im1.act(delta);
        im2.act(delta);
        im3.act(delta);
        im4.act(delta);
        im5.act(delta);
    }
}