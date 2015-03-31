package com.deeep.flycaveman.widgets;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.deeep.flycaveman.Assets;
import com.deeep.flycaveman.Core;

/**
 * Created by scanevaro on 29/03/2015.
 */
public class TutorialWidget extends Actor {
    private Animation animation1, animation2;
    private float stateTime;
    private Image text1, text2;
    public Image background;

    public TutorialWidget() {
        animation1 = new Animation(0.25f, Assets.tutorial0, Assets.tutorial1, Assets.tutorial2, Assets.tutorial3,
                Assets.tutorial4);
        animation1.setPlayMode(Animation.PlayMode.LOOP);
        text1 = new Image(Assets.touchNH);
        text1.setSize(432, 239);
        text1.setOrigin(text1.getWidth() / 2, text1.getHeight() / 2);
        text1.setPosition(0, Core.VIRTUAL_HEIGHT + text1.getHeight());
        text1.addAction(Actions.forever(Actions.sequence(Actions.fadeIn(0.6f), Actions.delay(0.5f), Actions.fadeOut(0.6f))));
        animation2 = new Animation(0.25f, Assets.tutorial20, Assets.tutorial21, Assets.tutorial22, Assets.tutorial23,
                Assets.tutorial24);
        animation2.setPlayMode(Animation.PlayMode.LOOP);
        text2 = new Image(Assets.touch2);
        text2.setSize(432, 239);
        text2.setOrigin(text2.getWidth() / 2, text2.getHeight() / 2);
        text2.setPosition(Core.VIRTUAL_WIDTH / 2, Core.VIRTUAL_HEIGHT + text2.getHeight());
        text2.addAction(Actions.forever(Actions.sequence(Actions.fadeIn(0.6f), Actions.delay(0.5f), Actions.fadeOut(0.6f))));
        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                addAction(Actions.moveTo(0, -Core.VIRTUAL_HEIGHT, 0.25f, Interpolation.linear));
                text1.addAction(Actions.moveTo(0, -text1.getHeight(), 0.2f, Interpolation.linear));
                text2.addAction(Actions.moveTo(Core.VIRTUAL_WIDTH / 2, -text2.getHeight(), 0.2f, Interpolation.linear));
                background.addAction(new SequenceAction(Actions.fadeOut(0.35f, Interpolation.fade),
                        Actions.moveTo(0, -Core.VIRTUAL_HEIGHT, 0.25f, Interpolation.linear)));
                clearListeners();
            }
        });
        background = new Image(Assets.items.findRegion("white"));
        background.setColor(0, 0, 0, 0.45f);
        background.setSize(Core.VIRTUAL_WIDTH, Core.VIRTUAL_HEIGHT);
        background.setPosition(0, 0);
        background.addAction(Actions.alpha(0));
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        stateTime += delta;
        text1.act(delta);
        text2.act(delta);
        background.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        background.draw(batch, parentAlpha);
        batch.setColor(getColor().r, getColor().g, getColor().b, getColor().a * parentAlpha);
        batch.draw(animation1.getKeyFrame(stateTime), getX() + 50, getY() + 346 / 2, 306, 346);
        batch.draw(animation2.getKeyFrame(stateTime), getX() + Core.VIRTUAL_WIDTH / 2 + 306 / 2 - 100, getY() + 346 / 2, 306, 346);
        text1.draw(batch, parentAlpha);
        text2.draw(batch, parentAlpha);
    }

    public void moveDown() {
        addAction(Actions.moveTo(0, 0, 0.4f, Interpolation.linear));
        text1.addAction(Actions.moveTo(0, 25, 0.4f, Interpolation.linear));
        text2.addAction(Actions.moveTo(Core.VIRTUAL_WIDTH / 2, 25, 0.4f, Interpolation.linear));
        background.addAction(Actions.alpha(0.45f, 0.4f, Interpolation.fade));
    }
}