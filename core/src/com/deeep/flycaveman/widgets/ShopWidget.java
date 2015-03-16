package com.deeep.flycaveman.widgets;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Timer;
import com.deeep.flycaveman.Assets;
import com.deeep.flycaveman.Core;
import com.deeep.flycaveman.screens.GameScreen;

/**
 * Created by scanevaro on 15/03/2015.
 */
public class ShopWidget {
    private Window shopDialog, wingsDialog, steroidsDialog, staminaplusDialog, springsDialog;
    private GameScreen screen;
    private boolean wingsOpen, steroidsOpen, staminaOpen, springsOpen;

    public ShopWidget(final Core game) {
        this.screen = (GameScreen) game.screen;

        shopDialog = new Window("Shopuhg!", Assets.skin);
        shopDialog.setSize(680, Core.VIRTUAL_HEIGHT - 25);
        shopDialog.setPosition(0, Core.VIRTUAL_HEIGHT + shopDialog.getHeight());
        shopDialog.setKeepWithinStage(false);

        ImageButton.ImageButtonStyle retryStyle = new ImageButton.ImageButtonStyle();
        retryStyle.imageUp = new TextureRegionDrawable(Assets.restartButton);
        retryStyle.imageUp.setMinWidth(128);
        retryStyle.imageUp.setMinHeight(128);
        retryStyle.imageDown = new TextureRegionDrawable(Assets.buttonBroken);
        retryStyle.imageDown.setMinWidth(178);
        retryStyle.imageDown.setMinHeight(178);
        ImageButton retryButton = new ImageButton(retryStyle);
        retryButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setVisible(false);
                screen.darkness.addAction(Actions.fadeIn(0.4f));
                screen.stage.addActor(screen.darkness);
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        game.setScreen(new GameScreen(game));
                    }
                }, 0.4f);
            }
        });
        retryButton.setSize(178, 178);
        retryButton.setPosition(0, 0);
        shopDialog.addActor(retryButton);

        ImageButton.ImageButtonStyle wingsStyle = new ImageButton.ImageButtonStyle(Assets.skin.get(Button.ButtonStyle.class));
        wingsStyle.imageUp = new TextureRegionDrawable(Assets.wings);
        wingsStyle.imageUp.setMinWidth(140);
        wingsStyle.imageUp.setMinHeight(140);
        wingsStyle.imageDown = new TextureRegionDrawable(Assets.wings);
        wingsStyle.imageDown.setMinWidth(140);
        wingsStyle.imageDown.setMinHeight(140);
        ImageButton wingsButton = new ImageButton(wingsStyle);
        wingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                show("wingsDialog");
            }
        });
        wingsButton.setSize(140, 140);
        wingsButton.setPosition(50, shopDialog.getHeight() / 2 + 38);
        shopDialog.addActor(wingsButton);

        ImageButton.ImageButtonStyle steroidsStyle = new ImageButton.ImageButtonStyle(Assets.skin.get(Button.ButtonStyle.class));
        steroidsStyle.imageUp = new TextureRegionDrawable(Assets.steroids);
        steroidsStyle.imageUp.setMinWidth(140);
        steroidsStyle.imageUp.setMinHeight(140);
        steroidsStyle.imageDown = new TextureRegionDrawable(Assets.steroids);
        steroidsStyle.imageDown.setMinWidth(140);
        steroidsStyle.imageDown.setMinHeight(140);
        ImageButton steroidsButton = new ImageButton(steroidsStyle);
        steroidsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                show("steroidsDialog");
            }
        });
        steroidsButton.setSize(140, 140);
        steroidsButton.setPosition(50 + 15 + 20 + wingsButton.getWidth(), shopDialog.getHeight() / 2 + 38);
        shopDialog.addActor(steroidsButton);

        ImageButton.ImageButtonStyle staminaplusStyle = new ImageButton.ImageButtonStyle(Assets.skin.get(Button.ButtonStyle.class));
        staminaplusStyle.imageUp = new TextureRegionDrawable(Assets.staminaplus);
        staminaplusStyle.imageUp.setMinWidth(140);
        staminaplusStyle.imageUp.setMinHeight(140);
        staminaplusStyle.imageDown = new TextureRegionDrawable(Assets.staminaplus);
        staminaplusStyle.imageDown.setMinWidth(140);
        staminaplusStyle.imageDown.setMinHeight(140);
        ImageButton staminaplusButton = new ImageButton(staminaplusStyle);
        staminaplusButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                show("staminaplusDialog");
            }
        });
        staminaplusButton.setSize(140, 140);
        staminaplusButton.setPosition(50 + 15 + 45 + wingsButton.getWidth() + steroidsButton.getWidth(), shopDialog.getHeight() / 2 + 38);
        shopDialog.addActor(staminaplusButton);

        ImageButton.ImageButtonStyle springsStyle = new ImageButton.ImageButtonStyle(Assets.skin.get(Button.ButtonStyle.class));
        springsStyle.imageUp = new TextureRegionDrawable(Assets.springs);
        springsStyle.imageUp.setMinWidth(140);
        springsStyle.imageUp.setMinHeight(140);
        springsStyle.imageDown = new TextureRegionDrawable(Assets.springs);
        springsStyle.imageDown.setMinWidth(140);
        springsStyle.imageDown.setMinHeight(140);
        ImageButton springsButton = new ImageButton(springsStyle);
        springsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                show("springsDialog");
            }
        });
        springsButton.setSize(140, 140);
        springsButton.setPosition(300, shopDialog.getHeight() / 2 - springsButton.getHeight());
        shopDialog.addActor(springsButton);

        game.screen.stage.addActor(shopDialog);

        buildBuyDialogs();
    }

    private void buildBuyDialogs() {
        buildWingsDialog();
        buildSteroidsDialog();
        buildStaminaDialog();
        buildSpringsDialog();
    }

    private void buildWingsDialog() {
        wingsDialog = new Window("Wings", Assets.skin);
        wingsDialog.setSize(350, Core.VIRTUAL_HEIGHT - 24);
        wingsDialog.setPosition(Core.VIRTUAL_WIDTH + wingsDialog.getWidth(), 12);
        wingsDialog.setKeepWithinStage(false);

        screen.stage.addActor(wingsDialog);
    }

    private void buildSteroidsDialog() {
        steroidsDialog = new Window("Steroids", Assets.skin);
        steroidsDialog.setSize(350, Core.VIRTUAL_HEIGHT - 24);
        steroidsDialog.setPosition(Core.VIRTUAL_WIDTH + steroidsDialog.getWidth(), 12);
        steroidsDialog.setKeepWithinStage(false);

        screen.stage.addActor(steroidsDialog);
    }

    private void buildStaminaDialog() {
        staminaplusDialog = new Window("Stamina Plus", Assets.skin);
        staminaplusDialog.setSize(350, Core.VIRTUAL_HEIGHT - 24);
        staminaplusDialog.setPosition(Core.VIRTUAL_WIDTH + staminaplusDialog.getWidth(), 12);
        staminaplusDialog.setKeepWithinStage(false);

        screen.stage.addActor(staminaplusDialog);
    }

    private void buildSpringsDialog() {
        springsDialog = new Window("Springs", Assets.skin);
        springsDialog.setSize(350, Core.VIRTUAL_HEIGHT - 24);
        springsDialog.setPosition(Core.VIRTUAL_WIDTH + springsDialog.getWidth(), 12);
        springsDialog.setKeepWithinStage(false);

        screen.stage.addActor(springsDialog);
    }

    private void show(String dialog) {
        if (wingsOpen)
            screen.stage.addActor(wingsDialog);
        else if (steroidsOpen)
            screen.stage.addActor(steroidsDialog);
        else if (staminaOpen)
            screen.stage.addActor(staminaplusDialog);
        else if (springsOpen)
            screen.stage.addActor(springsDialog);

        if (dialog.equals("wingsDialog")) {
            wingsOpen = true;
            steroidsOpen = false;
            staminaOpen = false;
            springsOpen = false;
            screen.stage.addActor(wingsDialog);

            wingsDialog.addAction(Actions.moveTo(Core.VIRTUAL_WIDTH - wingsDialog.getWidth(), 12, 0.4f, Interpolation.linear));
            steroidsDialog.addAction(Actions.moveTo(Core.VIRTUAL_WIDTH + steroidsDialog.getWidth(), 12, 0.4f, Interpolation.linear));
            staminaplusDialog.addAction(Actions.moveTo(Core.VIRTUAL_WIDTH + staminaplusDialog.getWidth(), 12, 0.4f, Interpolation.linear));
            springsDialog.addAction(Actions.moveTo(Core.VIRTUAL_WIDTH + springsDialog.getWidth(), 12, 0.4f, Interpolation.linear));

            screen.stage.addActor(wingsDialog);
        } else if (dialog.equals("steroidsDialog")) {
            steroidsOpen = true;
            wingsOpen = false;
            staminaOpen = false;
            springsOpen = false;
            screen.stage.addActor(steroidsDialog);

            wingsDialog.addAction(Actions.moveTo(Core.VIRTUAL_WIDTH + wingsDialog.getWidth(), 12, 0.4f, Interpolation.linear));
            steroidsDialog.addAction(Actions.moveTo(Core.VIRTUAL_WIDTH - steroidsDialog.getWidth(), 12, 0.4f, Interpolation.linear));
            staminaplusDialog.addAction(Actions.moveTo(Core.VIRTUAL_WIDTH + staminaplusDialog.getWidth(), 12, 0.4f, Interpolation.linear));
            springsDialog.addAction(Actions.moveTo(Core.VIRTUAL_WIDTH + springsDialog.getWidth(), 12, 0.4f, Interpolation.linear));
        } else if (dialog.equals("staminaplusDialog")) {
            staminaOpen = true;
            steroidsOpen = false;
            wingsOpen = false;
            springsOpen = false;
            screen.stage.addActor(staminaplusDialog);

            wingsDialog.addAction(Actions.moveTo(Core.VIRTUAL_WIDTH + wingsDialog.getWidth(), 12, 0.4f, Interpolation.linear));
            steroidsDialog.addAction(Actions.moveTo(Core.VIRTUAL_WIDTH + steroidsDialog.getWidth(), 12, 0.4f, Interpolation.linear));
            staminaplusDialog.addAction(Actions.moveTo(Core.VIRTUAL_WIDTH - staminaplusDialog.getWidth(), 12, 0.4f, Interpolation.linear));
            springsDialog.addAction(Actions.moveTo(Core.VIRTUAL_WIDTH + springsDialog.getWidth(), 12, 0.4f, Interpolation.linear));
        } else if (dialog.equals("springsDialog")) {
            springsOpen = true;
            steroidsOpen = false;
            wingsOpen = false;
            staminaOpen = false;
            screen.stage.addActor(springsDialog);

            wingsDialog.addAction(Actions.moveTo(Core.VIRTUAL_WIDTH + wingsDialog.getWidth(), 12, 0.4f, Interpolation.linear));
            steroidsDialog.addAction(Actions.moveTo(Core.VIRTUAL_WIDTH + steroidsDialog.getWidth(), 12, 0.4f, Interpolation.linear));
            staminaplusDialog.addAction(Actions.moveTo(Core.VIRTUAL_WIDTH + staminaplusDialog.getWidth(), 12, 0.4f, Interpolation.linear));
            springsDialog.addAction(Actions.moveTo(Core.VIRTUAL_WIDTH - springsDialog.getWidth(), 12, 0.4f, Interpolation.linear));
        }
    }

    public void setVisible(boolean flag) {
        if (flag) {
            shopDialog.addAction(Actions.moveTo(0, Core.VIRTUAL_HEIGHT / 2 - shopDialog.getHeight() / 2, 0.4f, Interpolation.linear));
        } else {
            shopDialog.addAction(Actions.moveTo(0, Core.VIRTUAL_HEIGHT + shopDialog.getHeight(), 0.4f, Interpolation.linear));

            wingsDialog.setVisible(flag);
            steroidsDialog.setVisible(flag);
            staminaplusDialog.setVisible(flag);
            springsDialog.setVisible(flag);
        }
    }
}