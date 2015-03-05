package com.deeep.flycaveman.widgets;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.deeep.flycaveman.Assets;
import com.deeep.flycaveman.Core;
import com.deeep.flycaveman.entities.CaveMan;

/**
 * Created by scanevaro on 05/03/2015.
 */
public class ExpressionsWidget extends Actor {


    private Image background, face;

    public ExpressionsWidget() {
        background = new Image(Assets.faceBackground);
        background.setSize(96, 96);
        background.setPosition(Core.VIRTUAL_WIDTH / 3 - background.getWidth() / 2, 0);

        face = new Image();
        face.setSize(90, 90);
        face.setPosition(Core.VIRTUAL_WIDTH / 3 - face.getWidth() / 2, 0);

        face.setDrawable(new TextureRegionDrawable(Assets.faceHappy));
    }

    public void update(CaveMan caveman) {
        if (caveman.getState() == CaveMan.STATE_HAPPY)
            face.setDrawable(new TextureRegionDrawable(Assets.faceHappy));
        else if (caveman.getState() == CaveMan.STATE_TIRED)
            face.setDrawable(new TextureRegionDrawable(Assets.faceTired));
        else if (caveman.getState() == CaveMan.STATE_KO)
            face.setDrawable(new TextureRegionDrawable(Assets.faceKO));
        else if (caveman.getState() == CaveMan.STATE_PAIN)
            face.setDrawable(new TextureRegionDrawable(Assets.facePain));
        else if (caveman.getState() == CaveMan.STATE_PASSION)
            face.setDrawable(new TextureRegionDrawable(Assets.facePassion));
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(getColor().r, getColor().g, getColor().b, getColor().a * parentAlpha);

        background.draw(batch, parentAlpha);
        face.draw(batch, parentAlpha);
    }
}