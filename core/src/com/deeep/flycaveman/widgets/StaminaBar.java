package com.deeep.flycaveman.widgets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.deeep.flycaveman.Assets;
import com.deeep.flycaveman.entities.CaveMan;

/**
 * Created by scanevaro on 09/02/2015.
 */
public class StaminaBar extends Actor {
    private CaveMan caveman;
    public Image background, fill[], bar, hand;

    public StaminaBar(CaveMan caveMan) {
        this.caveman = caveMan;
        background = new Image(Assets.staminaBackground);
        fill = new Image[(int) caveMan.stamina];
        for (int i = 0; i < (int) caveMan.stamina; i++) fill[i] = new Image(Assets.staminaFill);
        bar = new Image(Assets.staminaBar);
        hand = new Image(Assets.staminaHand);
        background.setPosition(100, 5);
        float posX = 3;
        for (int x = 0; x < caveMan.stamina; x++) {
            fill[x].setPosition(100 + posX, 10);
//            fill[x].setScaleX(3.6f);
            fill[x].setWidth(background.getWidth() / caveMan.stamina - 2);
            posX += fill[x].getWidth() + 1;
        }
        bar.setPosition(100, 5);
        hand.setSize(48, 54);
        hand.setPosition(100 + background.getWidth() / 2 - hand.getWidth() / 2, 15);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        background.act(delta);
        for (int x = 0; x < fill.length; x++) fill[x].act(delta);
        bar.act(delta);
        hand.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        background.draw(batch, parentAlpha);
        for (int x = 0; x < caveman.stamina; x++) fill[x].draw(batch, parentAlpha);
        bar.draw(batch, parentAlpha);
        hand.draw(batch, parentAlpha);
    }

    @Override
    public void setColor(Color color) {
        super.setColor(color);
        background.setColor(color);
        for (int x = 0; x < fill.length; x++) fill[x].setColor(color);
        bar.setColor(color);
        hand.setColor(color);
    }

    @Override
    public void addAction(Action action) {
        background.addAction(Actions.delay(0.5f, Actions.fadeIn(1.0f)));
        for (int x = 0; x < fill.length; x++) fill[x].addAction(Actions.delay(0.5f, Actions.fadeIn(1.0f)));
        bar.addAction(Actions.delay(0.5f, Actions.fadeIn(1.0f)));
        hand.addAction(Actions.delay(0.5f, Actions.fadeIn(1.0f)));
    }

    public void fadeOut() {
        background.addAction(Actions.delay(0.25f, Actions.fadeOut(1.0f)));
        for (int x = 0; x < fill.length; x++) fill[x].addAction(Actions.delay(0.25f, Actions.fadeOut(1.0f)));
        bar.addAction(Actions.delay(0.25f, Actions.fadeOut(1.0f)));
        hand.addAction(Actions.delay(0.25f, Actions.fadeOut(1.0f)));
    }
}