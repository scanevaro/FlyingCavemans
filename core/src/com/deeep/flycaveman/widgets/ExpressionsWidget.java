package com.deeep.flycaveman.widgets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.deeep.flycaveman.Assets;
import com.deeep.flycaveman.entities.CaveMan;

/**
 * Created by scanevaro on 05/03/2015.
 */
public class ExpressionsWidget extends Actor {
    private CaveMan caveman;
    private Image background, face;
    private TextureRegionDrawable happyFace, tiredFace, okFace, painFace, passionFace;

    public ExpressionsWidget() {
        background = new Image(Assets.faceBackground);
        background.setSize(96, 96);
        background.setPosition(0, 0);
        happyFace = new TextureRegionDrawable(Assets.faceHappy);
        tiredFace = new TextureRegionDrawable(Assets.faceTired);
        okFace = new TextureRegionDrawable(Assets.faceKO);
        painFace = new TextureRegionDrawable(Assets.facePain);
        passionFace = new TextureRegionDrawable(Assets.facePassion);
        face = new Image();
        face.setSize(90, 90);
        face.setPosition(3, 3);
        face.setDrawable(new TextureRegionDrawable(Assets.faceHappy));
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        background.act(delta);
        face.act(delta);
        if (caveman.getState() == CaveMan.STATE_HAPPY) face.setDrawable(happyFace);
        else if (caveman.getState() == CaveMan.STATE_TIRED) face.setDrawable(tiredFace);
        else if (caveman.getState() == CaveMan.STATE_KO) face.setDrawable(okFace);
        else if (caveman.getState() == CaveMan.STATE_PAIN) face.setDrawable(painFace);
        else if (caveman.getState() == CaveMan.STATE_PASSION) face.setDrawable(passionFace);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        background.draw(batch, parentAlpha);
        face.draw(batch, parentAlpha);
    }

    public void setCaveman(CaveMan caveman) {
        this.caveman = caveman;
    }

    @Override
    public void setColor(Color color) {
        super.setColor(color);
        background.setColor(color);
        face.setColor(color);
    }

    @Override
    public void addAction(Action action) {
        background.addAction(Actions.delay(0.5f, Actions.fadeIn(1.0f)));
        face.addAction(Actions.delay(0.5f, Actions.fadeIn(1.0f)));
    }

    public void fadeOut() {
        background.addAction(Actions.delay(0.25f, Actions.fadeOut(1.0f)));
        face.addAction(Actions.delay(0.25f, Actions.fadeOut(1.0f)));
    }
}