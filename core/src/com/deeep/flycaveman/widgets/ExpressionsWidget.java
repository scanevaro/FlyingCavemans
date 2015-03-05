package com.deeep.flycaveman.widgets;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.deeep.flycaveman.Assets;
import com.deeep.flycaveman.Core;

/**
 * Created by scanevaro on 05/03/2015.
 */
public class ExpressionsWidget extends Actor {

    private final int STATE_HAPPY = 0;
    private final int STATE_TIRED = 1;
    private final int STATE_PAIN = 2;
    private final int STATE_KO = 3;
    private final int STATE_PASSION = 4;

    private Image background, face;

    private int state;

    public ExpressionsWidget() {
        background = new Image(Assets.faceBackground);
        background.setSize(96, 96);
        background.setPosition(Core.VIRTUAL_WIDTH / 3 - background.getWidth() / 2, 0);

        face = new Image();
        face.setSize(90, 90);
        face.setPosition(Core.VIRTUAL_WIDTH / 3 - face.getWidth() / 2, 0);

        state = STATE_HAPPY;

        update();
    }

    public void update() {
        if (state == STATE_HAPPY)
            face.setDrawable(new TextureRegionDrawable(Assets.faceHappy));
        else if (state == STATE_TIRED)
            face.setDrawable(new TextureRegionDrawable(Assets.faceTired));
        else if (state == STATE_KO)
            face.setDrawable(new TextureRegionDrawable(Assets.faceKO));
        else if (state == STATE_PAIN)
            face.setDrawable(new TextureRegionDrawable(Assets.facePain));
        else if (state == STATE_PASSION)
            face.setDrawable(new TextureRegionDrawable(Assets.facePassion));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(getColor().r, getColor().g, getColor().b, getColor().a * parentAlpha);

        background.draw(batch, parentAlpha);
        face.draw(batch, parentAlpha);
    }
}