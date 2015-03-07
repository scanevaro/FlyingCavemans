package com.deeep.flycaveman.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.deeep.flycaveman.Assets;

/**
 * Created by scanevaro on 06/03/2015.
 */
public class Title implements Entity {
    private float sizeX = 8, sizeY = 4.0f, positionX = 18, positionY = 9;

    public Sprite title, touchNHold;

    public Title() {
        title = new Sprite(Assets.title);
        title.setSize(sizeX * 2, sizeY * 2);
        title.setOrigin(title.getWidth() / 2, title.getHeight() / 2);
        title.setPosition(positionX - title.getWidth() / 2, positionY - title.getHeight() / 2);

        touchNHold = new Sprite(Assets.touchNH);
        touchNHold.setSize(16, 3.5f);
        touchNHold.setOrigin(touchNHold.getWidth() / 2, touchNHold.getHeight() / 2);
        touchNHold.setPosition(10.0f, 0.5f);
    }

    @Override
    public void draw(Batch batch) {
        title.draw(batch);

        touchNHold.draw(batch);
    }
}