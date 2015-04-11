package com.deeep.flycaveman.widgets;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.deeep.flycaveman.Assets;
import com.deeep.flycaveman.Core;
import com.deeep.flycaveman.entities.PowerUp;

/**
 * Created by scanevaro on 07/04/2015.
 */
public class PickupsWidget extends Actor {
    private Image stamina, percentage, plus, minus, infinite, one, five;

    public PickupsWidget() {
        stamina = new Image(Assets.staminaText);
        stamina.setSize(stamina.getPrefWidth(), stamina.getPrefHeight());
        stamina.setPosition(-stamina.getWidth() - 20, Core.VIRTUAL_HEIGHT / 2 - (stamina.getHeight() * 2) + 50);
        stamina.setRotation(15);
        percentage = new Image(Assets.percentage);
        percentage.setSize(percentage.getPrefWidth(), percentage.getPrefHeight());
        percentage.setPosition(-percentage.getWidth(), Core.VIRTUAL_HEIGHT / 2 - (percentage.getHeight() / 2) + 50);
        infinite = new Image(Assets.infinite);
        infinite.setSize(infinite.getPrefWidth(), infinite.getPrefHeight());
        infinite.setPosition(-infinite.getWidth() - 45, Core.VIRTUAL_HEIGHT / 2 - (infinite.getHeight() / 2) + 50);
        one = new Image(Assets.one);
        one.setSize(one.getPrefWidth(), one.getPrefHeight());
        one.setPosition(-one.getWidth() - percentage.getWidth(), Core.VIRTUAL_HEIGHT / 2 - (one.getHeight() / 2) + 50);
        five = new Image(Assets.five);
        five.setSize(five.getPrefWidth(), five.getPrefHeight());
        five.setPosition(-five.getWidth() - percentage.getWidth(),
                Core.VIRTUAL_HEIGHT / 2 - (five.getHeight() / 2) + 50);
        plus = new Image(Assets.plus);
        plus.setSize(plus.getPrefWidth(), plus.getPrefHeight());
        plus.setPosition(-plus.getWidth() - one.getWidth() - percentage.getWidth(),
                Core.VIRTUAL_HEIGHT / 2 - (plus.getHeight() / 2) + 50);
        minus = new Image(Assets.minus);
        minus.setSize(minus.getPrefWidth(), minus.getPrefHeight());
        minus.setPosition(-minus.getWidth() - five.getWidth() - percentage.getWidth(),
                Core.VIRTUAL_HEIGHT / 2 - (minus.getHeight() / 2) + 50);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        stamina.act(delta);
        plus.act(delta);
        minus.act(delta);
        infinite.act(delta);
        percentage.act(delta);
        one.act(delta);
        five.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        stamina.draw(batch, parentAlpha);
        plus.draw(batch, parentAlpha);
        minus.draw(batch, parentAlpha);
        infinite.draw(batch, parentAlpha);
        percentage.draw(batch, parentAlpha);
        one.draw(batch, parentAlpha);
        five.draw(batch, parentAlpha);
    }

