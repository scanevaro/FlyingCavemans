package com.deeep.flycaveman.classes;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by Elmar on 2/18/2015.
 */
public class Theme {
    public static Theme PreHistoric;
    public static final int PreHistoricNr = 1;
    public static Theme PreHistoricJungleTransition;
    public static final int PreHistoricJungleNr = 2;
    public static Theme Jungle;
    public static final int JungleNr = 3;
    public static Theme JunglePreHistoricTransition;
    public static final int JunglePreHistoricNr = 4;

    public Background background = new Background();

    public boolean transition = false;
    public int transitionNr;
    public int nr;

    public Theme() {

    }

    public void setTheme(Theme theme) {
        this.background = theme.background;
    }

    public void update(float x) {

    }

    public void draw(SpriteBatch spriteBatch) {
        this.background.draw(spriteBatch);
    }

    public static void initThemes() {
        Theme.PreHistoric = new Theme();
        Background temp = Theme.PreHistoric.background;
        temp.setLayer(3, Assets.preHistoric_layer_1, 0);
        temp.setLayer(2, Assets.preHistoric_layer_2, 0.4f);
        temp.setLayer(1, Assets.preHistoric_layer_3, 0.8f);
        Theme.PreHistoric.nr = Theme.PreHistoricNr;

        Theme.Jungle = new Theme();
        temp = Theme.Jungle.background;
        temp.setLayer(3, Assets.jungle_layer_1, 0);
        temp.setLayer(2, Assets.jungle_layer_2, 0.4f);
        temp.setLayer(1, Assets.jungle_layer_3, 0.5f);
        Theme.Jungle.nr = Theme.JungleNr;

        Theme.PreHistoricJungleTransition = new Theme();
        temp = Theme.PreHistoricJungleTransition.background;
        temp.setLayer(3, Assets.historic_jungle_layer_1, 0);
        temp.setLayer(2, Assets.historic_jungle_layer_2, 0.4f);
        temp.setLayer(1, Assets.historic_jungle_layer_3, 0.5f);
        Theme.PreHistoricJungleTransition.transition = true;
        Theme.PreHistoricJungleTransition.transitionNr = Theme.JungleNr;
        Theme.PreHistoricJungleTransition.nr = Theme.PreHistoricJungleNr;

        Theme.JunglePreHistoricTransition = new Theme();
        temp = Theme.JunglePreHistoricTransition.background;
        temp.setLayer(3, Assets.jungle_historic_layer_1, 0);
        temp.setLayer(2, Assets.jungle_historic_layer_2, 0.4f);
        temp.setLayer(1, Assets.jungle_historic_layer_3, 0.5f);
        Theme.JunglePreHistoricTransition.transition = true;
        Theme.JunglePreHistoricTransition.transitionNr = Theme.PreHistoricNr;
        Theme.JunglePreHistoricTransition.nr = Theme.JunglePreHistoricNr;
    }

    public static Theme getTheme(int nr) {
        switch (nr) {
            case Theme.JunglePreHistoricNr:
                return Theme.JunglePreHistoricTransition;
            case Theme.JungleNr:
                return Theme.Jungle;
            case Theme.PreHistoricNr:
                return Theme.PreHistoric;
            case Theme.PreHistoricJungleNr:
                return Theme.PreHistoricJungleTransition;
        }
        return Theme.PreHistoric;
    }
}
