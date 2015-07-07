package com.deeep.flycaveman.screens;

import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.deeep.flycaveman.Assets;
import com.deeep.flycaveman.Core;
import com.deeep.flycaveman.input.GameInputProcessor;
import com.deeep.flycaveman.widgets.*;
import com.deeep.flycaveman.world.MusicController;
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
    public Image darkness;
    private final float minZoom = 0.7f, maxZoom = 56;
    private final boolean debug = false;
    /**
     * Widgets
     */
    private TextButton distanceLabel, heightLabel;
    private ImageButton pauseButton;
    private ShopWidget shopWidget;
    private GameOverWidget gameOverWidget;
    private StaminaBar staminaBar;
    private ExpressionsWidget expressions;
    private CoinsWidget coinsWidget;
    private Dialog nameDialog;
    private TextField nameTextArea;
    private TutorialWidget tutorialWidget;
    public PickupsWidget pickupsWidget;
    /**
     * World
     */
    public World world;
    private float height;
    private float maxHeight;
    public static boolean retry;
    private static String name;
    private String distance;

    public GameScreen(Core game) {
        this.game = game;
        retry = false;
    }

    @Override
    public void show() {
        if (tweenManager == null) tweenManager = new TweenManager();

        maxHeight = 0;

        prepareScreen();
        setWidgets();
        prepareWorld();

        staminaBar = new StaminaBar(world.caveman);

        getGameCamera();
        configureWidgets();
        setLayout();
        setInputProcessor();
        prepareShopDialog();
        prepareGameOverDialog();
        prepareNameDialog();

        expressions.setCaveman(world.caveman);

        stage.addActor(darkness);
    }

    private void prepareScreen() {
        batch = game.getSpriteBatch();
        stage = new Stage(new FitViewport(Core.VIRTUAL_WIDTH, Core.VIRTUAL_HEIGHT), batch);

        darkness = new Image(Assets.darkSky);
        darkness.setPosition(0, 0);
        darkness.setSize(Core.VIRTUAL_WIDTH, Core.VIRTUAL_HEIGHT);
        darkness.addAction(Actions.fadeOut(0.5f));
        darkness.setTouchable(Touchable.disabled);
    }

    private void setWidgets() {
        distanceLabel = new TextButton("Distance: ", Assets.skin.get("defaultSmallFont", TextButton.TextButtonStyle.class));
        heightLabel = new TextButton("Height: ", Assets.skin.get("defaultSmallFont", TextButton.TextButtonStyle.class));
        ImageButton.ImageButtonStyle pauseStyle = new ImageButton.ImageButtonStyle();
        pauseStyle.imageUp = new TextureRegionDrawable(Assets.pauseUp);
        pauseStyle.imageUp.setMinWidth(64);
        pauseStyle.imageUp.setMinHeight(64);
        pauseStyle.imageDown = new TextureRegionDrawable(Assets.pauseUp);
        pauseStyle.imageDown.setMinWidth(64);
        pauseStyle.imageDown.setMinHeight(64);
        pauseButton = new ImageButton(pauseStyle);
        expressions = new ExpressionsWidget();
        coinsWidget = new CoinsWidget();
        tutorialWidget = new TutorialWidget();
        pickupsWidget = new PickupsWidget();
    }

    private void configureWidgets() {
        pauseButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.dialogs.update(game);
            }
        });

        distanceLabel.setColor(new Color(1, 1, 1, 0));
        heightLabel.setColor(new Color(1, 1, 1, 0));
        pauseButton.setColor(new Color(1, 1, 1, 0));
        staminaBar.setColor(new Color(1, 1, 1, 0));
        expressions.setColor(new Color(1, 1, 1, 0));
        coinsWidget.setColor(new Color(1, 1, 1, 0));
    }

    private void prepareWorld() {
        worldStage = new Stage(new FitViewport(Core.BOX2D_VIRTUAL_WIDTH, Core.BOX2D_VIRTUAL_HEIGHT));
        gameCamera = (OrthographicCamera) worldStage.getCamera();
        world = new World(game, worldStage, stage, debug);
        stage.addActor(world);
    }

    private void getGameCamera() {
        gameCamera = (OrthographicCamera) worldStage.getCamera();
        gameCamera.position.set(Core.BOX2D_VIRTUAL_WIDTH / 2, Core.BOX2D_VIRTUAL_HEIGHT / 2 - 3.55f, 0);
    }

    private void setLayout() {
        distanceLabel.setSize(255, 72);
        distanceLabel.setPosition(64 + 5, Core.VIRTUAL_HEIGHT - distanceLabel.getHeight() - 1);
        heightLabel.setSize(222, 72);
        heightLabel.setPosition(distanceLabel.getX() + distanceLabel.getWidth() + 5, Core.VIRTUAL_HEIGHT - heightLabel.getHeight() - 1);
        pauseButton.setSize(64, 64);
        pauseButton.setPosition(0, Core.VIRTUAL_HEIGHT - pauseButton.getHeight() - 1);
        coinsWidget.setPosition(300, 1);
        tutorialWidget.setSize(Core.VIRTUAL_WIDTH, Core.VIRTUAL_HEIGHT * 2);
        tutorialWidget.setPosition(0, Core.VIRTUAL_HEIGHT);

        stage.addActor(distanceLabel);
        stage.addActor(heightLabel);
        stage.addActor(pauseButton);
        stage.addActor(staminaBar);
        stage.addActor(expressions);
        stage.addActor(coinsWidget);
        stage.addActor(tutorialWidget);
        stage.addActor(pickupsWidget);
    }

    private void setInputProcessor() {
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(gameInputProcessor = new GameInputProcessor(game, world));
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    private void prepareShopDialog() {
        shopWidget = new ShopWidget(game);
    }

    private void prepareGameOverDialog() {
        gameOverWidget = new GameOverWidget(game, shopWidget);
    }

    private void prepareNameDialog() {
        if (name != null) return;

        nameDialog = new Dialog("Insert your name", Assets.skin.get("dialog", Window.WindowStyle.class)) {
            protected void result(Object object) {
                if (!nameTextArea.getText().equals(""))
                    name = nameTextArea.getText();
                else name = "Anon";

                nameDialog.addAction(Actions.moveTo(nameDialog.getX(), -Core.VIRTUAL_HEIGHT, 0.25f, Interpolation.linear));
                tutorialWidget.moveFirstDown();
            }
        };
        nameDialog.setKeepWithinStage(false);
        nameDialog.setSize(350, 200);

        nameTextArea = new TextField("", Assets.skin.get("default2", TextField.TextFieldStyle.class));
        nameTextArea.setSize(200, 64);
        nameTextArea.setPosition(nameDialog.getWidth() / 2 - 25, nameDialog.getHeight() / 2 + 5);
        nameTextArea.setMaxLength(7);
        nameDialog.addActor(nameTextArea);

        TextButton yesButton = new TextButton("Accept", Assets.skin);
        yesButton.setSize(128, 96);
        nameDialog.getButtonTable().add(yesButton).width(128).height(96);
        nameDialog.setObject(yesButton, null);

        nameDialog.show(stage);
    }

    @Override
    public void render(float delta) {
        MusicController.musicController.update(world.caveman);
        if (GameInputProcessor.flying) {
            distanceLabel.addAction(Actions.delay(0.5f, Actions.fadeIn(1.0f)));
            heightLabel.addAction(Actions.delay(0.5f, Actions.fadeIn(1.0f)));
            pauseButton.addAction(Actions.delay(0.5f, Actions.fadeIn(1.0f)));
            staminaBar.addAction(Actions.delay(0.5f, Actions.fadeIn(1.0f)));
            expressions.addAction(Actions.delay(0.5f, Actions.fadeIn(1.0f)));
            coinsWidget.fadeIn();
        }

        updateGameCam();

        /**Updates*/
        if (!Core.dialogOpen) {
            gameInputProcessor.update(delta);
            updateUI();

            world.update(delta, name);
            shopWidget.update();
            gameOverWidget.update(name, distance, maxHeight, world.caveman.flapDistance,
                    world.caveman.smacked, world.caveman.powerUpsPicked, world.caveman.coinsPicked);
            stage.act();

            if (world.isGameOver()) {

                distanceLabel.addAction(Actions.delay(0.25f, Actions.fadeOut(1.0f)));
                heightLabel.addAction(Actions.delay(0.25f, Actions.fadeOut(1.0f)));
                pauseButton.addAction(Actions.delay(0.25f, Actions.fadeOut(1.0f)));
                staminaBar.fadeOut();
                expressions.fadeOut();
                coinsWidget.fadeOut();

                if (!GameScreen.retry)
                    gameOverWidget.setVisible(true);
                else gameOverWidget.setVisible(false);
            }
        }
        /**Draws*/
        stage.draw();
    }

    private void updateUI() {
        //todo make this visible in gameoverscreen
        distance = String.valueOf((int) (world.caveman.body.getPosition().x - Core.BOX2D_VIRTUAL_WIDTH / 3));
        distanceLabel.setText("Distance: " + String.valueOf((int) (world.caveman.body.getPosition().x - Core.BOX2D_VIRTUAL_WIDTH / 3)));
        heightLabel.setText("Height: " + String.valueOf(world.caveman.body.getPosition().y - 1.5f).substring(0, 2));

        if (world.caveman.body.getPosition().y - 1.5f > maxHeight)
            maxHeight = world.caveman.body.getPosition().y - 1.5f;
    }

    private void updateGameCam() {
        if (GameInputProcessor.flying) {
            if (world.caveman.body.getPosition().y >= Core.BOX2D_VIRTUAL_HEIGHT / 2 - 3.55f) {
                gameCamera.position.set(world.caveman.body.getPosition().x + 5, world.caveman.body.getPosition().y + 0.5f, 0);
                /**Update Start screen camera*/
                world.updateStartCam((world.caveman.body.getPosition().x + 5) * 35, (world.caveman.body.getPosition().y + 2.5f) * 35, 0);
            } else {
                gameCamera.position.set(world.caveman.body.getPosition().x + 5, Core.BOX2D_VIRTUAL_HEIGHT / 2 - 3.55f, 0);
                /**Update Start screen camera*/
                world.updateStartCam((world.caveman.body.getPosition().x + 5) * 35, (Core.VIRTUAL_HEIGHT / 2), 0);
            }
        }

        if (world.caveman.body.getPosition().y > (Core.BOX2D_VIRTUAL_HEIGHT / 2f)) {
            height = world.caveman.body.getPosition().y - 9;
            gameCamera.zoom = minZoom + (height * 2.0f) / maxZoom;
            world.updateStartZoom(minZoom + (height * 2.0f) / maxZoom);
        } else {
            gameCamera.zoom = minZoom;
            world.updateStartZoom(minZoom);
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