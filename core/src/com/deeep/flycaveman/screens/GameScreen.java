package com.deeep.flycaveman.screens;

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
import com.deeep.flycaveman.input.GameInputProcessor;

/**
 * Created by scanevaro on 10/10/2014.
 */
public class GameScreen extends AbstractScreen {
    private Core game;
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
    private Label distanceLabel;
    private Label distanceTraveled;
    private Label heightLabel;
    private Label height;
    private ImageButton restartButton;
    private Window gameOverDialog;
    /**
     * World
     */
    private World world;

    public GameScreen(Core game) {
        this.game = game;
    }

    @Override
    public void show() {
        prepareScreen();
        setWidgets();
        configureWidgets();
        prepareWorld();
        getGameCamera();
        setLayout();
        setInputProcessor();
        prepareGameOverDialog();

//        if (!Assets.getAssets().getMusic().isPlaying())
//            Assets.getAssets().getMusic().play();
    }

    private void prepareScreen() {
        batch = game.getSpriteBatch();
        stage = new Stage(new FitViewport(Core.VIRTUAL_WIDTH, Core.VIRTUAL_HEIGHT), batch);
    }

    private void setWidgets() {
        distanceLabel = new Label("Distance: ", Assets.skin);
        distanceTraveled = new Label("", Assets.skin);
        heightLabel = new Label("Height", Assets.skin);
        height = new Label("", Assets.skin);

        ImageButton.ImageButtonStyle restartStyle = new ImageButton.ImageButtonStyle();
        restartStyle.imageUp = new TextureRegionDrawable(Assets.restartButton);
        restartStyle.imageUp.setMinWidth(96);
        restartStyle.imageUp.setMinHeight(96);
        restartButton = new ImageButton(restartStyle);
    }

    private void configureWidgets() {
        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
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
        distanceLabel.setPosition(Core.VIRTUAL_WIDTH - 130, 1);
        distanceTraveled.setPosition(Core.VIRTUAL_WIDTH - 50, 10);
        heightLabel.setPosition(Core.VIRTUAL_WIDTH / 2 - 80, 1);
        height.setPosition(Core.VIRTUAL_WIDTH / 2, 10);

        restartButton.setSize(96, 96);
        restartButton.setPosition(0, 0);

        stage.addActor(distanceLabel);
        stage.addActor(distanceTraveled);
        stage.addActor(heightLabel);
        stage.addActor(height);
        stage.addActor(restartButton);
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

        String distanceTraveled = String.valueOf(world.caveman.body.getPosition().x - Core.BOX2D_VIRTUAL_WIDTH / 3);

        Label distance = new Label(distanceLabel.getText().toString() + " " + distanceTraveled, Assets.skin);
        distance.setPosition(0, gameOverDialog.getHeight() / 2);
        gameOverDialog.addActor(distance);

        ImageButton.ImageButtonStyle retryStyle = new ImageButton.ImageButtonStyle();
        retryStyle.imageUp = new TextureRegionDrawable(Assets.restartButton);
        retryStyle.imageDown = new TextureRegionDrawable(Assets.restartButton);
        ImageButton retryButton = new ImageButton(retryStyle);
        retryButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
            }
        });
        retryButton.setSize(64, 64);
        retryButton.setPosition(gameOverDialog.getWidth() - retryButton.getWidth(), 0);
        gameOverDialog.addActor(retryButton);

        TextButton quitButton = new TextButton("Q U I T", Assets.skin);
        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        quitButton.setSize(64, 64);
        quitButton.setPosition(0, 0);
        gameOverDialog.addActor(quitButton);

        gameOverDialog.setPosition(Core.VIRTUAL_WIDTH / 2 - gameOverDialog.getWidth() / 2, Core.VIRTUAL_HEIGHT / 2 + gameOverDialog.getHeight());

        gameOverDialog.setVisible(false);

        stage.addActor(gameOverDialog);
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

            if (world.isGameOver())
                gameOverDialog.setVisible(true);
        }

        /**Draws*/
        stage.draw();
    }

    private void updateUI() {
        distanceTraveled.setText(String.valueOf(world.caveman.body.getPosition().x - Core.BOX2D_VIRTUAL_WIDTH / 3));
        height.setText(String.valueOf(world.caveman.body.getPosition().y - 1.5f).substring(0, 3));
    }

    private void updateGameCam() {
        gameCamera.position.set(world.caveman.body.getPosition().x, world.caveman.body.getPosition().y, 0);
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