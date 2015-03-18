package com.deeep.flycaveman.widgets;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Timer;
import com.deeep.flycaveman.Assets;
import com.deeep.flycaveman.Core;
import com.deeep.flycaveman.entities.CaveMan;
import com.deeep.flycaveman.screens.GameScreen;

/**
 * Created by scanevaro on 15/03/2015.
 */
public class ShopWidget {
    private Window shopDialog, wingsDialog, steroidsDialog, staminaplusDialog, springsDialog;
    private GameScreen screen;
    private CoinsWidget coinsWidget;
    private ImageButton buyWingsButton, buySteroidsButton, buyStaminaButton, buySpringsButton;
    private boolean wingsOpen, steroidsOpen, staminaOpen, springsOpen;

    public ShopWidget(final Core game) {
        this.screen = (GameScreen) game.screen;

        shopDialog = new Window("Shopuhg!", Assets.skin);
        shopDialog.setSize(680, Core.VIRTUAL_HEIGHT - 25);
        shopDialog.setPosition(0, Core.VIRTUAL_HEIGHT + shopDialog.getHeight());
        shopDialog.setKeepWithinStage(false);
        shopDialog.removeCaptureListener(shopDialog.getCaptureListeners().first());

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

        buildCoinWidget();
        buildBuyDialogs();
    }

    private void buildCoinWidget() {
        coinsWidget = new CoinsWidget();
        coinsWidget.setPosition(240, -coinsWidget.background.getHeight());

        screen.stage.addActor(coinsWidget);
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

        TextArea textArea = new TextArea("Wings help cavemen flap like birds and flying lizards." +
                " Just no time until cavemen grow a pair like them....", Assets.skin);
        textArea.setSize(300, 300);
        textArea.setPosition(30, 134);
        textArea.clearListeners();
        wingsDialog.addActor(textArea);

        Image star[] = new Image[3];
        star[0] = new Image(Assets.starBlack);
        star[1] = new Image(Assets.starBlack);
        star[2] = new Image(Assets.starBlack);
        int posX = 70;
        for (Image staR : star) {
            staR.setSize(64, 64);
            staR.setPosition(posX, 88);
            posX += 70;
            wingsDialog.addActor(staR);
        }

        ImageButton.ImageButtonStyle buyStyle = new ImageButton.ImageButtonStyle(/*Assets.skin.get(Button.ButtonStyle.class)*/);
        buyStyle.imageUp = new TextureRegionDrawable(Assets.buyButton);
        buyStyle.imageUp.setMinWidth(160);
        buyStyle.imageUp.setMinHeight(96);
        buyStyle.imageDown = new TextureRegionDrawable(Assets.buyButtonDown);
        buyStyle.imageDown.setMinWidth(160);
        buyStyle.imageDown.setMinHeight(96);
        buyStyle.imageDisabled = new TextureRegionDrawable(Assets.buyButtonDisabled);
        buyStyle.imageDisabled.setMinWidth(160);
        buyStyle.imageDisabled.setMinHeight(96);
        buyWingsButton = new ImageButton(buyStyle);
        buyWingsButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (buyWingsButton.isDisabled()) return true;

                return false;
            }

