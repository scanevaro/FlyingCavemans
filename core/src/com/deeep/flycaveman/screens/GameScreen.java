package com.deeep.flycaveman.screens;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenManager;
import aurelienribon.tweenengine.equations.Cubic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.deeep.flycaveman.Core;
import com.deeep.flycaveman.classes.ActorAccessor;
import com.deeep.flycaveman.classes.Assets;
import com.deeep.flycaveman.classes.World;
import com.deeep.flycaveman.entities.StaminaBar;
import com.deeep.flycaveman.input.GameInputProcessor;

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
    private OrthographicCamera gameCamera;
    private GameInputProcessor gameInputProcessor;
    /**
     * Widgets
     */
    private TextButton distanceLabel, heightLabel;
    private Label distance;
    private ImageButton pauseButton;
    private Window gameOverDialog;
    private StaminaBar staminaBar;
    /**
     * World
     */
    private World world;

    public GameScreen(Core game) {
        this.game = game;
    }

    @Override
    public void show() {
        if (tweenManager == null) tweenManager = new TweenManager();

        prepareScreen();
        setWidgets();
        configureWidgets();
        prepareWorld();

        staminaBar = new StaminaBar(world.caveman);

        getGameCamera();
        setLayout();
        setInputProcessor();
        prepareGameOverDialog();

//        if (!Assets.getAssets().getMusic().isPlaying())
//            Assets.getAssets().getMusic().play();

        Assets.font.setScale(0.5f);
    }

    private void prepareScreen() {
        batch = game.getSpriteBatch();
        stage = new Stage(new FitViewport(Core.VIRTUAL_WIDTH, Core.VIRTUAL_HEIGHT), batch);
    }

    private void setWidgets() {
        distanceLabel = new TextButton("Distance: ", Assets.skin);
        heightLabel = new TextButton("Height: ", Assets.skin);

        ImageButton.ImageButtonStyle restartStyle = new ImageButton.ImageButtonStyle();
        restartStyle.imageUp = new TextureRegionDrawable(Assets.restartButton);
        restartStyle.imageUp.setMinWidth(64);
        restartStyle.imageUp.setMinHeight(64);
        pauseButton = new ImageButton(restartStyle);
    }

    private void configureWidgets() {
        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.dialogs.update(game.screen);
            }
        });
    }

    private void prepareWorld() {
        worldStage = new Stage(new FitViewport(Core.BOX2D_VIRTUAL_WIDTH, Core.BOX2D_VIRTUAL_HEIGHT));
        world = new World(worldStage, stage, true);
        stage.addActor(world);
    }

    private void getGameCamera() {
        gameCamera = (OrthographicCamera) worldStage.getCamera();
    }

    private void setLayout() {
        distanceLabel.setSize(224, 64);
        distanceLabel.setPosition(Core.VIRTUAL_WIDTH - distanceLabel.getWidth(), 1);
        heightLabel.setSize(160, 64);
        heightLabel.setPosition(Core.VIRTUAL_WIDTH / 2 + staminaBar.background.getWidth() - heightLabel.getWidth() / 2, 1);

        pauseButton.setSize(64, 64);
        pauseButton.setPosition(0, Core.VIRTUAL_HEIGHT - pauseButton.getHeight());

        stage.addActor(distanceLabel);
        stage.addActor(heightLabel);
        stage.addActor(pauseButton);
        stage.addActor(staminaBar);
    }

    private void setInputProcessor() {
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(gameInputProcessor = new GameInputProcessor(game, world));
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    private void prepareGameOverDialog() {
        gameOverDialog = new Window("UGHA UGH", Assets.skin);

        //( ͡° ͜ʖ ͡°) < l'elmar face

        gameOverDialog.setSize(512, 400);

        distance = new Label("", Assets.skin);
        distance.setPosition(25, gameOverDialog.getHeight() / 2);
        gameOverDialog.addActor(distance);

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
                game.setScreen(new GameScreen(game));
            }
        });
        shopButton.setSize(96, 96);
        shopButton.setPosition(gameOverDialog.getWidth() / 2 - shopButton.getWidth() / 2, 0);
        gameOverDialog.addActor(shopButton);

        gameOverDialog.setPosition(Core.VIRTUAL_WIDTH / 2 - gameOverDialog.getWidth() / 2, Core.VIRTUAL_HEIGHT / 2 + gameOverDialog.getHeight());

        gameOverDialog.setVisible(false);

        stage.addActor(gameOverDialog);

        Tween.to(gameOverDialog, ActorAccessor.CPOS_XY, 0.7f)
                .ease(Cubic.INOUT)
                .target(100, 50)
                .repeat(-10, 5.3f)
                .delay(0.5f)
                .start(tweenManager);
    }

    @Override
    public void render(float delta) {

        /**Updates*/
        if (!Core.dialogOpen) {
            gameInputProcessor.update(delta);
            updateUI();
            updateGameCam();
            world.update(delta);
            stage.act();

            if (world.isGameOver()) {
                gameOverDialog.setVisible(true);

                distance.setText(distanceLabel.getText().toString());
            }
        }

        /**Draws*/
        stage.draw();
    }

    private void updateUI() {
        distanceLabel.setText("Distance: " + String.valueOf(world.caveman.body.getPosition().x - Core.BOX2D_VIRTUAL_WIDTH / 3));
        heightLabel.setText("Height: " + String.valueOf(world.caveman.body.getPosition().y - 1.5f).substring(0, 3));
    }

    private void updateGameCam() {
        if (world.caveman.body.getPosition().x > 11.1f)
            if (world.caveman.body.getPosition().y >= 6.5f)
                gameCamera.position.set(world.caveman.body.getPosition().x + 5, world.caveman.body.getPosition().y + 2.5f, 0);
            else
                gameCamera.position.set(world.caveman.body.getPosition().x + 5, 9, 0);
        gameCamera.update();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
        worldStage.getViewport().update(width, height);
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}