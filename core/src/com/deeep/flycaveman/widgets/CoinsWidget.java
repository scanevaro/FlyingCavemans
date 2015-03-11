package com.deeep.flycaveman.widgets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.deeep.flycaveman.Assets;
import com.deeep.flycaveman.entities.CaveMan;

/**
 * Created by scanevaro on 08/03/2015.
 */
public class CoinsWidget extends Actor {
    private Image background, coin;
    private Label amount;
    private CaveMan caveMan;

    public CoinsWidget() {
        background = new Image(Assets.button);
        background.setSize(128, 70);
        background.setPosition(112, 0);

        coin = new Image(Assets.coin1);
        coin.setSize(50, 50);
        coin.setPosition(128, 10);

        amount = new Label("", Assets.skin);
        amount.setPosition(196, 36);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        background.draw(batch, parentAlpha);
        coin.draw(batch, parentAlpha);
        amount.draw(batch, parentAlpha);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        background.act(delta);
        coin.act(delta);

        amount.setText(String.valueOf(CaveMan.coins));
        amount.act(delta);
    }

    @Override
    public void setColor(Color color) {
        background.setColor(color);
        coin.setColor(color);
        amount.setColor(color);
    }

    @Override
    public void addAction(Action action) {
        background.addAction(Actions.delay(0.5f, Actions.fadeIn(1.0f)));
        coin.addAction(Actions.delay(0.5f, Actions.fadeIn(1.0f)));
        amount.addAction(Actions.delay(0.5f, Actions.fadeIn(1.0f)));
    }

    public void setCaveMan(CaveMan caveMan) {
        this.caveMan = caveMan;
    }
}