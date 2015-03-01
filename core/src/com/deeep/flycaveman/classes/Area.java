package com.deeep.flycaveman.classes;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.deeep.flycaveman.Core;

/**
 * Created by Elmar on 8-2-2015.
 */
public class Area {
    private int area = 0;
    public static final int DESSERT = 0;
    public static final int MOUNTAINS = 1;
    private Weather weather;
    private float x;
    private Theme theme;

    public Area() {
        Theme.initThemes();
        theme = Theme.Jungle;
        weather = new Weather();
    }

    public void update(Body caveman) {
        x = caveman.getPosition().x;
    }

    public void draw(SpriteBatch spriteBatch) {
        weather.update(x);
        theme.background.update(x);
        theme.background.draw(spriteBatch);
        weather.draw(spriteBatch);
        //spriteBatch.draw(areas[area], x - xPosition, 0, Core.BOX2D_VIRTUAL_WIDTH + Core.BOX2D_VIRTUAL_WIDTH / 2, Core.BOX2D_VIRTUAL_HEIGHT);
        //spriteBatch.draw(areas[area], x - xPosition + Core.BOX2D_VIRTUAL_WIDTH + Core.BOX2D_VIRTUAL_WIDTH / 2, 0, Core.BOX2D_VIRTUAL_WIDTH + Core.BOX2D_VIRTUAL_WIDTH / 2, Core.BOX2D_VIRTUAL_HEIGHT);
        //spriteBatch.draw(areas[area], x - xPosition - (Core.BOX2D_VIRTUAL_WIDTH + Core.BOX2D_VIRTUAL_WIDTH / 2), 0, Core.BOX2D_VIRTUAL_WIDTH + Core.BOX2D_VIRTUAL_WIDTH / 2, Core.BOX2D_VIRTUAL_HEIGHT);
    }
}
