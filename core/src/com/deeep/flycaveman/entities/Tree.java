package com.deeep.flycaveman.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.deeep.flycaveman.Assets;

/**
 * Created by Elmar on 22-3-2015.
 */
public class Tree implements Entity{
    private Sprite sprite;

    public Tree(float x, float y) {
        sprite = new Sprite(Assets.tree);
        sprite.setPosition(x,y);
        sprite.setSize(4,8);
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);

    }

    @Override
    public void draw(Batch batch) {
        sprite.draw(batch);

    }

    @Override
    public void update(float delta) {
    }
}
