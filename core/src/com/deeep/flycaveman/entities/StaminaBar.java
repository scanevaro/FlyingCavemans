package com.deeep.flycaveman.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.deeep.flycaveman.Assets;
import com.deeep.flycaveman.Core;

/**
 * Created by scanevaro on 09/02/2015.
 */
public class StaminaBar extends Actor {
    private CaveMan caveman;
    public Image background, fill[], bar, hand;
    private float maxStamina;

    public StaminaBar(CaveMan caveMan) {
        this.caveman = caveMan;

        background = new Image(Assets.staminaBackground);

        fill = new Image[(int) caveMan.stamina];
        for (int i = 0; i < (int) caveMan.stamina; i++)
            fill[i] = new Image(Assets.staminaFill);

        bar = new Image(Assets.staminaBar);
        hand = new Image(Assets.staminaHand);

        background.setPosition(Core.VIRTUAL_WIDTH / 2 - background.getWidth() / 2, 5);

        float posX = 3;
        for (int x = 0; x < caveMan.stamina; x++) {
            fill[x].setPosition(Core.VIRTUAL_WIDTH / 2 - background.getWidth() / 2 + posX, 10);
            fill[x].setScaleX(3.6f);
            posX += fill[x].getWidth() + 26;
        }

        bar.setPosition(Core.VIRTUAL_WIDTH / 2 - bar.getWidth() / 2, 5);
        hand.setSize(48, 54);
        hand.setPosition(Core.VIRTUAL_WIDTH / 2 - hand.getWidth() / 2, 15);

        maxStamina = caveman.stamina;
    }

    @Override
    public void act(float delta) {
        super.act(delta);

//        System.out.println(5 % 3.5);

        for (int x = 0; x < caveman.stamina; x++)
            fill[x].setScaleX(3.6f/*caveman.stamina / 5 * 100 / 5.0f*/);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(getColor().r, getColor().g, getColor().b, getColor().a * parentAlpha);

        background.draw(batch, parentAlpha);

        for (int x = 0; x < caveman.stamina; x++)
            fill[x].draw(batch, parentAlpha);

        bar.draw(batch, parentAlpha);
        hand.draw(batch, parentAlpha);
    }

    @Override
    public void setColor(Color color) {
        super.setColor(color);

        background.setColor(color);

        for (int x = 0; x < fill.length; x++)
            fill[x].setColor(color);

        bar.setColor(color);
        hand.setColor(color);
    }

    @Override
    public void addAction(Action action) {
        super.addAction(action);

        background.addAction(action);

        for (int x = 0; x < fill.length; x++)
            fill[x].addAction(action);

        bar.addAction(action);
        hand.addAction(action);
    }
}