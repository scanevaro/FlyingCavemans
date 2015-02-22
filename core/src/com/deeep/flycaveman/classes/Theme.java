package com.deeep.flycaveman.classes;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Elmar on 2/18/2015.
 */
public class Theme {
    public static Theme PreHistoric;
    public Background background = new Background();

    public Theme() {

    }

    public void setTheme(Theme theme) {
        this.background = theme.background;
    }

    public void update(float x){

    }

    public void draw(SpriteBatch spriteBatch){
        this.background.draw(spriteBatch);
    }

    public static void initThemes() {
        Theme.PreHistoric = new Theme();
        Background temp = Theme.PreHistoric.background;
        temp.setLayer(3, Assets.preHistoric_layer_1, 0);
        temp.setLayer(2, Assets.preHistoric_layer_2, 0.2f);
        temp.setLayer(1, Assets.preHistoric_layer_3, 0.4f);
    }
}
