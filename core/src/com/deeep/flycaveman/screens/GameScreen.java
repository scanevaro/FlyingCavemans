package com.deeep.flycaveman.screens;

import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.deeep.flycaveman.Assets;
import com.deeep.flycaveman.Core;
import com.deeep.flycaveman.input.GameInputProcessor;
import com.deeep.flycaveman.widgets.*;
import com.deeep.flycaveman.world.World;

/**
 * Created by scanevaro on 10/10/2014.
 */
public class GameScreen extends AbstractScreen {
    private Core game;
    private TweenManager tweenManager;
    /**
     * Screen
     */
    private SpriteBatch batch;
    private Stage worldStage;
    public static OrthographicCamera gameCamera;
    private GameInputProcessor gameInputProcessor;
    /**
     * Widgets
     */
    private TextButton distanceLabel, heightLabel;
    public static Label distance;
    private ImageButton pauseButton;
    private Window gameOverDialog, shopDialog;
    private GameOverWidget gameOverWidget;
    private StaminaBar staminaBar;
    private FlapButton flapButton;
    private ExpressionsWidget expressions;
    private CoinsWidget coinsWidget;
    /**
     * World
     */
    public World world;
    private float height;

    public GameScreen(Core game) {
        this.game = game;
    }

    @Override
    public void show() {
        if (tweenManager == null) tweenManager = new TweenManager();

        prepareScreen();
        setWidgets();
        prepareWorld();

        staminaBar = new StaminaBar(world.caveman);

        getGameCamera();
        configureWidgets();
        setLayout();
        setInputProcessor();
        prepareGameOverDialog();
        prepareShopDialog();

        expressions.setCaveman(world.caveman);
        coinsWidget.setCaveMan(world.caveman);
    }

    private void prepareScreen() {
        batch = game.getSpriteBatch();
        stage = new Stage(new FitViewport(Core.VIRTUAL_WIDTH, Core.VIRTUAL_HEIGHT), batch);
    }

    private void setWidgets() {
        distanceLabel = new TextButton("Distance: ", Assets.skin);
        heightLabel = new TextButton("Height: ", Assets.skin);

        ImageButton.ImageButtonStyle pauseStyle = new ImageButton.ImageButtonStyle();
        pauseStyle.imageUp = new TextureRegionDrawable(Assets.pauseUp);
        pauseStyle.imageUp.setMinWidth(64);
        pauseStyle.imageUp.setMinHeight(64);
        pauseStyle.imageDown = new TextureRegionDrawable(Assets.pauseUp);
        pauseStyle.imageDown.setMinWidth(64);
        pauseStyle.imageDown.setMinHeight(64);
        pauseButton = new ImageButton(pauseStyle);

        flapButton = new FlapButton();
        expressions = new ExpressionsWidget();
        coinsWidget = new CoinsWidget();
    }

