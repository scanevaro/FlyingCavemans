package com.deeep.flycaveman.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
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
    //Screen
    private SpriteBatch batch;
    private Stage worldStage;
    private GameInputProcessor gameInputProcessor;
    //Widgets
    private Label distanceLabel;
    private Label distanceTraveled;
    private Label heightLabel;
    private Label height;
    private ImageButton restartButton;
    private Window gameOverDialog;
    //World
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
        distanceLabel = new Label("Distance: ", Assets.getAssets().getSkin());
        distanceTraveled = new Label("", Assets.getAssets().getSkin());
        heightLabel = new Label("Height", Assets.getAssets().getSkin());
        height = new Label("", Assets.getAssets().getSkin());

        ImageButton.ImageButtonStyle topGun1Style = new ImageButton.ImageButtonStyle(Assets.getAssets().getSkin().get(Button.ButtonStyle.class));
        topGun1Style.imageUp = new TextureRegionDrawable(new TextureRegion(Assets.getAssets().getRestartButton()));
        restartButton = new ImageButton(topGun1Style);
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
//        gameOverDialog = new Window("UGHA UGH", Assets.getAssets().getSkin());
//
//        //( ͡° ͜ʖ ͡°) < l'elmar face
//
//        gameOverDialog.setSize(512, 256);
//
//        String distanceTraveled = String.valueOf(world.caveman.body.getPosition().x - Game.BOX2D_VIRTUAL_WIDTH / 3);
//
//        Label distance = new Label(distanceLabel.getText().toString() + " " + distanceTraveled, Assets.getAssets().getSkin());
//        distance.setPosition(20, gameOverDialog.getHeight() / 2);
//        gameOverDialog.addActor(distance);
//
//        TextButton retryButton = new TextButton("R e t r y", Assets.getAssets().getSkin());
//        retryButton.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                game.setScreen(new GameScreen());
//            }
//        });
//        retryButton.setPosition(gameOverDialog.getWidth() - retryButton.getWidth() - 2, 5);
//        gameOverDialog.addActor(retryButton);
//
//        TextButton quitButton = new TextButton("Q U I T", Assets.getAssets().getSkin());
//        quitButton.addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                Gdx.app.exit();
//            }
//        });
//        quitButton.setPosition(2, 5);
//
//        //add to dialog
//        gameOverDialog.addActor(quitButton);
//
//        gameOverDialog.setPosition(Game.VIRTUAL_WIDTH / 2 - gameOverDialog.getWidth() / 2, Game.VIRTUAL_HEIGHT / 2 + gameOverDialog.getHeight());
//
//        stage.addActor(gameOverDialog);
    }

    @Override
    public void render(float delta) {

        /**Updates*/
        if (!Core.dialogOpen) {
            gameInputProcessor.update(delta);
            updateUI();
            world.update(delta);
            stage.act();
        }

        /**Draws*/
        stage.draw();
//        if (world.isGameOver()) gameOverDialog.draw();
    }

    private void updateUI() {
        distanceTraveled.setText(String.valueOf(world.caveman.body.getPosition().x - Core.BOX2D_VIRTUAL_WIDTH / 3));
        height.setText(String.valueOf(world.caveman.body.getPosition().y - 1.5f).substring(0, 3));
    }

    @Override
    public void resume() {
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
        worldStage.getViewport().update(width, height);
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void dispose() {
    }
}