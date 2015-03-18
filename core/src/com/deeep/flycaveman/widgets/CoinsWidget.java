package com.deeep.flycaveman.widgets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
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
    public Image background;
    private Image coin;
    private Label amount;

    public CoinsWidget() {
        background = new Image(Assets.button);
        background.setSize(128, 70);

        coin = new Image(Assets.coin1);
        coin.setSize(50, 50);

        amount = new Label("", Assets.skin);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);

        background.setPosition(x, y);
        coin.setPosition(x + 16, y + 10);
        amount.setPosition(x + 84, y + 36);
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

    public void fadeIn() {
        background.addAction(Actions.delay(0.5f, Actions.fadeIn(1.0f)));
        coin.addAction(Actions.delay(0.5f, Actions.fadeIn(1.0f)));
        amount.addAction(Actions.delay(0.5f, Actions.fadeIn(1.0f)));
    }

    public void fadeOut() {
        background.addAction(Actions.delay(0.25f, Actions.fadeOut(1.0f)));
        coin.addAction(Actions.delay(0.25f, Actions.fadeOut(1.0f)));
        amount.addAction(Actions.delay(0.25f, Actions.fadeOut(1.0f)));
    }

    public void moveUp() {
        background.addAction(Actions.moveTo(240, 0, 0.4f, Interpolation.linear));
        coin.addAction(Actions.moveTo(240 + 16, 10, 0.4f, Interpolation.linear));
        amount.addAction(Actions.moveTo(240 + 84, 36, 0.4f, Interpolation.linear));
    }

    public void moveDown() {
        background.addAction(Actions.moveTo(240, -background.getHeight(), 0.4f, Interpolation.linear));
        coin.addAction(Actions.moveTo(240 + 16, -10 - background.getHeight(), 0.4f, Interpolation.linear));
        amount.addAction(Actions.moveTo(240 + 84, -36 - background.getHeight(), 0.4f, Interpolation.linear));
    }
}