    private void configureWidgets() {
        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.dialogs.update(game.screen);
            }
        });

        distanceLabel.setColor(new Color(1, 1, 1, 0));
        heightLabel.setColor(new Color(1, 1, 1, 0));
        pauseButton.setColor(new Color(1, 1, 1, 0));
        staminaBar.setColor(new Color(1, 1, 1, 0));
        flapButton.setColor(new Color(1, 1, 1, 0));
        expressions.setColor(new Color(1, 1, 1, 0));
        coinsWidget.setColor(new Color(1, 1, 1, 0));
    }

    private void prepareWorld() {
        worldStage = new Stage(new FitViewport(Core.BOX2D_VIRTUAL_WIDTH, Core.BOX2D_VIRTUAL_HEIGHT));
        gameCamera = (OrthographicCamera) worldStage.getCamera();
        world = new World(worldStage, stage, false);
        stage.addActor(world);

        flapButton.setCaveMan(world.caveman);
    }

    private void getGameCamera() {
        gameCamera = (OrthographicCamera) worldStage.getCamera();
        gameCamera.position.set(Core.BOX2D_VIRTUAL_WIDTH / 2, Core.BOX2D_VIRTUAL_HEIGHT / 2 - 2, 0);
    }

    private void setLayout() {
        distanceLabel.setSize(224, 64);
        distanceLabel.setPosition(Core.VIRTUAL_WIDTH - distanceLabel.getWidth(), 1);
        heightLabel.setSize(160, 64);
        heightLabel.setPosition(Core.VIRTUAL_WIDTH / 2 + staminaBar.background.getWidth() - heightLabel.getWidth() / 2, 1);
        pauseButton.setSize(64, 64);
        pauseButton.setPosition(0, Core.VIRTUAL_HEIGHT - pauseButton.getHeight());
        flapButton.setSize(96, 96);
        flapButton.setPosition(0, 0);

        stage.addActor(distanceLabel);
        stage.addActor(heightLabel);
        stage.addActor(pauseButton);
        stage.addActor(staminaBar);
        stage.addActor(flapButton);
        stage.addActor(expressions);
        stage.addActor(coinsWidget);
    }

    private void setInputProcessor() {
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(gameInputProcessor = new GameInputProcessor(game, world));
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    private void prepareShopDialog() {
        shopDialog = game.dialogs.buildShop(game);
    }

    private void prepareGameOverDialog() {
        gameOverDialog = game.dialogs.buildGameOver(game);
        gameOverWidget = new GameOverWidget(game, shopDialog);
    }

    @Override
    public void render(float delta) {

        if (gameInputProcessor.flying) {
            distanceLabel.addAction(Actions.delay(0.5f, Actions.fadeIn(1.0f)));
            heightLabel.addAction(Actions.delay(0.5f, Actions.fadeIn(1.0f)));
            pauseButton.addAction(Actions.delay(0.5f, Actions.fadeIn(1.0f)));
            staminaBar.addAction(Actions.delay(0.5f, Actions.fadeIn(1.0f)));
            flapButton.addAction(Actions.delay(0.5f, Actions.fadeIn(1.0f)));
            expressions.addAction(Actions.delay(0.5f, Actions.fadeIn(1.0f)));
            coinsWidget.addAction(Actions.delay(0.5f, Actions.fadeIn(1.0f)));
        }

        /**Updates*/
        if (!Core.dialogOpen) {
            gameInputProcessor.update(delta);
            updateUI();
            updateGameCam();
            world.update(delta);
            stage.act();

            if (world.isGameOver()) {

                distanceLabel.addAction(Actions.delay(0.25f, Actions.fadeOut(1.0f)));
                heightLabel.addAction(Actions.delay(0.25f, Actions.fadeOut(1.0f)));
                pauseButton.addAction(Actions.delay(0.25f, Actions.fadeOut(1.0f)));
                staminaBar.fadeOut();
                flapButton.addAction(Actions.delay(0.25f, Actions.fadeOut(1.0f)));
                expressions.fadeOut();
                coinsWidget.fadeOut();

                gameOverWidget.setVisible();
//                if (shopDialog.isVisible()) gameOverDialog.setVisible(false);
//                else gameOverDialog.setVisible(true);

//                distance.setText(distanceLabel.getText().toString());
            }
        }

        /**Draws*/
        stage.draw();
    }

    private void updateUI() {
        //todo make this visible in gameoverscreen
        distanceLabel.setText("Distance: " + String.valueOf((int) (world.caveman.body.getPosition().x - Core.BOX2D_VIRTUAL_WIDTH / 3)));
        heightLabel.setText("Height: " + String.valueOf(world.caveman.body.getPosition().y - 1.5f).substring(0, 3));
    }

    private void updateGameCam() {
        if (world.caveman.body.getPosition().x > world.caveman.startPosX)
            if (world.caveman.body.getPosition().y >= Core.BOX2D_VIRTUAL_HEIGHT / 2 - 2) {
                gameCamera.position.set(world.caveman.body.getPosition().x + 5 + 5, world.caveman.body.getPosition().y + 0.5f, 0);
                /**Update Start screen camera*/
                world.updateStartCam((world.caveman.body.getPosition().x + 5 + 5) * 35, (world.caveman.body.getPosition().y + 2.5f) * 35, 0);
            } else {
                gameCamera.position.set(world.caveman.body.getPosition().x + 5 + 5, Core.BOX2D_VIRTUAL_HEIGHT / 2 - 2, 0);
                /**Update Start screen camera*/
                world.updateStartCam((world.caveman.body.getPosition().x + 5 + 5) * 35, (Core.VIRTUAL_HEIGHT / 2), 0);
            }

        if (world.caveman.body.getPosition().y > (Core.BOX2D_VIRTUAL_HEIGHT / 2f)) {
            height = world.caveman.body.getPosition().y - 9;
            gameCamera.zoom = 0.9f + (height * 2.5f) / 56;
            world.updateStartZoom(0.9f + (height * 2.5f) / 56);
        } else {
            gameCamera.zoom = 0.9f;
            world.updateStartZoom(0.9f);
        }
        gameCamera.zoom = Math.min(gameCamera.zoom, 2f);
        gameCamera.update();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
        worldStage.getViewport().update(width, height);
        world.resize(width, height);
    }

    @Override
    public void dispose() {
        stage.dispose();
        world.dispose();
    }
}