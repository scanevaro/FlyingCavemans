package com.deeep.flycaveman.widgets;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.deeep.flycaveman.Assets;
import com.deeep.flycaveman.Core;

/**
 * Created by scanevaro on 29/03/2015.
 */
public class TutorialWidget extends Actor {
    private Animation animation;
    private float stateTime;
    private Image text;
    public Image background;

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
                addAction(Actions.moveTo(0, -Core.VIRTUAL_HEIGHT, 0.25f, Interpolation.linear));
                text.addAction(Actions.moveTo(Core.VIRTUAL_WIDTH / 2 - text.getWidth() / 2, -text.getHeight(), 0.2f, Interpolation.linear));
                background.addAction(Actions.moveTo(0, -Core.VIRTUAL_HEIGHT, 0.25f, Interpolation.linear));
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
        text.act(delta);
        background.act(delta);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        background.draw(batch, parentAlpha);
        batch.setColor(getColor().r, getColor().g, getColor().b, getColor().a * parentAlpha);
        batch.draw(animation.getKeyFrame(stateTime), getX() + Core.VIRTUAL_WIDTH / 2 - 306 / 2, getY() + 346 / 2, 306, 346);
        text.draw(batch, parentAlpha);
    }

    public void moveDown() {
        addAction(Actions.moveTo(0, 0, 0.4f, Interpolation.linear));
        text.addAction(Actions.moveTo(150, 70, 0.4f, Interpolation.linear));
        background.addAction(Actions.alpha(0.45f, 0.4f, Interpolation.fade));
    }
}