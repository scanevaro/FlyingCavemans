package com.deeep.flycaveman.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RepeatAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.deeep.flycaveman.Assets;

/**
 * Created by Elmar on 22-3-2015.
 */
public class Tree implements Entity {
    private Sprite sprite;
    private Image glow;

    public Tree(float x, float y) {
        sprite = new Sprite(Assets.tree);
        sprite.setPosition(x, y);
        sprite.setSize(4, 8);
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
        glow = new Image(Assets.glow);
        glow.setPosition(x, y + 2);
        glow.setSize(4, 4);
        glow.setOrigin(glow.getWidth() / 2, glow.getHeight() / 2);
        RepeatAction repeatAction = new RepeatAction();
        repeatAction.setAction(new SequenceAction(Actions.scaleTo(1.5f, 1.5f, 0.5f), Actions.scaleTo(1.0f, 1.0f, 0.5f)));
        repeatAction.setCount(RepeatAction.FOREVER);
        glow.addAction(repeatAction);
    }

    @Override
    public void draw(Batch batch) {
        glow.draw(batch, 1);
        sprite.draw(batch);
    }

    @Override
    public void update(float delta) {
        glow.act(delta);
    }
}
