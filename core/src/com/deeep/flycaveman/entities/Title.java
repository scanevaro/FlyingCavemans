package com.deeep.flycaveman.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.deeep.flycaveman.Assets;

/**
 * Created by scanevaro on 06/03/2015.
 */
public class Title implements Entity {
    private float sizeX = 8, sizeY = 4.0f, positionX = 18, positionY = 9;

    public Image title, touchNHold;
    private Window leader;

    public Title(Stage stage) {
        title = new Image(Assets.title);
        title.setSize(sizeX * 2, sizeY * 2);
        title.setOrigin(title.getWidth() / 2, title.getHeight() / 2);
        title.setPosition(positionX - title.getWidth() / 2, positionY - title.getHeight() / 2);

        touchNHold = new Image(Assets.touchNH);
        touchNHold.setSize(19, 3.5f);
        touchNHold.setOrigin(touchNHold.getWidth() / 2, touchNHold.getHeight() / 2);
        touchNHold.setPosition(4.5f, 0.35f);

        touchNHold.addAction(Actions.forever(Actions.sequence(Actions.fadeOut(0.6f), Actions.delay(0.2f), Actions.fadeIn(0.6f))));

        leader = new Window("Leaderboards", Assets.skin.get("default1", Window.WindowStyle.class));
        leader.setSize(5, 5);
        leader.setOrigin(leader.getWidth() / 2, leader.getHeight() / 2);
        leader.setPosition(0, 0);
        stage.addActor(leader);
    }

    @Override
    public void draw(Batch batch) {
        title.draw(batch, 1);

        touchNHold.draw(batch, 1);

//        leader.draw(batch, 1);
    }

    public void update(float delta) {
        touchNHold.act(delta);
    }
}