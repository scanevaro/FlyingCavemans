package com.deeep.flycaveman.widgets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Timer;
import com.deeep.flycaveman.Assets;
import com.deeep.flycaveman.Core;
import com.deeep.flycaveman.entities.CaveMan;
import com.deeep.flycaveman.screens.GameScreen;

/**
 * Created by scanevaro on 12/03/2015.
 */
public class GameOverWidget {
    private Core game;
    private Window topRightWindow, bottomRightWindow;
    private ShopWidget shopWidget;
    private Image leftWindow;
    private Label statsLabel, distanceLabel, maxHeightLabel, flappingLabel, entitiesLabel, powerupsLabel, coinsLabel,
            maxDistance, colectedCoins;
    private GameScreen screen;
    private boolean sendScore;
    private String name, distance;
    public static String maxDistanceF;

    public GameOverWidget(final Core game, ShopWidget shopWidget) {
        this.game = game;
        this.screen = (GameScreen) game.screen;
        this.shopWidget = shopWidget;

        setTopRightWindow();

        setBottomRightWindow();

        setLeftWindow();

        sendScore = true;
    }

    private void setTopRightWindow() {
        topRightWindow = new Window("", Assets.skin);
        topRightWindow.setSize(513, 258);
        topRightWindow.setPosition(Core.VIRTUAL_WIDTH + topRightWindow.getWidth(),
                Core.VIRTUAL_HEIGHT - topRightWindow.getHeight());
        topRightWindow.setKeepWithinStage(false);
        topRightWindow.padTop(0);

        TextButton gameOverTitle = new TextButton("Game Over", Assets.skin);
        gameOverTitle.setSize(320, 100);
        gameOverTitle.setPosition(topRightWindow.getWidth() / 2 - gameOverTitle.getWidth() / 2, 160);
        topRightWindow.addActor(gameOverTitle);

        //( ͡° ͜ʖ ͡°) < l'elmar face
        maxDistance = new Label("Max Distance: ", Assets.skin);
        maxDistance.setPosition(70, 110);
        topRightWindow.addActor(maxDistance);

        colectedCoins = new Label("Colected Coins : xxxxx", Assets.skin);
        colectedCoins.setPosition(70, 65);
        topRightWindow.addActor(colectedCoins);

        topRightWindow.setVisible(false);

        game.screen.stage.addActor(topRightWindow);
    }

    private void setBottomRightWindow() {
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
                GameScreen.retry = true;
                screen.darkness.addAction(Actions.fadeIn(0.4f));
                screen.stage.addActor(screen.darkness);
                Timer.schedule(new Timer.Task() {
                    @Override
                    public void run() {
                        game.setScreen(new GameScreen(game));
                    }
                }, 0.4f);
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
        retryButton.setPosition(bottomRightWindow.getWidth() / 2 + 15,
                bottomRightWindow.getHeight() / 2 - retryButton.getHeight() / 2);
        bottomRightWindow.addActor(retryButton);

        ImageButton.ImageButtonStyle homeStyle = new ImageButton.ImageButtonStyle();
        homeStyle.imageUp = new TextureRegionDrawable(Assets.shopButton);
        homeStyle.imageUp.setMinWidth(128);
        homeStyle.imageUp.setMinHeight(128);
        homeStyle.imageDown = new TextureRegionDrawable(Assets.buttonBroken);
        homeStyle.imageDown.setMinWidth(178);
        homeStyle.imageDown.setMinHeight(178);
        ImageButton homeButton = new ImageButton(homeStyle);
        homeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameScreen.retry = true;
                shopWidget.setVisible(true);
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

        game.screen.stage.addActor(bottomRightWindow);
    }

    private void setLeftWindow() {
        leftWindow = new Image(Assets.dialog);
        leftWindow.setSize(470, 400);
        leftWindow.setOrigin(leftWindow.getWidth() / 2, leftWindow.getHeight() / 2);
        leftWindow.setPosition(-leftWindow.getWidth(), 140);
        leftWindow.setVisible(false);
        game.screen.stage.addActor(leftWindow);

        statsLabel = new Label("Stats", Assets.skin.get("defaultBackground", Label.LabelStyle.class));
        statsLabel.setAlignment(Align.center);
        statsLabel.setSize(280, 80);
        statsLabel.setPosition(-leftWindow.getWidth(), 460);
        statsLabel.setVisible(false);
        game.screen.stage.addActor(statsLabel);

        distanceLabel = new Label("Distance: ", Assets.skin);
        distanceLabel.setPosition(-leftWindow.getWidth(), 420);
        distanceLabel.setVisible(false);
        game.screen.stage.addActor(distanceLabel);
        maxHeightLabel = new Label("Max Height: ", Assets.skin);
        maxHeightLabel.setPosition(-leftWindow.getWidth(), 380);
        maxHeightLabel.setVisible(false);
        game.screen.stage.addActor(maxHeightLabel);
        flappingLabel = new Label("Flapping Distance: ", Assets.skin);
        flappingLabel.setPosition(-leftWindow.getWidth(), 340);
        flappingLabel.setVisible(false);
        game.screen.stage.addActor(flappingLabel);
        entitiesLabel = new Label("Entities Smacked: ", Assets.skin);
        entitiesLabel.setPosition(-leftWindow.getWidth(), 300);
        entitiesLabel.setVisible(false);
        game.screen.stage.addActor(entitiesLabel);
        powerupsLabel = new Label("Powerups Picked up: ", Assets.skin);
        powerupsLabel.setPosition(-leftWindow.getWidth(), 260);
        powerupsLabel.setVisible(false);
        game.screen.stage.addActor(powerupsLabel);
        coinsLabel = new Label("Coins Picked up: ", Assets.skin);
        coinsLabel.setPosition(-leftWindow.getWidth(), 220);
        coinsLabel.setVisible(false);
        game.screen.stage.addActor(coinsLabel);
    }

