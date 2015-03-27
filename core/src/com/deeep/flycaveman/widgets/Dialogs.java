package com.deeep.flycaveman.widgets;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Timer;
import com.deeep.flycaveman.Assets;
import com.deeep.flycaveman.Core;
import com.deeep.flycaveman.Settings;
import com.deeep.flycaveman.screens.GameScreen;

/**
 * Created by scanevaro on 14/01/2015.
 */
public class Dialogs {
    public Window window;
    private ImageButton retryButton, muteButton, resumeButton;

    public void update(Core game) {
        if (!Core.dialogOpen) {
            Core.dialogOpen = true;
            build(game);
        } else {
            Core.dialogOpen = false;
            remove();
        }
    }

    private void build(final Core game) {
        if (game.screen instanceof GameScreen) {
            if (window != null) {
                if (Settings.soundEnabled) muteButton.setChecked(false);
                else muteButton.setChecked(true);

                window.setPosition(Core.VIRTUAL_WIDTH / 2 - window.getWidth() / 2, Core.VIRTUAL_HEIGHT / 2);
                game.screen.stage.addActor(window);
                return;
            }

            window = new Window("Pause", Assets.skin);
            window.setSize(480, 250);

            ImageButton.ImageButtonStyle retryStyle = new ImageButton.ImageButtonStyle();
            retryStyle.imageUp = new TextureRegionDrawable(Assets.restartButton);
            retryStyle.imageUp.setMinWidth(128);
            retryStyle.imageUp.setMinHeight(128);
            retryStyle.imageDown = new TextureRegionDrawable(Assets.buttonBroken);
            retryStyle.imageDown.setMinWidth(178);
            retryStyle.imageDown.setMinHeight(178);
            retryButton = new ImageButton(retryStyle);
            retryButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    GameScreen screen = (GameScreen) game.screen;
                    screen.darkness.addAction(Actions.fadeIn(0.4f));
                    screen.stage.addActor(screen.darkness);
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            game.setScreen(new GameScreen(game));
                        }
                    }, 0.4f);
                    window.remove();
                    Core.dialogOpen = false;
                }
            });
            retryButton.setSize(178, 178);
            retryButton.setPosition(0, 0);
            window.addActor(retryButton);

            ImageButton.ImageButtonStyle muteStyle = new ImageButton.ImageButtonStyle();
            muteStyle.imageUp = new TextureRegionDrawable(Assets.mute1Button);
            muteStyle.imageUp.setMinWidth(128);
            muteStyle.imageUp.setMinHeight(128);
            muteStyle.imageChecked = new TextureRegionDrawable(Assets.mute2Button);
            muteStyle.imageChecked.setMinWidth(128);
            muteStyle.imageChecked.setMinHeight(128);
            muteStyle.imageDown = new TextureRegionDrawable(Assets.buttonBroken);
            muteStyle.imageDown.setMinWidth(178);
            muteStyle.imageDown.setMinHeight(178);
            muteButton = new ImageButton(muteStyle);
            muteButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (Settings.soundEnabled) {
                        Settings.soundEnabled = false;
                        muteButton.setChecked(true);
                    } else {
                        Settings.soundEnabled = true;
                        muteButton.setChecked(false);
                    }
                }
            });
            muteButton.setSize(178, 178);
            muteButton.setPosition(window.getWidth() / 2 - muteButton.getWidth() / 2, 0);
            window.addActor(muteButton);

            ImageButton.ImageButtonStyle resumeStyle = new ImageButton.ImageButtonStyle();
            resumeStyle.imageUp = new TextureRegionDrawable(Assets.resumeButton);
            resumeStyle.imageUp.setMinWidth(128);
            resumeStyle.imageUp.setMinHeight(128);
            resumeStyle.imageDown = new TextureRegionDrawable(Assets.buttonBroken);
            resumeStyle.imageDown.setMinWidth(178);
            resumeStyle.imageDown.setMinHeight(178);
            resumeButton = new ImageButton(resumeStyle);
            resumeButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    window.remove();
                    Core.dialogOpen = false;
                }
            });
            resumeButton.setSize(178, 178);
            resumeButton.setPosition(window.getWidth() - resumeButton.getWidth(), 0);
            window.addActor(resumeButton);

            window.setPosition(Core.VIRTUAL_WIDTH / 2 - window.getWidth() / 2, Core.VIRTUAL_HEIGHT / 2);

            /**Add to screen stage*/
            game.screen.stage.addActor(window);
        }
    }

    private void remove() {
        if (window != null)
            window.remove();
    }
}