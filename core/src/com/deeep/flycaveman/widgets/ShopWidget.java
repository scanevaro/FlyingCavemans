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
    private Window shopDialog, wingsDialog, steroidsDialog, staminaplusDialog, springsDialog, clenchDialog, magnetDialog;
    private GameScreen screen;
    private CoinsWidget coinsWidget;
    private ImageButton buyWingsButton, buySteroidsButton, buyStaminaButton, buySpringsButton, buyMagnetButton, buyClenchButton;
    private boolean wingsOpen, steroidsOpen, staminaOpen, springsOpen, magnetOpen, clenchOpen;
    private Image starsWings[], starsSteroids[], starsStamina[], starsSprings[], starsMagnet[], starsClench[];

    private final int wingsPrice = 1/*25*/,
            springsPrice = 1/*35*/,
            steroidsPrice = 1/*25*/,
            staminaPrice = 1/*15*/,
            magnetPrice = 1/*35*/,
            clenchPrice = 1/*35*/;

    public ShopWidget(final Core game) {
        this.screen = (GameScreen) game.screen;

        shopDialog = new Window("Shopuhg!", Assets.skin);
        shopDialog.setSize(680, Core.VIRTUAL_HEIGHT - 25);
        shopDialog.setPosition(0, Core.VIRTUAL_HEIGHT + shopDialog.getHeight());
        shopDialog.setKeepWithinStage(false);
        shopDialog.removeCaptureListener(shopDialog.getCaptureListeners().first());

        ImageButton.ImageButtonStyle retryStyle = new ImageButton.ImageButtonStyle();
        retryStyle.imageUp = new TextureRegionDrawable(Assets.restartButton);
        retryStyle.imageUp.setMinWidth(104);
        retryStyle.imageUp.setMinHeight(104);
        retryStyle.imageDown = new TextureRegionDrawable(Assets.buttonBroken);
        retryStyle.imageDown.setMinWidth(145);
        retryStyle.imageDown.setMinHeight(145);
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
        retryButton.setSize(145, 145);
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
        steroidsButton.setPosition(50 + 15 + 20 + wingsButton.getWidth() + 35, shopDialog.getHeight() / 2 + 38);
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
        staminaplusButton.setPosition(50 + 15 + 45 + wingsButton.getWidth() + steroidsButton.getWidth() + 35 + 35, shopDialog.getHeight() / 2 + 38);
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
        springsButton.setPosition(50, shopDialog.getHeight() / 2 - springsButton.getHeight() + 25);
        shopDialog.addActor(springsButton);

        ImageButton.ImageButtonStyle magnetStyle = new ImageButton.ImageButtonStyle(Assets.skin.get(Button.ButtonStyle.class));
        magnetStyle.imageUp = new TextureRegionDrawable(Assets.magnet);
        magnetStyle.imageUp.setMinWidth(140);
        magnetStyle.imageUp.setMinHeight(140);
        magnetStyle.imageDown = new TextureRegionDrawable(Assets.magnet);
        magnetStyle.imageDown.setMinWidth(140);
        magnetStyle.imageDown.setMinHeight(140);
        ImageButton magnetButton = new ImageButton(magnetStyle);
        magnetButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                show("magnetDialog");
            }
        });
        magnetButton.setSize(140, 140);
        magnetButton.setPosition(225 + 35, shopDialog.getHeight() / 2 - magnetButton.getHeight() + 25);
        shopDialog.addActor(magnetButton);

        ImageButton.ImageButtonStyle clenchStyle = new ImageButton.ImageButtonStyle(Assets.skin.get(Button.ButtonStyle.class));
        clenchStyle.imageUp = new TextureRegionDrawable(Assets.clench);
        clenchStyle.imageUp.setMinWidth(140);
        clenchStyle.imageUp.setMinHeight(140);
        clenchStyle.imageDown = new TextureRegionDrawable(Assets.clench);
        clenchStyle.imageDown.setMinWidth(140);
        clenchStyle.imageDown.setMinHeight(140);
        ImageButton clenchButton = new ImageButton(clenchStyle);
        clenchButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                show("clenchDialog");
            }
        });
        clenchButton.setSize(140, 140);
        clenchButton.setPosition(390 + 35 + 35, shopDialog.getHeight() / 2 - clenchButton.getHeight() + 25);
        shopDialog.addActor(clenchButton);

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
        buildMagnetDialog();
        buildClenchDialog();
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

        starsWings = new Image[3];
        starsWings[0] = new Image(Assets.starBlack);
        starsWings[1] = new Image(Assets.starBlack);
        starsWings[2] = new Image(Assets.starBlack);
        int posX = 70;
        for (Image staR : starsWings) {
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
            public void clicked(InputEvent event, float x, float y) {
                screen.world.caveman.addWings(wingsPrice);
            }
        });
        buyWingsButton.setSize(160, 96);
        buyWingsButton.setPosition(0, 0);
        wingsDialog.addActor(buyWingsButton);

        CoinSprite coinSprite = new CoinSprite();
        coinSprite.setSize(64, 64);
        coinSprite.setPosition(wingsDialog.getWidth() / 2, 15);
        wingsDialog.addActor(coinSprite);

        Label cost = new Label("= " + wingsPrice, Assets.skin);
        cost.setPosition(wingsDialog.getWidth() / 2 + 64 + 4, 24);
        wingsDialog.addActor(cost);
    }

    private void buildSteroidsDialog() {
        steroidsDialog = new Window("Steroids", Assets.skin);
        steroidsDialog.setSize(350, Core.VIRTUAL_HEIGHT - 24);
        steroidsDialog.setPosition(Core.VIRTUAL_WIDTH + steroidsDialog.getWidth(), 12);
        steroidsDialog.setKeepWithinStage(false);

        screen.stage.addActor(steroidsDialog);

        TextArea textArea = new TextArea("Steroids good for flying higher... Steroids taste funny", Assets.skin);
        textArea.setSize(300, 300);
        textArea.setPosition(30, 134);
        textArea.clearListeners();
        steroidsDialog.addActor(textArea);

        starsSteroids = new Image[3];
        starsSteroids[0] = new Image(Assets.starBlack);
        starsSteroids[1] = new Image(Assets.starBlack);
        starsSteroids[2] = new Image(Assets.starBlack);
        int posX = 70;
        for (Image staR : starsSteroids) {
            staR.setSize(64, 64);
            staR.setPosition(posX, 88);
            posX += 70;
            steroidsDialog.addActor(staR);
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
        buySteroidsButton = new ImageButton(buyStyle);
        buySteroidsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                screen.world.caveman.addSteroids(steroidsPrice);
            }
        });
        buySteroidsButton.setSize(160, 96);
        buySteroidsButton.setPosition(0, 0);
        steroidsDialog.addActor(buySteroidsButton);

        CoinSprite coinSprite = new CoinSprite();
        coinSprite.setSize(64, 64);
        coinSprite.setPosition(wingsDialog.getWidth() / 2, 15);
        steroidsDialog.addActor(coinSprite);

        Label cost = new Label("= " + steroidsPrice, Assets.skin);
        cost.setPosition(wingsDialog.getWidth() / 2 + 64 + 4, 24);
        steroidsDialog.addActor(cost);
    }

    private void buildStaminaDialog() {
        staminaplusDialog = new Window("Stamina Plus", Assets.skin);
        staminaplusDialog.setSize(350, Core.VIRTUAL_HEIGHT - 24);
        staminaplusDialog.setPosition(Core.VIRTUAL_WIDTH + staminaplusDialog.getWidth(), 12);
        staminaplusDialog.setKeepWithinStage(false);

        screen.stage.addActor(staminaplusDialog);

        TextArea textArea = new TextArea("Better Stamina, Longer Flapping", Assets.skin);
        textArea.setSize(300, 300);
        textArea.setPosition(30, 134);
        textArea.clearListeners();
        staminaplusDialog.addActor(textArea);

        starsStamina = new Image[3];
        starsStamina[0] = new Image(Assets.starBlack);
        starsStamina[1] = new Image(Assets.starBlack);
        starsStamina[2] = new Image(Assets.starBlack);
        int posX = 70;
        for (Image staR : starsStamina) {
            staR.setSize(64, 64);
            staR.setPosition(posX, 88);
            posX += 70;
            staminaplusDialog.addActor(staR);
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
        buyStaminaButton = new ImageButton(buyStyle);
        buyStaminaButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                screen.world.caveman.addStamina(staminaPrice);
            }
        });
        buyStaminaButton.setSize(160, 96);
        buyStaminaButton.setPosition(0, 0);
        staminaplusDialog.addActor(buyStaminaButton);

        CoinSprite coinSprite = new CoinSprite();
        coinSprite.setSize(64, 64);
        coinSprite.setPosition(wingsDialog.getWidth() / 2, 15);
        staminaplusDialog.addActor(coinSprite);

        Label cost = new Label("= " + staminaPrice, Assets.skin);
        cost.setPosition(wingsDialog.getWidth() / 2 + 64 + 4, 24);
        staminaplusDialog.addActor(cost);
    }

    private void buildSpringsDialog() {
        springsDialog = new Window("Springs", Assets.skin);
        springsDialog.setSize(350, Core.VIRTUAL_HEIGHT - 24);
        springsDialog.setPosition(Core.VIRTUAL_WIDTH + springsDialog.getWidth(), 12);
        springsDialog.setKeepWithinStage(false);

        screen.stage.addActor(springsDialog);

        TextArea textArea = new TextArea("Bouncy bouncy", Assets.skin);
        textArea.setSize(300, 300);
        textArea.setPosition(30, 134);
        textArea.clearListeners();
        springsDialog.addActor(textArea);

        starsSprings = new Image[3];
        starsSprings[0] = new Image(Assets.starBlack);
        starsSprings[1] = new Image(Assets.starBlack);
        starsSprings[2] = new Image(Assets.starBlack);
        int posX = 70;
        for (Image staR : starsSprings) {
            staR.setSize(64, 64);
            staR.setPosition(posX, 88);
            posX += 70;
            springsDialog.addActor(staR);
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
        buySpringsButton = new ImageButton(buyStyle);
        buySpringsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                screen.world.caveman.addSprings(springsPrice);
            }
        });
        buySpringsButton.setSize(160, 96);
        buySpringsButton.setPosition(0, 0);
        springsDialog.addActor(buySpringsButton);

        CoinSprite coinSprite = new CoinSprite();
        coinSprite.setSize(64, 64);
        coinSprite.setPosition(wingsDialog.getWidth() / 2, 15);
        springsDialog.addActor(coinSprite);

        Label cost = new Label("= " + springsPrice, Assets.skin);
        cost.setPosition(wingsDialog.getWidth() / 2 + 64 + 4, 24);
        springsDialog.addActor(cost);
    }

    private void buildMagnetDialog() {
        magnetDialog = new Window("Magnet", Assets.skin);
        magnetDialog.setSize(350, Core.VIRTUAL_HEIGHT - 24);
        magnetDialog.setPosition(Core.VIRTUAL_WIDTH + magnetDialog.getWidth(), 12);
        magnetDialog.setKeepWithinStage(false);

        screen.stage.addActor(magnetDialog);

        TextArea textArea = new TextArea("Absorv powerups when they are close to you", Assets.skin);
        textArea.setSize(300, 300);
        textArea.setPosition(30, 134);
        textArea.clearListeners();
        magnetDialog.addActor(textArea);

        starsMagnet = new Image[3];
        starsMagnet[0] = new Image(Assets.starBlack);
        starsMagnet[1] = new Image(Assets.starBlack);
        starsMagnet[2] = new Image(Assets.starBlack);
        int posX = 70;
        for (Image staR : starsMagnet) {
            staR.setSize(64, 64);
            staR.setPosition(posX, 88);
            posX += 70;
            magnetDialog.addActor(staR);
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
        buyMagnetButton = new ImageButton(buyStyle);
        buyMagnetButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                screen.world.caveman.addMagnet(magnetPrice);
            }
        });
        buyMagnetButton.setSize(160, 96);
        buyMagnetButton.setPosition(0, 0);
        magnetDialog.addActor(buyMagnetButton);

        CoinSprite coinSprite = new CoinSprite();
        coinSprite.setSize(64, 64);
        coinSprite.setPosition(magnetDialog.getWidth() / 2, 15);
        magnetDialog.addActor(coinSprite);

        Label cost = new Label("= " + magnetPrice, Assets.skin);
        cost.setPosition(magnetDialog.getWidth() / 2 + 64 + 4, 24);
        magnetDialog.addActor(cost);
    }

    private void buildClenchDialog() {
        clenchDialog = new Window("Clench Butt!", Assets.skin);
        clenchDialog.setSize(350, Core.VIRTUAL_HEIGHT - 24);
        clenchDialog.setPosition(Core.VIRTUAL_WIDTH + clenchDialog.getWidth(), 12);
        clenchDialog.setKeepWithinStage(false);

        screen.stage.addActor(clenchDialog);

        TextArea textArea = new TextArea("Learn how to clench the butt to bounce a bit higher off the ground!... It hurts!!", Assets.skin);
        textArea.setSize(300, 300);
        textArea.setPosition(30, 134);
        textArea.clearListeners();
        clenchDialog.addActor(textArea);

        starsClench = new Image[3];
        starsClench[0] = new Image(Assets.starBlack);
        starsClench[1] = new Image(Assets.starBlack);
        starsClench[2] = new Image(Assets.starBlack);
        int posX = 70;
        for (Image staR : starsClench) {
            staR.setSize(64, 64);
            staR.setPosition(posX, 88);
            posX += 70;
            clenchDialog.addActor(staR);
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
        buyClenchButton = new ImageButton(buyStyle);
        buyClenchButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                screen.world.caveman.addClench(clenchPrice);
            }
        });
        buyClenchButton.setSize(160, 96);
        buyClenchButton.setPosition(0, 0);
        clenchDialog.addActor(buyClenchButton);

        CoinSprite coinSprite = new CoinSprite();
        coinSprite.setSize(64, 64);
        coinSprite.setPosition(clenchDialog.getWidth() / 2, 15);
        clenchDialog.addActor(coinSprite);

        Label cost = new Label("= " + clenchPrice, Assets.skin);
        cost.setPosition(clenchDialog.getWidth() / 2 + 64 + 4, 24);
        clenchDialog.addActor(cost);
    }

    private void show(String dialog) {
        if (wingsOpen) screen.stage.addActor(wingsDialog);
        else if (steroidsOpen) screen.stage.addActor(steroidsDialog);
        else if (staminaOpen) screen.stage.addActor(staminaplusDialog);
        else if (springsOpen) screen.stage.addActor(springsDialog);
        else if (magnetOpen) screen.stage.addActor(magnetDialog);
        else if (clenchOpen) screen.stage.addActor(clenchDialog);

        if (dialog.equals("wingsDialog")) {
            wingsOpen = true;
            steroidsOpen = false;
            staminaOpen = false;
            springsOpen = false;
            magnetOpen = false;
            clenchOpen = false;
            screen.stage.addActor(wingsDialog);

            wingsDialog.addAction(Actions.moveTo(Core.VIRTUAL_WIDTH - wingsDialog.getWidth(), 12, 0.4f, Interpolation.linear));
            steroidsDialog.addAction(Actions.moveTo(Core.VIRTUAL_WIDTH + steroidsDialog.getWidth(), 12, 0.4f, Interpolation.linear));
            staminaplusDialog.addAction(Actions.moveTo(Core.VIRTUAL_WIDTH + staminaplusDialog.getWidth(), 12, 0.4f, Interpolation.linear));
            springsDialog.addAction(Actions.moveTo(Core.VIRTUAL_WIDTH + springsDialog.getWidth(), 12, 0.4f, Interpolation.linear));
            magnetDialog.addAction(Actions.moveTo(Core.VIRTUAL_WIDTH + springsDialog.getWidth(), 12, 0.4f, Interpolation.linear));
            clenchDialog.addAction(Actions.moveTo(Core.VIRTUAL_WIDTH + springsDialog.getWidth(), 12, 0.4f, Interpolation.linear));
        } else if (dialog.equals("steroidsDialog")) {
            steroidsOpen = true;
            wingsOpen = false;
            staminaOpen = false;
            springsOpen = false;
            magnetOpen = false;
            clenchOpen = false;
            screen.stage.addActor(steroidsDialog);

            wingsDialog.addAction(Actions.moveTo(Core.VIRTUAL_WIDTH + wingsDialog.getWidth(), 12, 0.4f, Interpolation.linear));
            steroidsDialog.addAction(Actions.moveTo(Core.VIRTUAL_WIDTH - steroidsDialog.getWidth(), 12, 0.4f, Interpolation.linear));
            staminaplusDialog.addAction(Actions.moveTo(Core.VIRTUAL_WIDTH + staminaplusDialog.getWidth(), 12, 0.4f, Interpolation.linear));
            springsDialog.addAction(Actions.moveTo(Core.VIRTUAL_WIDTH + springsDialog.getWidth(), 12, 0.4f, Interpolation.linear));
            magnetDialog.addAction(Actions.moveTo(Core.VIRTUAL_WIDTH + springsDialog.getWidth(), 12, 0.4f, Interpolation.linear));
            clenchDialog.addAction(Actions.moveTo(Core.VIRTUAL_WIDTH + springsDialog.getWidth(), 12, 0.4f, Interpolation.linear));
        } else if (dialog.equals("staminaplusDialog")) {
            staminaOpen = true;
            steroidsOpen = false;
            wingsOpen = false;
            springsOpen = false;
            magnetOpen = false;
            clenchOpen = false;
            screen.stage.addActor(staminaplusDialog);

            wingsDialog.addAction(Actions.moveTo(Core.VIRTUAL_WIDTH + wingsDialog.getWidth(), 12, 0.4f, Interpolation.linear));
            steroidsDialog.addAction(Actions.moveTo(Core.VIRTUAL_WIDTH + steroidsDialog.getWidth(), 12, 0.4f, Interpolation.linear));
            staminaplusDialog.addAction(Actions.moveTo(Core.VIRTUAL_WIDTH - staminaplusDialog.getWidth(), 12, 0.4f, Interpolation.linear));
            springsDialog.addAction(Actions.moveTo(Core.VIRTUAL_WIDTH + springsDialog.getWidth(), 12, 0.4f, Interpolation.linear));
            magnetDialog.addAction(Actions.moveTo(Core.VIRTUAL_WIDTH + springsDialog.getWidth(), 12, 0.4f, Interpolation.linear));
            clenchDialog.addAction(Actions.moveTo(Core.VIRTUAL_WIDTH + springsDialog.getWidth(), 12, 0.4f, Interpolation.linear));
        } else if (dialog.equals("springsDialog")) {
            springsOpen = true;
            steroidsOpen = false;
            wingsOpen = false;
            staminaOpen = false;
            magnetOpen = false;
            clenchOpen = false;
            screen.stage.addActor(springsDialog);

            wingsDialog.addAction(Actions.moveTo(Core.VIRTUAL_WIDTH + wingsDialog.getWidth(), 12, 0.4f, Interpolation.linear));
            steroidsDialog.addAction(Actions.moveTo(Core.VIRTUAL_WIDTH + steroidsDialog.getWidth(), 12, 0.4f, Interpolation.linear));
            staminaplusDialog.addAction(Actions.moveTo(Core.VIRTUAL_WIDTH + staminaplusDialog.getWidth(), 12, 0.4f, Interpolation.linear));
            springsDialog.addAction(Actions.moveTo(Core.VIRTUAL_WIDTH - springsDialog.getWidth(), 12, 0.4f, Interpolation.linear));
            magnetDialog.addAction(Actions.moveTo(Core.VIRTUAL_WIDTH + springsDialog.getWidth(), 12, 0.4f, Interpolation.linear));
            clenchDialog.addAction(Actions.moveTo(Core.VIRTUAL_WIDTH + springsDialog.getWidth(), 12, 0.4f, Interpolation.linear));
        } else if (dialog.equals("magnetDialog")) {
            springsOpen = false;
            steroidsOpen = false;
            wingsOpen = false;
            staminaOpen = false;
            magnetOpen = true;
            clenchOpen = false;
            screen.stage.addActor(magnetDialog);

            wingsDialog.addAction(Actions.moveTo(Core.VIRTUAL_WIDTH + wingsDialog.getWidth(), 12, 0.4f, Interpolation.linear));
            steroidsDialog.addAction(Actions.moveTo(Core.VIRTUAL_WIDTH + steroidsDialog.getWidth(), 12, 0.4f, Interpolation.linear));
            staminaplusDialog.addAction(Actions.moveTo(Core.VIRTUAL_WIDTH + staminaplusDialog.getWidth(), 12, 0.4f, Interpolation.linear));
            springsDialog.addAction(Actions.moveTo(Core.VIRTUAL_WIDTH + springsDialog.getWidth(), 12, 0.4f, Interpolation.linear));
            magnetDialog.addAction(Actions.moveTo(Core.VIRTUAL_WIDTH - springsDialog.getWidth(), 12, 0.4f, Interpolation.linear));
            clenchDialog.addAction(Actions.moveTo(Core.VIRTUAL_WIDTH + springsDialog.getWidth(), 12, 0.4f, Interpolation.linear));
        } else if (dialog.equals("clenchDialog")) {
            springsOpen = true;
            steroidsOpen = false;
            wingsOpen = false;
            staminaOpen = false;
            magnetOpen = false;
            clenchOpen = true;
            screen.stage.addActor(clenchDialog);

            wingsDialog.addAction(Actions.moveTo(Core.VIRTUAL_WIDTH + wingsDialog.getWidth(), 12, 0.4f, Interpolation.linear));
            steroidsDialog.addAction(Actions.moveTo(Core.VIRTUAL_WIDTH + steroidsDialog.getWidth(), 12, 0.4f, Interpolation.linear));
            staminaplusDialog.addAction(Actions.moveTo(Core.VIRTUAL_WIDTH + staminaplusDialog.getWidth(), 12, 0.4f, Interpolation.linear));
            springsDialog.addAction(Actions.moveTo(Core.VIRTUAL_WIDTH + springsDialog.getWidth(), 12, 0.4f, Interpolation.linear));
            magnetDialog.addAction(Actions.moveTo(Core.VIRTUAL_WIDTH + springsDialog.getWidth(), 12, 0.4f, Interpolation.linear));
            clenchDialog.addAction(Actions.moveTo(Core.VIRTUAL_WIDTH - springsDialog.getWidth(), 12, 0.4f, Interpolation.linear));
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
            magnetDialog.addAction(Actions.moveTo(Core.VIRTUAL_WIDTH + springsDialog.getWidth(), 12, 0.4f, Interpolation.linear));
            clenchDialog.addAction(Actions.moveTo(Core.VIRTUAL_WIDTH + springsDialog.getWidth(), 12, 0.4f, Interpolation.linear));

            coinsWidget.moveDown();
        }
    }

    public void update() {
        updateButtons();
        updateStars();
    }

    private void updateButtons() {
        if (CaveMan.coins >= wingsPrice) {
            buyWingsButton.setDisabled(false);
            buyWingsButton.setTouchable(Touchable.enabled);
        } else {
            buyWingsButton.setDisabled(true);
            buyWingsButton.setTouchable(Touchable.disabled);
        }
        if (CaveMan.coins >= springsPrice) {
            buySpringsButton.setDisabled(false);
            buySpringsButton.setTouchable(Touchable.enabled);
        } else {
            buySpringsButton.setDisabled(true);
            buySpringsButton.setTouchable(Touchable.disabled);
        }
        if (CaveMan.coins >= staminaPrice) {
            buyStaminaButton.setDisabled(false);
            buyStaminaButton.setTouchable(Touchable.enabled);
        } else {
            buyStaminaButton.setDisabled(true);
            buyStaminaButton.setTouchable(Touchable.disabled);
        }
        if (CaveMan.coins >= steroidsPrice) {
            buySteroidsButton.setDisabled(false);
            buySteroidsButton.setTouchable(Touchable.enabled);
        } else {
            buySteroidsButton.setDisabled(true);
            buySteroidsButton.setTouchable(Touchable.disabled);
        }
        if (CaveMan.coins >= magnetPrice) {
            buyMagnetButton.setDisabled(false);
            buyMagnetButton.setTouchable(Touchable.enabled);
        } else {
            buyMagnetButton.setDisabled(true);
            buyMagnetButton.setTouchable(Touchable.disabled);
        }
        if (CaveMan.coins >= clenchPrice) {
            buyClenchButton.setDisabled(false);
            buyClenchButton.setTouchable(Touchable.enabled);
        } else {
            buyClenchButton.setDisabled(true);
            buyClenchButton.setTouchable(Touchable.disabled);
        }
    }

    private void updateStars() {
        if (CaveMan.wings == 1) starsWings[0].setDrawable(new TextureRegionDrawable(Assets.starBright));
        if (CaveMan.wings == 2) starsWings[1].setDrawable(new TextureRegionDrawable(Assets.starBright));
        if (CaveMan.wings == 3) starsWings[2].setDrawable(new TextureRegionDrawable(Assets.starBright));
        if (CaveMan.steroids == 1) starsSteroids[0].setDrawable(new TextureRegionDrawable(Assets.starBright));
        if (CaveMan.steroids == 2) starsSteroids[1].setDrawable(new TextureRegionDrawable(Assets.starBright));
        if (CaveMan.steroids == 3) starsSteroids[2].setDrawable(new TextureRegionDrawable(Assets.starBright));
        if (CaveMan.staminaSize == 1) starsStamina[0].setDrawable(new TextureRegionDrawable(Assets.starBright));
        if (CaveMan.staminaSize == 2) starsStamina[1].setDrawable(new TextureRegionDrawable(Assets.starBright));
        if (CaveMan.staminaSize == 3) starsStamina[2].setDrawable(new TextureRegionDrawable(Assets.starBright));
        if (CaveMan.springs == 1) starsSprings[0].setDrawable(new TextureRegionDrawable(Assets.starBright));
        if (CaveMan.springs == 2) starsSprings[1].setDrawable(new TextureRegionDrawable(Assets.starBright));
        if (CaveMan.springs == 3) starsSprings[2].setDrawable(new TextureRegionDrawable(Assets.starBright));
        if (CaveMan.magnet == 1) starsMagnet[0].setDrawable(new TextureRegionDrawable(Assets.starBright));
        if (CaveMan.magnet == 2) starsMagnet[1].setDrawable(new TextureRegionDrawable(Assets.starBright));
        if (CaveMan.magnet == 3) starsMagnet[2].setDrawable(new TextureRegionDrawable(Assets.starBright));
        if (CaveMan.clench == 1) starsClench[0].setDrawable(new TextureRegionDrawable(Assets.starBright));
        if (CaveMan.clench == 2) starsClench[1].setDrawable(new TextureRegionDrawable(Assets.starBright));
        if (CaveMan.clench == 3) starsClench[2].setDrawable(new TextureRegionDrawable(Assets.starBright));
    }
}