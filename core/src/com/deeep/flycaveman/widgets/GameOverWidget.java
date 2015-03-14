package com.deeep.flycaveman.widgets;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.deeep.flycaveman.Assets;
import com.deeep.flycaveman.Core;
import com.deeep.flycaveman.screens.GameScreen;

/**
 * Created by scanevaro on 12/03/2015.
 */
public class GameOverWidget {
    private Window topRightWindow, bottomRightWindow;
    private Image leftWindow;
    private Label statsLabel, distanceLabel, maxHeightLabel, flappingLabel, entitiesLabel, powerupsLabel, coinsLabel;

    public GameOverWidget(final Core game, final Window shopDialog) {
        setTopRightWindow(game);

        setBottomRightWindow(game, shopDialog);

        setLeftWindow(game);
    }

    private void setTopRightWindow(final Core game) {
        topRightWindow = new Window("Game Over", Assets.skin);
        topRightWindow.setSize(513, 258);
        topRightWindow.setPosition(Core.VIRTUAL_WIDTH + 200, Core.VIRTUAL_HEIGHT - topRightWindow.getHeight());
        topRightWindow.setKeepWithinStage(false);
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
    }

    private void setBottomRightWindow(final Core game, final Window shopDialog) {
        bottomRightWindow = new Window("", Assets.skin.get("default2", Window.WindowStyle.class));
        bottomRightWindow.setSize(513, 258);
        bottomRightWindow.setPosition(Core.VIRTUAL_WIDTH + bottomRightWindow.getWidth(), 0);
        bottomRightWindow.setVisible(false);
        bottomRightWindow.setKeepWithinStage(false);

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

    private void setLeftWindow(final Core game) {
        leftWindow = new Image(Assets.dialog);
        leftWindow.setSize(380, 400);
        leftWindow.setOrigin(leftWindow.getWidth() / 2, leftWindow.getHeight() / 2);
        leftWindow.setPosition(-100, 140);
        leftWindow.setVisible(false);
        game.screen.stage.addActor(leftWindow);

        statsLabel = new Label("Stats", Assets.skin.get("defaultBackground", Label.LabelStyle.class));
        statsLabel.setAlignment(Align.center);
        statsLabel.setSize(280, 80);
        statsLabel.setPosition(-100, 460);
        statsLabel.setVisible(false);
        game.screen.stage.addActor(statsLabel);

        distanceLabel = new Label("Distance: ", Assets.skin);
        distanceLabel.setPosition(-100, 420);
        distanceLabel.setVisible(false);
        game.screen.stage.addActor(distanceLabel);
        maxHeightLabel = new Label("Max Height: ", Assets.skin);
        maxHeightLabel.setPosition(-100, 380);
        maxHeightLabel.setVisible(false);
        game.screen.stage.addActor(maxHeightLabel);
        flappingLabel = new Label("Flapping Distance: ", Assets.skin);
        flappingLabel.setPosition(-100, 340);
        flappingLabel.setVisible(false);
        game.screen.stage.addActor(flappingLabel);
        entitiesLabel = new Label("Entities Smacked: ", Assets.skin);
        entitiesLabel.setPosition(-100, 300);
        entitiesLabel.setVisible(false);
        game.screen.stage.addActor(entitiesLabel);
        powerupsLabel = new Label("Powerups Picked up: ", Assets.skin);
        powerupsLabel.setPosition(-100, 260);
        powerupsLabel.setVisible(false);
        game.screen.stage.addActor(powerupsLabel);
        coinsLabel = new Label("Coins Picked up: ", Assets.skin);
        coinsLabel.setPosition(-100, 220);
        coinsLabel.setVisible(false);
        game.screen.stage.addActor(coinsLabel);
    }

    public void setVisible() {
        bottomRightWindow.setVisible(true);
        topRightWindow.setVisible(true);
        leftWindow.setVisible(true);
        statsLabel.setVisible(true);
        distanceLabel.setVisible(true);
        maxHeightLabel.setVisible(true);
        flappingLabel.setVisible(true);
        entitiesLabel.setVisible(true);
        powerupsLabel.setVisible(true);
        coinsLabel.setVisible(true);

        leftWindow.addAction(Actions.moveTo(25, 140, 0.4f, Interpolation.pow2));
        statsLabel.addAction(Actions.moveTo(62, 460, 0.4f, Interpolation.pow2));
        distanceLabel.addAction(Actions.moveTo(50, 420, 0.4f, Interpolation.pow2));
        maxHeightLabel.addAction(Actions.moveTo(50, 380, 0.4f, Interpolation.pow2));
        flappingLabel.addAction(Actions.moveTo(50, 340, 0.4f, Interpolation.pow2));
        entitiesLabel.addAction(Actions.moveTo(50, 300, 0.4f, Interpolation.pow2));
        powerupsLabel.addAction(Actions.moveTo(50, 260, 0.4f, Interpolation.pow2));
        coinsLabel.addAction(Actions.moveTo(50, 220, 0.4f, Interpolation.pow2));
        bottomRightWindow.addAction(Actions.moveTo(447, 0, 0.4f, Interpolation.pow2));
        topRightWindow.addAction(Actions.moveTo(447, Core.VIRTUAL_HEIGHT - topRightWindow.getHeight(), 0.4f, Interpolation.pow2));
    }
}