package com.deeep.flycaveman.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.deeep.flycaveman.Core;
import com.deeep.flycaveman.classes.Assets;

/**
 * Created by scanevaro on 09/02/2015.
 */
public class StaminaBar extends Actor {
    private CaveMan caveman;
    public Image background, fill, bar, hand;

    public StaminaBar(CaveMan caveMan) {
        this.caveman = caveMan;

        background = new Image(Assets.staminaBackground);
        fill = new Image(Assets.staminaFill);
        bar = new Image(Assets.staminaBar);
        hand = new Image(Assets.staminaHand);

        background.setPosition(Core.VIRTUAL_WIDTH / 2 - background.getWidth() / 2, 5);
        fill.setPosition(Core.VIRTUAL_WIDTH / 2 - background.getWidth() / 2 + 3, 10);
        bar.setPosition(Core.VIRTUAL_WIDTH / 2 - bar.getWidth() / 2, 5);
        hand.setSize(48, 54);
        hand.setPosition(Core.VIRTUAL_WIDTH / 2 - hand.getWidth() / 2, 15);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        fill.setScaleX((caveman.stamina * 100 / 0.5f) * 20 / 100);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(getColor().r, getColor().g, getColor().b, getColor().a * parentAlpha);

        background.draw(batch, parentAlpha);
        fill.draw(batch, parentAlpha);
        bar.draw(batch, parentAlpha);
        hand.draw(batch, parentAlpha);
    }
}