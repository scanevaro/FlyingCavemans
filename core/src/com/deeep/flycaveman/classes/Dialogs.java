package com.deeep.flycaveman.classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.deeep.flycaveman.Core;
import com.deeep.flycaveman.screens.AbstractScreen;
import com.deeep.flycaveman.screens.GameScreen;

/**
 * Created by scanevaro on 14/01/2015.
 */
public class Dialogs {
    public Window window;
    public Window shopDialog;
    private Dialog wingsDialog, staminaplusDialog, springsDialog, shieldDialog, steroidsDialog;

    public void update(AbstractScreen screen) {
        if (!Core.dialogOpen) {
            Core.dialogOpen = true;
            build(screen);
        } else {
            Core.dialogOpen = false;
            remove();
        }
    }

    private void build(AbstractScreen screen) {
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

            window = new Window("Pause", Assets.skin);

            TextButton noButton = new TextButton("Resume", Assets.skin);
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

            TextButton backButton = new TextButton("Main Menu", Assets.skin);
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

    public Window buildGameOver(final Core game) {
        Window gameOverDialog = new Window("UGHA UGH", Assets.skin);

        //( ͡° ͜ʖ ͡°) < l'elmar face

        gameOverDialog.setSize(512, 400);

        GameScreen.distance = new Label("", Assets.skin);
        GameScreen.distance.setPosition(25, gameOverDialog.getHeight() / 2);
        gameOverDialog.addActor(GameScreen.distance);

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
                game.setScreen(new GameScreen(game));
            }
        });
        retryButton.setSize(96, 96);
        retryButton.setPosition(gameOverDialog.getWidth() - retryButton.getWidth(), 0);
        gameOverDialog.addActor(retryButton);

        ImageButton.ImageButtonStyle homeStyle = new ImageButton.ImageButtonStyle();
        homeStyle.imageUp = new TextureRegionDrawable(Assets.homeButton);
        homeStyle.imageUp.setMinWidth(96);
        homeStyle.imageUp.setMinHeight(96);
        homeStyle.imageDown = new TextureRegionDrawable(Assets.homeButton);
        homeStyle.imageDown.setMinWidth(96);
        homeStyle.imageDown.setMinHeight(96);
        ImageButton homeButton = new ImageButton(homeStyle);
        homeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
            }
        });
        homeButton.setSize(96, 96);
        homeButton.setPosition(0, 0);
        gameOverDialog.addActor(homeButton);

        ImageButton.ImageButtonStyle shopStyle = new ImageButton.ImageButtonStyle();
        shopStyle.imageUp = new TextureRegionDrawable(Assets.shopButton);
        shopStyle.imageUp.setMinWidth(96);
        shopStyle.imageUp.setMinHeight(96);
        shopStyle.imageDown = new TextureRegionDrawable(Assets.shopButton);
        shopStyle.imageDown.setMinWidth(96);
        shopStyle.imageDown.setMinHeight(96);
        ImageButton shopButton = new ImageButton(shopStyle);
        shopButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                shopDialog.setVisible(true);
            }
        });
        shopButton.setSize(96, 96);
        shopButton.setPosition(gameOverDialog.getWidth() / 2 - shopButton.getWidth() / 2, 0);
        gameOverDialog.addActor(shopButton);

        gameOverDialog.setPosition(Core.VIRTUAL_WIDTH / 2 - gameOverDialog.getWidth() / 2, Core.VIRTUAL_HEIGHT / 2 + gameOverDialog.getHeight());

        gameOverDialog.setVisible(false);

        game.screen.stage.addActor(gameOverDialog);

        return gameOverDialog;
    }

    public Window buildShop(final Core game) {
        shopDialog = new Window("Shopuhg!", Assets.skin);

        shopDialog.setSize(Core.VIRTUAL_WIDTH - 25, Core.VIRTUAL_HEIGHT - 25);

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
                game.setScreen(new GameScreen(game));
            }
        });
        retryButton.setSize(96, 96);
        retryButton.setPosition(shopDialog.getWidth() - retryButton.getWidth(), 0);
        shopDialog.addActor(retryButton);

        ImageButton.ImageButtonStyle homeStyle = new ImageButton.ImageButtonStyle();
        homeStyle.imageUp = new TextureRegionDrawable(Assets.homeButton);
        homeStyle.imageUp.setMinWidth(96);
        homeStyle.imageUp.setMinHeight(96);
        homeStyle.imageDown = new TextureRegionDrawable(Assets.homeButton);
        homeStyle.imageDown.setMinWidth(96);
        homeStyle.imageDown.setMinHeight(96);
        ImageButton homeButton = new ImageButton(homeStyle);
        homeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
            }
        });
        homeButton.setSize(96, 96);
        homeButton.setPosition(0, 0);
        shopDialog.addActor(homeButton);

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
                wingsDialog.show(game.screen.stage);
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
                steroidsDialog.show(game.screen.stage);
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
                staminaplusDialog.show(game.screen.stage);
            }
        });
        staminaplusButton.setSize(140, 140);
        staminaplusButton.setPosition(shopDialog.getWidth() / 2 - staminaplusButton.getWidth() / 2, shopDialog.getHeight() / 2 + 38);
        shopDialog.addActor(staminaplusButton);

        ImageButton.ImageButtonStyle shieldStyle = new ImageButton.ImageButtonStyle(Assets.skin.get(Button.ButtonStyle.class));
        shieldStyle.imageUp = new TextureRegionDrawable(Assets.shield);
        shieldStyle.imageUp.setMinWidth(140);
        shieldStyle.imageUp.setMinHeight(140);
        shieldStyle.imageDown = new TextureRegionDrawable(Assets.shield);
        shieldStyle.imageDown.setMinWidth(140);
        shieldStyle.imageDown.setMinHeight(140);
        ImageButton shieldButton = new ImageButton(shieldStyle);
        shieldButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                shieldDialog.show(game.screen.stage);
            }
        });
        shieldButton.setSize(140, 140);
        shieldButton.setPosition(150, shopDialog.getHeight() / 2 - shieldButton.getHeight());
        shopDialog.addActor(shieldButton);

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
                springsDialog.show(game.screen.stage);
            }
        });
        springsButton.setSize(140, 140);
        springsButton.setPosition(300, shopDialog.getHeight() / 2 - springsButton.getHeight());
        shopDialog.addActor(springsButton);

        shopDialog.setPosition(Core.VIRTUAL_WIDTH / 2 - shopDialog.getWidth() / 2, Core.VIRTUAL_HEIGHT / 2 - shopDialog.getHeight());

        shopDialog.setVisible(false);

        game.screen.stage.addActor(shopDialog);

        buildShopDialogs(game.screen);

        return shopDialog;
    }

    private void buildShopDialogs(final AbstractScreen screen) {
        wingsDialog = new Dialog("Buy Wings?", Assets.skin) {
            protected void result(Object object) {
//                if ((Boolean) object)
//                    if (((GameScreen) screen).coins >= 1000) {
//                        if ((Boolean) object)
//                            ((GameScreen) screen).world.caveman.addWings();
//
//                        ((GameScreen) screen).coins -= 1000;
//                    }
            }
        };
        wingsDialog.text("Buy Wings for 1000 coins?");
        TextButton yesButton1 = new TextButton("Yes", Assets.skin);
        TextButton noButton1 = new TextButton("No", Assets.skin);
        wingsDialog.getButtonTable().add(yesButton1).width(96).height(64);
        wingsDialog.getButtonTable().add(noButton1).width(96).height(64);
        wingsDialog.setObject(yesButton1, true);
        wingsDialog.setObject(noButton1, false);

        staminaplusDialog = new Dialog("Upgrade Stamina?", Assets.skin) {
            protected void result(Object object) {
//                if ((Boolean) object)
//                    if (((GameScreen) screen).coins >= 1000) {
//                        if ((Boolean) object)
//                            ((GameScreen) screen).world.caveman.addWings();
//
//                        ((GameScreen) screen).coins -= 1000;
//                    }
            }
        };
        staminaplusDialog.text("Updgrade Stamina for 1000 coins?");
        TextButton yesButton2 = new TextButton("Yes", Assets.skin);
        TextButton noButton2 = new TextButton("No", Assets.skin);
        staminaplusDialog.getButtonTable().add(yesButton2).width(96).height(64);
        staminaplusDialog.getButtonTable().add(noButton2).width(96).height(64);
        staminaplusDialog.setObject(yesButton2, true);
        staminaplusDialog.setObject(noButton2, false);

        steroidsDialog = new Dialog("Buy Steroids?", Assets.skin) {
            protected void result(Object object) {
//                if ((Boolean) object)
//                    if (((GameScreen) screen).coins >= 1000) {
//                        if ((Boolean) object)
//                            ((GameScreen) screen).world.caveman.addWings();
//
//                        ((GameScreen) screen).coins -= 1000;
//                    }
            }
        };
        steroidsDialog.text("Buy Steroids for 1000 coins?");
        TextButton yesButton3 = new TextButton("Yes", Assets.skin);
        TextButton noButton3 = new TextButton("No", Assets.skin);
        steroidsDialog.getButtonTable().add(yesButton3).width(96).height(64);
        steroidsDialog.getButtonTable().add(noButton3).width(96).height(64);
        steroidsDialog.setObject(yesButton3, true);
        steroidsDialog.setObject(noButton3, false);

        shieldDialog = new Dialog("Buy Shield?", Assets.skin) {
            protected void result(Object object) {
//                if ((Boolean) object)
//                    if (((GameScreen) screen).coins >= 1000) {
//                        if ((Boolean) object)
//                            ((GameScreen) screen).world.caveman.addWings();
//
//                        ((GameScreen) screen).coins -= 1000;
//                    }
            }
        };
        shieldDialog.text("Buy Shield for 1000 coins?");
        TextButton yesButton4 = new TextButton("Yes", Assets.skin);
        TextButton noButton4 = new TextButton("No", Assets.skin);
        shieldDialog.getButtonTable().add(yesButton4).width(96).height(64);
        shieldDialog.getButtonTable().add(noButton4).width(96).height(64);
        shieldDialog.setObject(yesButton4, true);
        shieldDialog.setObject(noButton4, false);

        springsDialog = new Dialog("Buy Springs?", Assets.skin) {
            protected void result(Object object) {
//                if ((Boolean) object)
//                    if (((GameScreen) screen).coins >= 1000) {
//                        if ((Boolean) object)
//                            ((GameScreen) screen).world.caveman.addWings();
//
//                        ((GameScreen) screen).coins -= 1000;
//                    }
            }
        };
        springsDialog.text("Buy Springs for 1000 coins?");
        TextButton yesButton5 = new TextButton("Yes", Assets.skin);
        TextButton noButton5 = new TextButton("No", Assets.skin);
        springsDialog.getButtonTable().add(yesButton5).width(96).height(64);
        springsDialog.getButtonTable().add(noButton5).width(96).height(64);
        springsDialog.setObject(yesButton5, true);
        springsDialog.setObject(noButton5, false);
    }
}