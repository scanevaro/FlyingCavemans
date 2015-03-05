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
import com.deeep.flycaveman.Assets;
import com.deeep.flycaveman.Core;
import com.deeep.flycaveman.entities.FlapButton;
import com.deeep.flycaveman.entities.StaminaBar;
import com.deeep.flycaveman.input.GameInputProcessor;
import com.deeep.flycaveman.widgets.ExpressionsWidget;
import com.deeep.flycaveman.widgets.SoundManager;
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
    private SoundManager soundManager;
    /**
     * Widgets
     */
    private TextButton distanceLabel, heightLabel;
    public static Label distance;
    private ImageButton pauseButton;
    private Window gameOverDialog, shopDialog;
    private StaminaBar staminaBar;
    //    private DropButton dropButton;
    private FlapButton flapButton;
    private ExpressionsWidget expressions;
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
        configureWidgets();
        prepareWorld();

        soundManager = game.soundManager;
        staminaBar = new StaminaBar(world.caveman);

        getGameCamera();
        setLayout();
        setInputProcessor();
        prepareGameOverDialog();
        prepareShopDialog();

        soundManager.playMusic(soundManager.getMusic("JungleTheme").getMusicObject(), true);

        Assets.font.setScale(0.5f);

        expressions.setCaveman(world.caveman);
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
//        dropButton = new DropButton();
        expressions = new ExpressionsWidget();
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
        gameCamera = (OrthographicCamera) worldStage.getCamera();
        world = new World(worldStage, stage, false);
        stage.addActor(world);

        flapButton.setCaveMan(world.caveman);
//        dropButton.setCaveMan(world.caveman);
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
//        dropButton.setSize(96, 96);
//        dropButton.setPosition(Core.VIRTUAL_WIDTH - dropButton.getWidth(), 0);

        stage.addActor(distanceLabel);
        stage.addActor(heightLabel);
        stage.addActor(pauseButton);
        stage.addActor(staminaBar);
        stage.addActor(flapButton);
//        stage.addActor(dropButton);
        stage.addActor(expressions);
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
            if (world.caveman.body.getPosition().y >= Core.BOX2D_VIRTUAL_HEIGHT / 2 - 2)
                gameCamera.position.set(world.caveman.body.getPosition().x + 5 + 5, world.caveman.body.getPosition().y + 0.5f, 0);
            else
                gameCamera.position.set(world.caveman.body.getPosition().x + 5 + 5, Core.BOX2D_VIRTUAL_HEIGHT / 2 - 2, 0);

        if (world.caveman.body.getPosition().y > (Core.BOX2D_VIRTUAL_HEIGHT / 2f)) {
            height = world.caveman.body.getPosition().y - 9;
            gameCamera.zoom = 0.9f + (height * 2.5f) / 56;
            System.out.println(world.caveman.body.getPosition().y + "   " + ((Core.BOX2D_VIRTUAL_HEIGHT / 2f)));
        } else {
            gameCamera.zoom = 0.9f;
            System.out.println("NOT " + world.caveman.body.getPosition().y + "   " + ((Core.BOX2D_VIRTUAL_HEIGHT / 2f)));
        }
        gameCamera.zoom = Math.min(gameCamera.zoom, 2f);
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
        world.dispose();
    }
}