    public void setVisible(boolean flag) {
//        if (sendScore && !game.gjapi.isActive()) {
//            Gdx.input.getTextInput(new TextInput(game.gjapi), "Enter your Username", "username", "hint");
//            sendScore = false;
//        }
        if (sendScore && name != null) {
            Net.HttpRequest request = new Net.HttpRequest("GET");
            request.setUrl("http://dreamlo.com/lb/j06iUPGoVUSeIxcHpQ5wkwBXZsEVad2kuUMdRVXaqKiA/add/" + name + "/" + distance);
            Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
                @Override
                public void handleHttpResponse(Net.HttpResponse httpResponse) {
                }

                @Override
                public void failed(Throwable t) {
                }

                @Override
                public void cancelled() {
                }
            });

            sendScore = false;
        }

        if (flag) {
            bottomRightWindow.setVisible(flag);
            topRightWindow.setVisible(flag);
            leftWindow.setVisible(flag);
            statsLabel.setVisible(flag);
            distanceLabel.setVisible(flag);
            maxHeightLabel.setVisible(flag);
            flappingLabel.setVisible(flag);
            entitiesLabel.setVisible(flag);
            powerupsLabel.setVisible(flag);
            coinsLabel.setVisible(flag);

            leftWindow.addAction(Actions.moveTo(0, 140, 0.45f, Interpolation.linear));
            statsLabel.addAction(Actions.moveTo(62, 460, 0.45f, Interpolation.linear));
            distanceLabel.addAction(Actions.moveTo(30, 420, 0.45f, Interpolation.linear));
            maxHeightLabel.addAction(Actions.moveTo(30, 380, 0.45f, Interpolation.linear));
            flappingLabel.addAction(Actions.moveTo(30, 340, 0.45f, Interpolation.linear));
            entitiesLabel.addAction(Actions.moveTo(30, 300, 0.45f, Interpolation.linear));
            powerupsLabel.addAction(Actions.moveTo(30, 260, 0.45f, Interpolation.linear));
            coinsLabel.addAction(Actions.moveTo(30, 220, 0.45f, Interpolation.linear));
            bottomRightWindow.addAction(Actions.moveTo(447, 0, 0.4f, Interpolation.linear));
            topRightWindow.addAction(Actions.moveTo(447, Core.VIRTUAL_HEIGHT - topRightWindow.getHeight(), 0.4f,
                    Interpolation.linear));
        } else {
            leftWindow.addAction(Actions.moveTo(-leftWindow.getWidth(), 140, 0.1f, Interpolation.linear));
            statsLabel.addAction(Actions.moveTo(-leftWindow.getWidth(), 460, 0.1f, Interpolation.linear));
            distanceLabel.addAction(Actions.moveTo(-leftWindow.getWidth(), 420, 0.1f, Interpolation.linear));
            maxHeightLabel.addAction(Actions.moveTo(-leftWindow.getWidth(), 380, 0.1f, Interpolation.linear));
            flappingLabel.addAction(Actions.moveTo(-leftWindow.getWidth(), 340, 0.1f, Interpolation.linear));
            entitiesLabel.addAction(Actions.moveTo(-leftWindow.getWidth(), 300, 0.1f, Interpolation.linear));
            powerupsLabel.addAction(Actions.moveTo(-leftWindow.getWidth(), 260, 0.1f, Interpolation.linear));
            coinsLabel.addAction(Actions.moveTo(-leftWindow.getWidth(), 220, 0.1f, Interpolation.linear));
            bottomRightWindow.addAction(Actions.moveTo(Core.VIRTUAL_WIDTH + bottomRightWindow.getWidth(), 0, 0.4f,
                    Interpolation.linear));
            topRightWindow.addAction(Actions.moveTo(Core.VIRTUAL_WIDTH + topRightWindow.getWidth(),
                    Core.VIRTUAL_HEIGHT - topRightWindow.getHeight(), 0.4f, Interpolation.linear));
        }
    }

    public void update(String name, String distance, float maxHeight, float flapDistance, int smacked, int powerUpsPicked,
                       int coinsPicked) {
        this.name = name;
        this.distance = distance;

        distanceLabel.setText("Distance: " + distance);
        maxHeightLabel.setText("Max Height: " + String.valueOf(maxHeight).substring(0, 2));
        flappingLabel.setText("Distance W/Flapping: " + String.valueOf(flapDistance).substring(0, 2));
        entitiesLabel.setText("Entities Smacked: " + String.valueOf(smacked));
        powerupsLabel.setText("PowerUps Picked Up: " + String.valueOf(powerUpsPicked));
        coinsLabel.setText("Coins Picked Up: " + String.valueOf(coinsPicked));
        colectedCoins.setText("Colected Coins : " + String.valueOf(CaveMan.coins));
        if (maxDistanceF == null)
            maxDistance.setText("Max Distance: " + distance);
        else maxDistance.setText("Max Distance: " + maxDistanceF);
        //TODO new record animation
    }
}