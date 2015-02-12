package com.deeep.flycaveman.screens;

import aurelienribon.tweenengine.TweenManager;
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
import com.deeep.flycaveman.classes.Assets;
import com.deeep.flycaveman.classes.World;
import com.deeep.flycaveman.entities.DropButton;
import com.deeep.flycaveman.entities.FlapButton;
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
    public static Label distance;
    private ImageButton pauseButton;
    private Window gameOverDialog, shopDialog;
    private StaminaBar staminaBar;
    private DropButton dropButton;
    private FlapButton flapButton;
    /**
     * World
     */
    public World world;

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
        prepareShopDialog();

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

        flapButton = new FlapButton();
        dropButton = new DropButton();
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

        flapButton.setCaveMan(world.caveman);
        dropButton.setCaveMan(world.caveman);
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
        flapButton.setSize(96, 96);
        flapButton.setPosition(0, 0);
        dropButton.setSize(96, 96);
        dropButton.setPosition(Core.VIRTUAL_WIDTH - dropButton.getWidth(), 0);

        stage.addActor(distanceLabel);
        stage.addActor(heightLabel);
        stage.addActor(pauseButton);
        stage.addActor(staminaBar);
        stage.addActor(flapButton);
        stage.addActor(dropButton);
    }

    private void setInputProcessor() {
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(gameInputProcessor = new GameInputProcessor(game, world));
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    private void prepareGameOverDialog() {
        gameOverDialog = game.dialogs.buildGameOver(game);
    }

    private void prepareShopDialog() {
        shopDialog = game.dialogs.buildShop(game);
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
                if (shopDialog.isVisible()) gameOverDialog.setVisible(false);
                else gameOverDialog.setVisible(true);

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
        if (world.caveman.body.getPosition().x > world.caveman.startPosX)
            if (world.caveman.body.getPosition().y >= world.caveman.startPosY)
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