    public void show(PowerUp.Type type) {
        if (type == PowerUp.Type.VODKA) {
            stamina.addAction(new SequenceAction(
                    Actions.moveTo(20, Core.VIRTUAL_HEIGHT / 2 - (stamina.getHeight() * 2) + 50, 0.3f),
                    Actions.scaleBy(0.25f, 0.25f, 0.2f), Actions.scaleBy(-0.25f, -0.25f, 0.2f),
                    Actions.delay(0.35f),
                    Actions.moveTo(-stamina.getWidth() - 20,
                            Core.VIRTUAL_HEIGHT / 2 - (stamina.getHeight() * 2) + 50, 0.3f)));
            percentage.addAction(new SequenceAction(
                    Actions.moveTo(128, Core.VIRTUAL_HEIGHT / 2 - (percentage.getHeight() / 2) + 50, 0.3f),
                    Actions.scaleBy(0.25f, 0.25f, 0.2f), Actions.scaleBy(-0.25f, -0.25f, 0.2f),
                    Actions.delay(0.35f),
                    Actions.moveTo(-percentage.getWidth(),
                            Core.VIRTUAL_HEIGHT / 2 - (percentage.getHeight() / 2) + 50, 0.3f)));
            five.addAction(new SequenceAction(
                    Actions.moveTo(64, Core.VIRTUAL_HEIGHT / 2 - (five.getHeight() / 2) + 50, 0.3f),
                    Actions.scaleBy(0.25f, 0.25f, 0.2f), Actions.scaleBy(-0.25f, -0.25f, 0.2f),
                    Actions.delay(0.35f),
                    Actions.moveTo(-five.getWidth() - percentage.getWidth(),
                            Core.VIRTUAL_HEIGHT / 2 - (five.getHeight() / 2) + 50, 0.3f)));
            minus.addAction(new SequenceAction(
                    Actions.moveTo(0, Core.VIRTUAL_HEIGHT / 2 - (minus.getHeight() / 2) + 50, 0.3f),
                    Actions.scaleBy(0.25f, 0.25f, 0.2f), Actions.scaleBy(-0.25f, -0.25f, 0.2f),
                    Actions.delay(0.35f),
                    Actions.moveTo(-minus.getWidth() - five.getWidth() - percentage.getWidth(),
                            Core.VIRTUAL_HEIGHT / 2 - (minus.getHeight() / 2) + 50, 0.3f)));
        } else if (type == PowerUp.Type.SPINACH) {
            stamina.addAction(new SequenceAction(
                    Actions.moveTo(20, Core.VIRTUAL_HEIGHT / 2 - (stamina.getHeight() * 2) + 50, 0.3f),
                    Actions.scaleBy(0.25f, 0.25f, 0.2f), Actions.scaleBy(-0.25f, -0.25f, 0.2f),
                    Actions.delay(0.35f),
                    Actions.moveTo(-stamina.getWidth() - 20,
                            Core.VIRTUAL_HEIGHT / 2 - (stamina.getHeight() * 2) + 50, 0.3f)));
            infinite.addAction(new SequenceAction(
                    Actions.moveTo(45, Core.VIRTUAL_HEIGHT / 2 - (infinite.getHeight() / 2) + 50, 0.3f),
                    Actions.scaleBy(0.25f, 0.25f, 0.2f), Actions.scaleBy(-0.25f, -0.25f, 0.2f),
                    Actions.delay(0.35f),
                    Actions.moveTo(-infinite.getWidth() - 45,
                            Core.VIRTUAL_HEIGHT / 2 - (infinite.getHeight() / 2) + 50, 0.3f)));
        } else if (type == PowerUp.Type.SODACAN) {
            stamina.addAction(new SequenceAction(
                    Actions.moveTo(20, Core.VIRTUAL_HEIGHT / 2 - (stamina.getHeight() * 2) + 50, 0.3f),
                    Actions.scaleBy(0.25f, 0.25f, 0.2f), Actions.scaleBy(-0.25f, -0.25f, 0.2f),
                    Actions.delay(0.35f),
                    Actions.moveTo(-stamina.getWidth() - 20,
                            Core.VIRTUAL_HEIGHT / 2 - (stamina.getHeight() * 2) + 50, 0.3f)));
            percentage.addAction(new SequenceAction(
                    Actions.moveTo(128, Core.VIRTUAL_HEIGHT / 2 - (percentage.getHeight() / 2) + 50, 0.3f),
                    Actions.scaleBy(0.25f, 0.25f, 0.2f), Actions.scaleBy(-0.25f, -0.25f, 0.2f),
                    Actions.delay(0.35f),
                    Actions.moveTo(-percentage.getWidth(),
                            Core.VIRTUAL_HEIGHT / 2 - (percentage.getHeight() / 2) + 50, 0.3f)));
            five.addAction(new SequenceAction(
                    Actions.moveTo(64, Core.VIRTUAL_HEIGHT / 2 - (five.getHeight() / 2) + 50, 0.3f),
                    Actions.scaleBy(0.25f, 0.25f, 0.2f), Actions.scaleBy(-0.25f, -0.25f, 0.2f),
                    Actions.delay(0.35f),
                    Actions.moveTo(-five.getWidth() - percentage.getWidth(),
                            Core.VIRTUAL_HEIGHT / 2 - (five.getHeight() / 2) + 50, 0.3f)));
            plus.addAction(new SequenceAction(
                    Actions.moveTo(0, Core.VIRTUAL_HEIGHT / 2 - (plus.getHeight() / 2) + 50, 0.3f),
                    Actions.scaleBy(0.25f, 0.25f, 0.2f), Actions.scaleBy(-0.25f, -0.25f, 0.2f),
                    Actions.delay(0.35f),
                    Actions.moveTo(-plus.getWidth() - five.getWidth() - percentage.getWidth(),
                            Core.VIRTUAL_HEIGHT / 2 - (plus.getHeight() / 2) + 50, 0.3f)));
        } else if (type == PowerUp.Type.MEAT) {
            stamina.addAction(new SequenceAction(
                    Actions.moveTo(20, Core.VIRTUAL_HEIGHT / 2 - (stamina.getHeight() * 2) + 50, 0.3f),
                    Actions.scaleBy(0.25f, 0.25f, 0.2f), Actions.scaleBy(-0.25f, -0.25f, 0.2f),
                    Actions.delay(0.35f),
                    Actions.moveTo(-stamina.getWidth() - 20,
                            Core.VIRTUAL_HEIGHT / 2 - (stamina.getHeight() * 2) + 50, 0.3f)));
            percentage.addAction(new SequenceAction(
                    Actions.moveTo(128, Core.VIRTUAL_HEIGHT / 2 - (percentage.getHeight() / 2) + 50, 0.3f),
                    Actions.scaleBy(0.25f, 0.25f, 0.2f), Actions.scaleBy(-0.25f, -0.25f, 0.2f),
                    Actions.delay(0.35f),
                    Actions.moveTo(-percentage.getWidth(),
                            Core.VIRTUAL_HEIGHT / 2 - (percentage.getHeight() / 2) + 50, 0.3f)));
            one.addAction(new SequenceAction(
                    Actions.moveTo(64, Core.VIRTUAL_HEIGHT / 2 - (one.getHeight() / 2) + 50, 0.3f),
                    Actions.scaleBy(0.25f, 0.25f, 0.2f), Actions.scaleBy(-0.25f, -0.25f, 0.2f),
                    Actions.delay(0.25f),
                    Actions.moveTo(-one.getWidth() - percentage.getWidth(),
                            Core.VIRTUAL_HEIGHT / 2 - (one.getHeight() / 2) + 50, 0.3f)));
            plus.addAction(new SequenceAction(
                    Actions.moveTo(0, Core.VIRTUAL_HEIGHT / 2 - (plus.getHeight() / 2) + 50, 0.3f),
                    Actions.scaleBy(0.25f, 0.25f, 0.2f), Actions.scaleBy(-0.25f, -0.25f, 0.2f),
                    Actions.delay(0.35f),
                    Actions.moveTo(-plus.getWidth() - one.getWidth() - percentage.getWidth(),
                            Core.VIRTUAL_HEIGHT / 2 - (plus.getHeight() / 2) + 50, 0.3f)));
        }
    }
}