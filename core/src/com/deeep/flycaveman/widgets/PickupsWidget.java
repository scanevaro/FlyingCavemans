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
    private Image stamina, percentage, plus, minus, infinite, one, five, coin, oneCoin, twoCoin, threeCoin, fourCoin;

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
        coin = new Image(Assets.coinText);
        coin.setSize(coin.getPrefWidth(), coin.getPrefHeight());
        coin.setPosition(-coin.getWidth() - 20, Core.VIRTUAL_HEIGHT / 2 - (coin.getHeight() * 2) + 50);
        oneCoin = new Image(Assets.oneCoin);
        oneCoin.setSize(oneCoin.getPrefWidth(), oneCoin.getPrefHeight());
        oneCoin.setPosition(-oneCoin.getWidth() - 20, Core.VIRTUAL_HEIGHT / 2 - (oneCoin.getHeight() / 2) + 50);
        twoCoin = new Image(Assets.twoCoin);
        twoCoin.setSize(twoCoin.getPrefWidth(), twoCoin.getPrefHeight());
        twoCoin.setPosition(-twoCoin.getWidth() - 20, Core.VIRTUAL_HEIGHT / 2 - (twoCoin.getHeight() / 2) + 50);
        threeCoin = new Image(Assets.threeCoin);
        threeCoin.setSize(threeCoin.getPrefWidth(), threeCoin.getPrefHeight());
        threeCoin.setPosition(-threeCoin.getWidth() - 20, Core.VIRTUAL_HEIGHT / 2 - (threeCoin.getHeight() / 2) + 50);
        fourCoin = new Image(Assets.fourCoin);
        fourCoin.setSize(fourCoin.getPrefWidth(), fourCoin.getPrefHeight());
        fourCoin.setPosition(-fourCoin.getWidth() - 20, Core.VIRTUAL_HEIGHT / 2 - (fourCoin.getHeight() / 2) + 50);
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
        coin.act(delta);
        oneCoin.act(delta);
        twoCoin.act(delta);
        threeCoin.act(delta);
        fourCoin.act(delta);
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
        coin.draw(batch, parentAlpha);
        oneCoin.draw(batch, parentAlpha);
        twoCoin.draw(batch, parentAlpha);
        threeCoin.draw(batch, parentAlpha);
        fourCoin.draw(batch, parentAlpha);
    }

    public void show(int coinStreak) {
        switch (coinStreak) {
            case 0:
                coin.addAction(new SequenceAction(
                        Actions.moveTo(20, Core.VIRTUAL_HEIGHT / 2 - (coin.getHeight() * 2) + 50, 0.2f),
                        Actions.scaleBy(0.25f, 0.25f, 0.2f), Actions.scaleBy(-0.25f, -0.25f, 0.2f),
                        Actions.delay(0.35f),
                        Actions.moveTo(-coin.getWidth() - 20,
                                Core.VIRTUAL_HEIGHT / 2 - (coin.getHeight() * 2) + 50, 0.2f)));
                oneCoin.addAction(new SequenceAction(
                        Actions.moveTo(64, Core.VIRTUAL_HEIGHT / 2 - (oneCoin.getHeight() / 2) + 50, 0.2f),
                        Actions.scaleBy(0.25f, 0.25f, 0.2f), Actions.scaleBy(-0.25f, -0.25f, 0.2f),
                        Actions.delay(0.35f),
                        Actions.moveTo(-oneCoin.getWidth(),
                                Core.VIRTUAL_HEIGHT / 2 - (oneCoin.getHeight() / 2) + 50, 0.2f)));
                plus.addAction(new SequenceAction(
                        Actions.moveTo(0, Core.VIRTUAL_HEIGHT / 2 - (plus.getHeight() / 2) + 50, 0.2f),
                        Actions.scaleBy(0.25f, 0.25f, 0.2f), Actions.scaleBy(-0.25f, -0.25f, 0.2f),
                        Actions.delay(0.35f),
                        Actions.moveTo(-plus.getWidth() - oneCoin.getWidth(),
                                Core.VIRTUAL_HEIGHT / 2 - (plus.getHeight() / 2) + 50, 0.2f)));
                break;
            case 1:
                coin.addAction(new SequenceAction(
                        Actions.moveTo(20, Core.VIRTUAL_HEIGHT / 2 - (coin.getHeight() * 2) + 50, 0.2f),
                        Actions.scaleBy(0.35f, 0.35f, 0.2f), Actions.scaleBy(-0.35f, -0.35f, 0.2f),
                        Actions.delay(0.35f),
                        Actions.moveTo(-coin.getWidth() - 20,
                                Core.VIRTUAL_HEIGHT / 2 - (coin.getHeight() * 2) + 50, 0.2f)));
                twoCoin.addAction(new SequenceAction(
                        Actions.moveTo(64, Core.VIRTUAL_HEIGHT / 2 - (twoCoin.getHeight() / 2) + 50, 0.2f),
                        Actions.scaleBy(0.35f, 0.35f, 0.2f), Actions.scaleBy(-0.35f, -0.35f, 0.2f),
                        Actions.delay(0.35f),
                        Actions.moveTo(-twoCoin.getWidth(),
                                Core.VIRTUAL_HEIGHT / 2 - (twoCoin.getHeight() / 2) + 50, 0.2f)));
                plus.addAction(new SequenceAction(
                        Actions.moveTo(0, Core.VIRTUAL_HEIGHT / 2 - (plus.getHeight() / 2) + 50, 0.2f),
                        Actions.scaleBy(0.35f, 0.35f, 0.2f), Actions.scaleBy(-0.35f, -0.35f, 0.2f),
                        Actions.delay(0.35f),
                        Actions.moveTo(-plus.getWidth() - twoCoin.getWidth(),
                                Core.VIRTUAL_HEIGHT / 2 - (plus.getHeight() / 2) + 50, 0.2f)));
                break;
            case 2:
                coin.addAction(new SequenceAction(
                        Actions.moveTo(20, Core.VIRTUAL_HEIGHT / 2 - (coin.getHeight() * 2) + 50, 0.2f),
                        Actions.scaleBy(0.45f, 0.45f, 0.2f), Actions.scaleBy(-0.45f, -0.45f, 0.2f),
                        Actions.delay(0.35f),
                        Actions.moveTo(-coin.getWidth() - 20,
                                Core.VIRTUAL_HEIGHT / 2 - (coin.getHeight() * 2) + 50, 0.3f)));
                threeCoin.addAction(new SequenceAction(
                        Actions.moveTo(64, Core.VIRTUAL_HEIGHT / 2 - (threeCoin.getHeight() / 2) + 50, 0.2f),
                        Actions.scaleBy(0.45f, 0.45f, 0.2f), Actions.scaleBy(-0.25f, -0.45f, 0.2f),
                        Actions.delay(0.35f),
                        Actions.moveTo(-threeCoin.getWidth(),
                                Core.VIRTUAL_HEIGHT / 2 - (threeCoin.getHeight() / 2) + 50, 0.2f)));
                plus.addAction(new SequenceAction(
                        Actions.moveTo(0, Core.VIRTUAL_HEIGHT / 2 - (plus.getHeight() / 2) + 50, 0.2f),
                        Actions.scaleBy(0.45f, 0.45f, 0.2f), Actions.scaleBy(-0.25f, -0.45f, 0.2f),
                        Actions.delay(0.35f),
                        Actions.moveTo(-plus.getWidth() - threeCoin.getWidth(),
                                Core.VIRTUAL_HEIGHT / 2 - (plus.getHeight() / 2) + 50, 0.2f)));
                break;
            case 3:
                coin.addAction(new SequenceAction(
                        Actions.moveTo(20, Core.VIRTUAL_HEIGHT / 2 - (coin.getHeight() * 2) + 50, 0.2f),
                        Actions.scaleBy(0.55f, 0.55f, 0.2f), Actions.scaleBy(-0.55f, -0.55f, 0.2f),
                        Actions.delay(0.35f),
                        Actions.moveTo(-coin.getWidth() - 20,
                                Core.VIRTUAL_HEIGHT / 2 - (coin.getHeight() * 2) + 50, 0.2f)));
                fourCoin.addAction(new SequenceAction(
                        Actions.moveTo(128, Core.VIRTUAL_HEIGHT / 2 - (fourCoin.getHeight() / 2) + 50, 0.2f),
                        Actions.scaleBy(0.55f, 0.55f, 0.2f), Actions.scaleBy(-0.55f, -0.55f, 0.2f),
                        Actions.delay(0.35f),
                        Actions.moveTo(-fourCoin.getWidth(),
                                Core.VIRTUAL_HEIGHT / 2 - (fourCoin.getHeight() / 2) + 50, 0.2f)));
                plus.addAction(new SequenceAction(
                        Actions.moveTo(0, Core.VIRTUAL_HEIGHT / 2 - (plus.getHeight() / 2) + 50, 0.2f),
                        Actions.scaleBy(0.55f, 0.55f, 0.2f), Actions.scaleBy(-0.55f, -0.55f, 0.2f),
                        Actions.delay(0.35f),
                        Actions.moveTo(-plus.getWidth() - fourCoin.getWidth(),
                                Core.VIRTUAL_HEIGHT / 2 - (plus.getHeight() / 2) + 50, 0.2f)));
                break;
        }
    }

    public void show(PowerUp.Type type) {
        if (type == PowerUp.Type.VODKA) {
            stamina.addAction(new SequenceAction(
                    Actions.moveTo(20, Core.VIRTUAL_HEIGHT / 2 - (stamina.getHeight() * 2) + 50, 0.2f),
                    Actions.scaleBy(0.25f, 0.25f, 0.2f), Actions.scaleBy(-0.25f, -0.25f, 0.2f),
                    Actions.delay(0.35f),
                    Actions.moveTo(-stamina.getWidth() - 20,
                            Core.VIRTUAL_HEIGHT / 2 - (stamina.getHeight() * 2) + 50, 0.2f)));
            percentage.addAction(new SequenceAction(
                    Actions.moveTo(128, Core.VIRTUAL_HEIGHT / 2 - (percentage.getHeight() / 2) + 50, 0.2f),
                    Actions.scaleBy(0.25f, 0.25f, 0.2f), Actions.scaleBy(-0.25f, -0.25f, 0.2f),
                    Actions.delay(0.35f),
                    Actions.moveTo(-percentage.getWidth(),
                            Core.VIRTUAL_HEIGHT / 2 - (percentage.getHeight() / 2) + 50, 0.2f)));
            five.addAction(new SequenceAction(
                    Actions.moveTo(64, Core.VIRTUAL_HEIGHT / 2 - (five.getHeight() / 2) + 50, 0.2f),
                    Actions.scaleBy(0.25f, 0.25f, 0.2f), Actions.scaleBy(-0.25f, -0.25f, 0.2f),
                    Actions.delay(0.35f),
                    Actions.moveTo(-five.getWidth() - percentage.getWidth(),
                            Core.VIRTUAL_HEIGHT / 2 - (five.getHeight() / 2) + 50, 0.3f)));
            minus.addAction(new SequenceAction(
                    Actions.moveTo(0, Core.VIRTUAL_HEIGHT / 2 - (minus.getHeight() / 2) + 50, 0.2f),
                    Actions.scaleBy(0.25f, 0.25f, 0.2f), Actions.scaleBy(-0.25f, -0.25f, 0.2f),
                    Actions.delay(0.35f),
                    Actions.moveTo(-minus.getWidth() - five.getWidth() - percentage.getWidth(),
                            Core.VIRTUAL_HEIGHT / 2 - (minus.getHeight() / 2) + 50, 0.2f)));
        } else if (type == PowerUp.Type.BEER) {
            stamina.addAction(new SequenceAction(
                    Actions.moveTo(20, Core.VIRTUAL_HEIGHT / 2 - (stamina.getHeight() * 2) + 50, 0.2f),
                    Actions.scaleBy(0.25f, 0.25f, 0.2f), Actions.scaleBy(-0.25f, -0.25f, 0.2f),
                    Actions.delay(0.35f),
                    Actions.moveTo(-stamina.getWidth() - 20,
                            Core.VIRTUAL_HEIGHT / 2 - (stamina.getHeight() * 2) + 50, 0.2f)));
            percentage.addAction(new SequenceAction(
                    Actions.moveTo(128, Core.VIRTUAL_HEIGHT / 2 - (percentage.getHeight() / 2) + 50, 0.2f),
                    Actions.scaleBy(0.25f, 0.25f, 0.2f), Actions.scaleBy(-0.25f, -0.25f, 0.2f),
                    Actions.delay(0.35f),
                    Actions.moveTo(-percentage.getWidth(),
                            Core.VIRTUAL_HEIGHT / 2 - (percentage.getHeight() / 2) + 50, 0.2f)));
            five.addAction(new SequenceAction(
                    Actions.moveTo(64, Core.VIRTUAL_HEIGHT / 2 - (five.getHeight() / 2) + 50, 0.2f),
                    Actions.scaleBy(0.25f, 0.25f, 0.2f), Actions.scaleBy(-0.25f, -0.25f, 0.2f),
                    Actions.delay(0.35f),
                    Actions.moveTo(-five.getWidth() - percentage.getWidth(),
                            Core.VIRTUAL_HEIGHT / 2 - (five.getHeight() / 2) + 50, 0.3f)));
            minus.addAction(new SequenceAction(
                    Actions.moveTo(0, Core.VIRTUAL_HEIGHT / 2 - (minus.getHeight() / 2) + 50, 0.2f),
                    Actions.scaleBy(0.25f, 0.25f, 0.2f), Actions.scaleBy(-0.25f, -0.25f, 0.2f),
                    Actions.delay(0.35f),
                    Actions.moveTo(-minus.getWidth() - five.getWidth() - percentage.getWidth(),
                            Core.VIRTUAL_HEIGHT / 2 - (minus.getHeight() / 2) + 50, 0.2f)));
        } else if (type == PowerUp.Type.SPINACH) {
            stamina.addAction(new SequenceAction(
                    Actions.moveTo(20, Core.VIRTUAL_HEIGHT / 2 - (stamina.getHeight() * 2) + 50, 0.2f),
                    Actions.scaleBy(0.25f, 0.25f, 0.2f), Actions.scaleBy(-0.25f, -0.25f, 0.2f),
                    Actions.delay(0.35f),
                    Actions.moveTo(-stamina.getWidth() - 20,
                            Core.VIRTUAL_HEIGHT / 2 - (stamina.getHeight() * 2) + 50, 0.2f)));
            infinite.addAction(new SequenceAction(
                    Actions.moveTo(45, Core.VIRTUAL_HEIGHT / 2 - (infinite.getHeight() / 2) + 50, 0.2f),
                    Actions.scaleBy(0.25f, 0.25f, 0.2f), Actions.scaleBy(-0.25f, -0.25f, 0.2f),
                    Actions.delay(0.35f),
                    Actions.moveTo(-infinite.getWidth() - 45,
                            Core.VIRTUAL_HEIGHT / 2 - (infinite.getHeight() / 2) + 50, 0.2f)));
        } else if (type == PowerUp.Type.SODACAN) {
            stamina.addAction(new SequenceAction(
                    Actions.moveTo(20, Core.VIRTUAL_HEIGHT / 2 - (stamina.getHeight() * 2) + 50, 0.2f),
                    Actions.scaleBy(0.25f, 0.25f, 0.2f), Actions.scaleBy(-0.25f, -0.25f, 0.2f),
                    Actions.delay(0.35f),
                    Actions.moveTo(-stamina.getWidth() - 20,
                            Core.VIRTUAL_HEIGHT / 2 - (stamina.getHeight() * 2) + 50, 0.2f)));
            percentage.addAction(new SequenceAction(
                    Actions.moveTo(128, Core.VIRTUAL_HEIGHT / 2 - (percentage.getHeight() / 2) + 50, 0.2f),
                    Actions.scaleBy(0.25f, 0.25f, 0.2f), Actions.scaleBy(-0.25f, -0.25f, 0.2f),
                    Actions.delay(0.35f),
                    Actions.moveTo(-percentage.getWidth(),
                            Core.VIRTUAL_HEIGHT / 2 - (percentage.getHeight() / 2) + 50, 0.2f)));
            five.addAction(new SequenceAction(
                    Actions.moveTo(64, Core.VIRTUAL_HEIGHT / 2 - (five.getHeight() / 2) + 50, 0.2f),
                    Actions.scaleBy(0.25f, 0.25f, 0.2f), Actions.scaleBy(-0.25f, -0.25f, 0.2f),
                    Actions.delay(0.35f),
                    Actions.moveTo(-five.getWidth() - percentage.getWidth(),
                            Core.VIRTUAL_HEIGHT / 2 - (five.getHeight() / 2) + 50, 0.2f)));
            plus.addAction(new SequenceAction(
                    Actions.moveTo(0, Core.VIRTUAL_HEIGHT / 2 - (plus.getHeight() / 2) + 50, 0.2f),
                    Actions.scaleBy(0.25f, 0.25f, 0.2f), Actions.scaleBy(-0.25f, -0.25f, 0.2f),
                    Actions.delay(0.35f),
                    Actions.moveTo(-plus.getWidth() - five.getWidth() - percentage.getWidth(),
                            Core.VIRTUAL_HEIGHT / 2 - (plus.getHeight() / 2) + 50, 0.2f)));
        } else if (type == PowerUp.Type.MEAT) {
            stamina.addAction(new SequenceAction(
                    Actions.moveTo(20, Core.VIRTUAL_HEIGHT / 2 - (stamina.getHeight() * 2) + 50, 0.2f),
                    Actions.scaleBy(0.25f, 0.25f, 0.2f), Actions.scaleBy(-0.25f, -0.25f, 0.2f),
                    Actions.delay(0.35f),
                    Actions.moveTo(-stamina.getWidth() - 20,
                            Core.VIRTUAL_HEIGHT / 2 - (stamina.getHeight() * 2) + 50, 0.2f)));
            percentage.addAction(new SequenceAction(
                    Actions.moveTo(128, Core.VIRTUAL_HEIGHT / 2 - (percentage.getHeight() / 2) + 50, 0.2f),
                    Actions.scaleBy(0.25f, 0.25f, 0.2f), Actions.scaleBy(-0.25f, -0.25f, 0.2f),
                    Actions.delay(0.35f),
                    Actions.moveTo(-percentage.getWidth(),
                            Core.VIRTUAL_HEIGHT / 2 - (percentage.getHeight() / 2) + 50, 0.2f)));
            one.addAction(new SequenceAction(
                    Actions.moveTo(64, Core.VIRTUAL_HEIGHT / 2 - (one.getHeight() / 2) + 50, 0.2f),
                    Actions.scaleBy(0.25f, 0.25f, 0.2f), Actions.scaleBy(-0.25f, -0.25f, 0.2f),
                    Actions.delay(0.25f),
                    Actions.moveTo(-one.getWidth() - percentage.getWidth(),
                            Core.VIRTUAL_HEIGHT / 2 - (one.getHeight() / 2) + 50, 0.2f)));
            plus.addAction(new SequenceAction(
                    Actions.moveTo(0, Core.VIRTUAL_HEIGHT / 2 - (plus.getHeight() / 2) + 50, 0.2f),
                    Actions.scaleBy(0.25f, 0.25f, 0.2f), Actions.scaleBy(-0.25f, -0.25f, 0.2f),
                    Actions.delay(0.35f),
                    Actions.moveTo(-plus.getWidth() - one.getWidth() - percentage.getWidth(),
                            Core.VIRTUAL_HEIGHT / 2 - (plus.getHeight() / 2) + 50, 0.2f)));
        }
    }
}