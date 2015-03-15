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
import com.deeep.flycaveman.screens.AbstractScreen;
import com.deeep.flycaveman.screens.GameScreen;

/**
 * Created by scanevaro on 15/03/2015.
 */
public class ShopWidget {
    private Window shopDialog, wingsDialog, steroidsDialog, staminaplusDialog, springsDialog;
    private GameScreen screen;

    public ShopWidget(final Core game) {
        this.screen = (GameScreen) game.screen;

        shopDialog = new Window("Shopuhg!", Assets.skin);
        shopDialog.setSize(Core.VIRTUAL_WIDTH - 25, Core.VIRTUAL_HEIGHT - 25);
        shopDialog.setPosition(Core.VIRTUAL_WIDTH / 2 - shopDialog.getWidth() / 2, Core.VIRTUAL_HEIGHT + shopDialog.getHeight());
        shopDialog.setVisible(false);
        shopDialog.setKeepWithinStage(false);

        ImageButton.ImageButtonStyle retryStyle = new ImageButton.ImageButtonStyle();
        retryStyle.imageUp = new TextureRegionDrawable(Assets.restartButton);
        retryStyle.imageUp.setMinWidth(96);
        retryStyle.imageUp.setMinHeight(96);
        retryStyle.imageDown = new TextureRegionDrawable(Assets.restartButton);
        retryStyle.imageDown.setMinWidth(96);
        retryStyle.imageDown.setMinHeight(96);
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
        retryButton.setSize(96, 96);
        retryButton.setPosition(shopDialog.getWidth() - retryButton.getWidth(), 0);
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
        staminaplusButton.setPosition(shopDialog.getWidth() / 2 - staminaplusButton.getWidth() / 2, shopDialog.getHeight() / 2 + 38);
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

        buildBuyDialogs(game.screen);
    }

    private void buildBuyDialogs(AbstractScreen screen) {
        wingsDialog = new Window("Wings", Assets.skin);
        steroidsDialog = new Window("Steroids", Assets.skin);
        staminaplusDialog = new Window("Stamina Plus", Assets.skin);
        springsDialog = new Window("SpringsDialog", Assets.skin);
    }

    private void show(String dialog) {
        if (dialog.equals("wingsDialog")) {

        } else if (dialog.equals("steroidsDialog")) {

        } else if (dialog.equals("staminaplusDialog")) {

        } else if (dialog.equals("springsDialog")) {

        }
    }

    public void setVisible(boolean flag) {
        if (flag) {
            shopDialog.setVisible(flag);
            shopDialog.addAction(Actions.moveTo(Core.VIRTUAL_WIDTH / 2 - shopDialog.getWidth() / 2,
                    Core.VIRTUAL_HEIGHT / 2 - shopDialog.getHeight() / 2, 0.4f, Interpolation.linear));
        } else
            shopDialog.addAction(Actions.moveTo(Core.VIRTUAL_WIDTH / 2 - shopDialog.getWidth() / 2,
                    Core.VIRTUAL_HEIGHT + shopDialog.getHeight(), 0.4f, Interpolation.linear));
    }
}