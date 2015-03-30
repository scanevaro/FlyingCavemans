package com.deeep.flycaveman.widgets;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;
import com.deeep.flycaveman.Assets;
import com.deeep.flycaveman.Core;

/**
 * Created by scanevaro on 29/03/2015.
 */
public class TutorialWidget extends Actor {
    private Animation animation;
    private float stateTime;
    private Image text;
    private FocusListener focusListener;

    public TutorialWidget() {
        animation = new Animation(0.25f, Assets.tutorial0, Assets.tutorial1, Assets.tutorial2, Assets.tutorial3,
                Assets.tutorial4);
        animation.setPlayMode(Animation.PlayMode.LOOP);
        text = new Image(Assets.touchNH);
        text.setSize(632, 128);
        text.setOrigin(text.getWidth() / 2, text.getHeight() / 2);
        text.setPosition(Core.VIRTUAL_WIDTH / 2 - text.getWidth() / 2, Core.VIRTUAL_HEIGHT + text.getHeight());
        text.addAction(Actions.forever(Actions.sequence(Actions.fadeOut(0.6f), Actions.delay(0.2f), Actions.fadeIn(0.6f))));

        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                addAction(Actions.moveTo(getX(), -getHeight(), 0.4f, Interpolation.linear));
                text.addAction(Actions.moveTo(Core.VIRTUAL_WIDTH / 2 - text.getWidth() / 2, -text.getHeight(), 0.2f, Interpolation.linear));
            }
        });
        focusListener = new FocusListener() {
            public void keyboardFocusChanged(FocusEvent event, Actor actor, boolean focused) {
                if (!focused) focusChanged(event);
            }

            public void scrollFocusChanged(FocusEvent event, Actor actor, boolean focused) {
                if (!focused) focusChanged(event);
            }

            private void focusChanged(FocusEvent event) {
                event.cancel();
                addAction(Actions.moveTo(getX(), -getHeight(), 0.4f, Interpolation.linear));
            }
        };
        addListener(focusListener);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        stateTime += delta;
        text.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(getColor().r, getColor().g, getColor().b, getColor().a * parentAlpha);
        batch.draw(animation.getKeyFrame(stateTime), getX(), getY(), getWidth(), getHeight());
        text.draw(batch, parentAlpha);
    }

    public void moveDown() {
        addAction(Actions.moveTo(getX(), Core.VIRTUAL_HEIGHT / 2 - getHeight() / 2, 0.4f, Interpolation.linear));
        text.addAction(Actions.moveTo(150, 70, 0.4f, Interpolation.linear));
    }
}
