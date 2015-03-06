package com.deeep.flycaveman.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.deeep.flycaveman.Assets;

/**
 * Created by scanevaro on 06/03/2015.
 */
public class Title implements Entity {
    private float sizeX = 8, sizeY = 4.0f, positionX = 18, positionY = 9;

    public Sprite sprite;

    public Title() {
        sprite = new Sprite(Assets.title);
        sprite.setSize(sizeX * 2, sizeY * 2);
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
    }

    @Override
    public void draw(Batch batch) {
        sprite.setPosition(positionX - sprite.getWidth() / 2, positionY - sprite.getHeight() / 2);
        sprite.draw(batch);
    }
}