            @Override
            public void clicked(InputEvent event, float x, float y) {
                screen.world.caveman.addWings();
            }
        });
        buyWingsButton.setSize(160, 96);
        buyWingsButton.setPosition(0, 0);
        wingsDialog.addActor(buyWingsButton);

        CoinSprite coinSprite = new CoinSprite();
        coinSprite.setSize(64, 64);
        coinSprite.setPosition(wingsDialog.getWidth() / 2, 15);
        wingsDialog.addActor(coinSprite);

        Label cost = new Label("= 50", Assets.skin);
        cost.setPosition(wingsDialog.getWidth() / 2 + 64 + 4, 24);
        wingsDialog.addActor(cost);
    }

    private void buildSteroidsDialog() {
        steroidsDialog = new Window("Steroids", Assets.skin);
        steroidsDialog.setSize(350, Core.VIRTUAL_HEIGHT - 24);
        steroidsDialog.setPosition(Core.VIRTUAL_WIDTH + steroidsDialog.getWidth(), 12);
        steroidsDialog.setKeepWithinStage(false);

        screen.stage.addActor(steroidsDialog);

        ImageButton.ImageButtonStyle buyStyle = new ImageButton.ImageButtonStyle(/*Assets.skin.get(Button.ButtonStyle.class)*/);
        buyStyle.imageUp = new TextureRegionDrawable(Assets.buyButton);
        buyStyle.imageUp.setMinWidth(160);
        buyStyle.imageUp.setMinHeight(96);
        buyStyle.imageDown = new TextureRegionDrawable(Assets.buyButtonDown);
        buyStyle.imageDown.setMinWidth(160);
        buyStyle.imageDown.setMinHeight(96);
        buyStyle.imageDisabled = new TextureRegionDrawable(Assets.buyButtonDisabled);
        buyStyle.imageDisabled.setMinWidth(160);
        buyStyle.imageDisabled.setMinHeight(96);
        buySteroidsButton = new ImageButton(buyStyle);
        buySteroidsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                screen.world.caveman.addSteroids();
            }
        });
        buySteroidsButton.setSize(160, 96);
        buySteroidsButton.setPosition(wingsDialog.getWidth() / 2 - buySteroidsButton.getWidth() / 2, 25);
        steroidsDialog.addActor(buySteroidsButton);
    }

    private void buildStaminaDialog() {
        staminaplusDialog = new Window("Stamina Plus", Assets.skin);
        staminaplusDialog.setSize(350, Core.VIRTUAL_HEIGHT - 24);
        staminaplusDialog.setPosition(Core.VIRTUAL_WIDTH + staminaplusDialog.getWidth(), 12);
        staminaplusDialog.setKeepWithinStage(false);

        screen.stage.addActor(staminaplusDialog);

        ImageButton.ImageButtonStyle buyStyle = new ImageButton.ImageButtonStyle(/*Assets.skin.get(Button.ButtonStyle.class)*/);
        buyStyle.imageUp = new TextureRegionDrawable(Assets.buyButton);
        buyStyle.imageUp.setMinWidth(160);
        buyStyle.imageUp.setMinHeight(96);
        buyStyle.imageDown = new TextureRegionDrawable(Assets.buyButtonDown);
        buyStyle.imageDown.setMinWidth(160);
        buyStyle.imageDown.setMinHeight(96);
        buyStyle.imageDisabled = new TextureRegionDrawable(Assets.buyButtonDisabled);
        buyStyle.imageDisabled.setMinWidth(160);
        buyStyle.imageDisabled.setMinHeight(96);
        buyStaminaButton = new ImageButton(buyStyle);
        buyStaminaButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                screen.world.caveman.upgradeStamina();
            }
        });
        buyStaminaButton.setSize(160, 96);
        buyStaminaButton.setPosition(wingsDialog.getWidth() / 2 - buyStaminaButton.getWidth() / 2, 25);
        staminaplusDialog.addActor(buyStaminaButton);
    }

    private void buildSpringsDialog() {
        springsDialog = new Window("Springs", Assets.skin);
        springsDialog.setSize(350, Core.VIRTUAL_HEIGHT - 24);
        springsDialog.setPosition(Core.VIRTUAL_WIDTH + springsDialog.getWidth(), 12);
        springsDialog.setKeepWithinStage(false);

        screen.stage.addActor(springsDialog);

        ImageButton.ImageButtonStyle buyStyle = new ImageButton.ImageButtonStyle(/*Assets.skin.get(Button.ButtonStyle.class)*/);
        buyStyle.imageUp = new TextureRegionDrawable(Assets.buyButton);
        buyStyle.imageUp.setMinWidth(160);
        buyStyle.imageUp.setMinHeight(96);
        buyStyle.imageDown = new TextureRegionDrawable(Assets.buyButtonDown);
        buyStyle.imageDown.setMinWidth(160);
        buyStyle.imageDown.setMinHeight(96);
        buyStyle.imageDisabled = new TextureRegionDrawable(Assets.buyButtonDisabled);
        buyStyle.imageDisabled.setMinWidth(160);
        buyStyle.imageDisabled.setMinHeight(96);
        buySpringsButton = new ImageButton(buyStyle);
        buySpringsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                screen.world.caveman.addSprings();
            }
        });
        buySpringsButton.setSize(160, 96);
        buySpringsButton.setPosition(wingsDialog.getWidth() / 2 - buySpringsButton.getWidth() / 2, 25);
        springsDialog.addActor(buySpringsButton);
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

            coinsWidget.moveUp();
        } else {
            shopDialog.addAction(Actions.moveTo(0, Core.VIRTUAL_HEIGHT + shopDialog.getHeight(), 0.4f, Interpolation.linear));

            wingsDialog.addAction(Actions.moveTo(Core.VIRTUAL_WIDTH + wingsDialog.getWidth(), 12, 0.4f, Interpolation.linear));
            steroidsDialog.addAction(Actions.moveTo(Core.VIRTUAL_WIDTH + steroidsDialog.getWidth(), 12, 0.4f, Interpolation.linear));
            staminaplusDialog.addAction(Actions.moveTo(Core.VIRTUAL_WIDTH + staminaplusDialog.getWidth(), 12, 0.4f, Interpolation.linear));
            springsDialog.addAction(Actions.moveTo(Core.VIRTUAL_WIDTH + springsDialog.getWidth(), 12, 0.4f, Interpolation.linear));

            coinsWidget.moveDown();
        }
    }

    public void update() {
        if (CaveMan.coins >= 150) {
            buyWingsButton.setDisabled(false);
            buyWingsButton.setTouchable(Touchable.enabled);
        } else {
            buyWingsButton.setDisabled(true);
            buyWingsButton.setTouchable(Touchable.disabled);
        }

        if (CaveMan.coins >= 100) {
            buySpringsButton.setDisabled(false);
            buySpringsButton.setTouchable(Touchable.enabled);
        } else {
            buySpringsButton.setDisabled(true);
            buySpringsButton.setTouchable(Touchable.disabled);
        }

        if (CaveMan.coins >= 30) {
            buyStaminaButton.setDisabled(false);
            buyStaminaButton.setTouchable(Touchable.enabled);
        } else {
            buyStaminaButton.setDisabled(true);
            buyStaminaButton.setTouchable(Touchable.disabled);
        }

        if (CaveMan.coins >= 50) {
            buySteroidsButton.setDisabled(false);
            buySteroidsButton.setTouchable(Touchable.enabled);
        } else {
            buySteroidsButton.setDisabled(true);
            buySteroidsButton.setTouchable(Touchable.disabled);
        }
    }
}