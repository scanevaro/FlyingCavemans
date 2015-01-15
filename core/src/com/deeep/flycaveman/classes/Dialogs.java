package com.deeep.flycaveman.classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.deeep.flycaveman.Core;
import com.deeep.flycaveman.screens.AbstracScreen;
import com.deeep.flycaveman.screens.GameScreen;

/**
 * Created by scanevaro on 14/01/2015.
 */
public class Dialogs {
    public Window window;

    public void update(AbstracScreen screen) {
        if (!Core.dialogOpen) {
            Core.dialogOpen = true;
            build(screen);
        } else {
            Core.dialogOpen = false;
            remove();
        }
    }

    private void build(AbstracScreen screen) {
        /**
         if(screen instanceof MainMenuScreen)
         return window = new Dialog("Quit Game?", Assets.getAssets().getSkin());
         */

        if (screen instanceof GameScreen) {
            if (window != null) {
                window.setPosition(Core.VIRTUAL_WIDTH / 2 - window.getWidth() / 2, Core.VIRTUAL_HEIGHT / 2);
                screen.stage.addActor(window);
                return;
            }

            window = new Window("Pause", Assets.getAssets().getSkin());

            TextButton noButton = new TextButton("Resume", Assets.getAssets().getSkin());
            noButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Core.dialogOpen = false;
                    window.remove();
                }
            });
            noButton.setPosition(noButton.getWidth() / 4, noButton.getHeight() / 2);

            //add to dialog
            window.addActor(noButton);

            TextButton backButton = new TextButton("Main Menu", Assets.getAssets().getSkin());
            backButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    /**
                     * Go Back and show Interstitial Ad
                     * game.setScreen(new MainMenuScreen());
                     */
                    Gdx.app.exit();
                }
            });
            backButton.setPosition(noButton.getWidth() / 4 + 20 + noButton.getWidth(), backButton.getHeight() / 2);
            //add to dialog
            window.addActor(backButton);

            window.setSize(noButton.getWidth() / 4 + 20 + backButton.getWidth() / 4 + noButton.getWidth() + backButton.getWidth(), backButton.getHeight() * 2.5f);
            window.setPosition(Core.VIRTUAL_WIDTH / 2 - window.getWidth() / 2, Core.VIRTUAL_HEIGHT / 2);

            /**Add to screen stage*/
            screen.stage.addActor(window);
        }
    }

    private void remove() {
        if (window != null)
            window.remove();
    }
}