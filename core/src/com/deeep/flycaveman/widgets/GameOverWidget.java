package com.deeep.flycaveman.widgets;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.deeep.flycaveman.Assets;
import com.deeep.flycaveman.Core;
import com.deeep.flycaveman.screens.GameScreen;

/**
 * Created by scanevaro on 12/03/2015.
 */
public class GameOverWidget {
    private Window topRightWindow, bottomRightWindow, leftWindow;

    public GameOverWidget(final Core game, final Window shopDialog) {
        topRightWindow = new Window("UGHA UGH (Game Over)", Assets.skin);
        topRightWindow.setSize(513, 258);
        topRightWindow.setPosition(Core.VIRTUAL_WIDTH - topRightWindow.getWidth(), Core.VIRTUAL_HEIGHT - topRightWindow.getHeight());
        //( ͡° ͜ʖ ͡°) < l'elmar face

//        GameScreen.distance = new Label("", Assets.skin);
//        GameScreen.distance.setPosition(25, gameOverDialog.getHeight() / 2);
        Label maxDistance = new Label("Max Distance: xxxxx", Assets.skin);
        maxDistance.setPosition(100, 120);
        topRightWindow.addActor(maxDistance);

        Label colectedCoins = new Label("Colected Coins : xxxxx", Assets.skin);
        colectedCoins.setPosition(100, 75);
        topRightWindow.addActor(colectedCoins);

        topRightWindow.setVisible(false);

        game.screen.stage.addActor(topRightWindow);

        bottomRightWindow = new Window("", Assets.skin.get("default2", Window.WindowStyle.class));
        bottomRightWindow.setSize(513, 258);
        bottomRightWindow.setPosition(Core.VIRTUAL_WIDTH - bottomRightWindow.getWidth(), 0);
        bottomRightWindow.setVisible(false);

        ImageButton.ImageButtonStyle retryStyle = new ImageButton.ImageButtonStyle();
        retryStyle.imageUp = new TextureRegionDrawable(Assets.restartButton);
        retryStyle.imageUp.setMinWidth(128);
        retryStyle.imageUp.setMinHeight(128);
        retryStyle.imageDown = new TextureRegionDrawable(Assets.buttonBroken);
        retryStyle.imageDown.setMinWidth(178);
        retryStyle.imageDown.setMinHeight(178);
        final ImageButton retryButton = new ImageButton(retryStyle);
        retryButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                super.touchDown(event, x, y, pointer, button);
                GameScreen s = (GameScreen) game.screen;
                //s.world.area.soundManager.playSound(Assets.buttonConfirm);
                return true;
            }
        });
        retryButton.setSize(178, 178);
        retryButton.setPosition(bottomRightWindow.getWidth() / 2 + 15, bottomRightWindow.getHeight() / 2 - retryButton.getHeight() / 2);
        bottomRightWindow.addActor(retryButton);

        ImageButton.ImageButtonStyle homeStyle = new ImageButton.ImageButtonStyle();
        homeStyle.imageUp = new TextureRegionDrawable(Assets.homeButton);
        homeStyle.imageUp.setMinWidth(128);
        homeStyle.imageUp.setMinHeight(128);
        homeStyle.imageDown = new TextureRegionDrawable(Assets.buttonBroken);
        homeStyle.imageDown.setMinWidth(178);
        homeStyle.imageDown.setMinHeight(178);
        ImageButton homeButton = new ImageButton(homeStyle);
        homeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
            }

            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                super.touchDown(event, x, y, pointer, button);
                GameScreen s = (GameScreen) game.screen;
                //s.world.area.soundManager.playSound(Assets.buttonConfirm);
                return true;
            }
        });
        homeButton.setSize(178, 178);
        homeButton.setPosition(50, bottomRightWindow.getHeight() / 2 - homeButton.getHeight() / 2);
        bottomRightWindow.addActor(homeButton);

//        ImageButton.ImageButtonStyle shopStyle = new ImageButton.ImageButtonStyle();
//        shopStyle.imageUp = new TextureRegionDrawable(Assets.shopButton);
//        shopStyle.imageUp.setMinWidth(96);
//        shopStyle.imageUp.setMinHeight(96);
//        shopStyle.imageDown = new TextureRegionDrawable(Assets.shopButton);
//        shopStyle.imageDown.setMinWidth(96);
//        shopStyle.imageDown.setMinHeight(96);
//        ImageButton shopButton = new ImageButton(shopStyle);
//        shopButton.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                shopDialog.setVisible(true);
//            }
//        });
//        shopButton.setSize(96, 96);
//        shopButton.setPosition(gameOverDialog.getWidth() / 2 - shopButton.getWidth() / 2, 0);
//        gameOverDialog.addActor(shopButton);

        game.screen.stage.addActor(bottomRightWindow);
    }

    public void setVisible() {
        bottomRightWindow.setVisible(true);
        topRightWindow.setVisible(true);
//        leftWindow.setVisible(true);
    }
}