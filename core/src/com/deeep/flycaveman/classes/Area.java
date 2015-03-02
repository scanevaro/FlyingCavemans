package com.deeep.flycaveman.classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.deeep.flycaveman.Core;

/**
 * Created by Elmar on 8-2-2015.
 */
public class Area {
    private int area = 0;
    public static final int DESSERT = 0;
    public static final int MOUNTAINS = 1;
    private float x;
    private Theme theme;

    public Area() {
        Theme.initThemes();
        theme = Theme.PreHistoric;
    }

    public void update(Body caveman) {
        x = caveman.getPosition().x;
        if (Gdx.input.isKeyPressed(Input.Keys.NUM_1)) {
            theme = Theme.Jungle;
        } else if (Gdx.input.isKeyPressed(Input.Keys.NUM_2)) {
            theme = Theme.PreHistoric;
        }else if (Gdx.input.isKeyPressed(Input.Keys.NUM_3)) {
            theme = Theme.JunglePreHistoricTransition;
        }
    }

    public void draw(SpriteBatch spriteBatch) {
        float xPosition = x % (Core.BOX2D_VIRTUAL_WIDTH + Core.BOX2D_VIRTUAL_WIDTH / 2);
        theme.background.update(x);
        theme.background.draw(spriteBatch);
        //spriteBatch.draw(areas[area], x - xPosition, 0, Core.BOX2D_VIRTUAL_WIDTH + Core.BOX2D_VIRTUAL_WIDTH / 2, Core.BOX2D_VIRTUAL_HEIGHT);
        //spriteBatch.draw(areas[area], x - xPosition + Core.BOX2D_VIRTUAL_WIDTH + Core.BOX2D_VIRTUAL_WIDTH / 2, 0, Core.BOX2D_VIRTUAL_WIDTH + Core.BOX2D_VIRTUAL_WIDTH / 2, Core.BOX2D_VIRTUAL_HEIGHT);
        //spriteBatch.draw(areas[area], x - xPosition - (Core.BOX2D_VIRTUAL_WIDTH + Core.BOX2D_VIRTUAL_WIDTH / 2), 0, Core.BOX2D_VIRTUAL_WIDTH + Core.BOX2D_VIRTUAL_WIDTH / 2, Core.BOX2D_VIRTUAL_HEIGHT);
    }
}
