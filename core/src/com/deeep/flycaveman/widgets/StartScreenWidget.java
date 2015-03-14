package com.deeep.flycaveman.widgets;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.deeep.flycaveman.Assets;
import com.deeep.flycaveman.Core;

/**
 * Created by scanevaro on 06/03/2015.
 */
public class StartScreenWidget {
    public Image title, touchNHold, leaderboardsDialog;
    private Label leaderboardsLabel, leaderboardsItems[];

    public StartScreenWidget() {
        title = new Image(Assets.title);
        title.setSize(400, 200);
        title.setOrigin(title.getWidth() / 2, title.getHeight() / 2);
        title.setPosition(Core.VIRTUAL_WIDTH, Core.VIRTUAL_HEIGHT / 2);
        title.addAction(Actions.moveTo(Core.VIRTUAL_WIDTH / 2, Core.VIRTUAL_HEIGHT / 2, 0.35f, Interpolation.linear));

        touchNHold = new Image(Assets.touchNH);
        touchNHold.setSize(632, 128);
        touchNHold.setOrigin(touchNHold.getWidth() / 2, touchNHold.getHeight() / 2);
        touchNHold.setPosition(150, 70);
        touchNHold.addAction(Actions.forever(Actions.sequence(Actions.fadeOut(0.6f), Actions.delay(0.2f), Actions.fadeIn(0.6f))));

        leaderboardsDialog = new Image(Assets.dialog);
        leaderboardsDialog.setSize(300, 350);
        leaderboardsDialog.setOrigin(leaderboardsDialog.getWidth() / 2, leaderboardsDialog.getHeight() / 2);
        leaderboardsDialog.setPosition(-leaderboardsDialog.getWidth(), 120);
        leaderboardsDialog.addAction(Actions.moveTo(50, 120, 0.4f, Interpolation.linear));

        leaderboardsLabel = new Label("Leaderboards", Assets.skin.get("defaultBackground", Label.LabelStyle.class));
        leaderboardsLabel.setAlignment(Align.center);
        leaderboardsLabel.setSize(280, 80);
        leaderboardsLabel.setPosition(-leaderboardsLabel.getWidth(), 390);
        leaderboardsLabel.addAction(Actions.moveTo(62, 390, 0.4f, Interpolation.linear));

        leaderboardsItems = new Label[6];
        int posY = 350;
        for (int i = 0; i < leaderboardsItems.length; i++) {
            if (i == leaderboardsItems.length - 1) {
                leaderboardsItems[i] = new Label("Getting your Best", Assets.skin.get("ownScore", Label.LabelStyle.class));
                posY -= 8;
                leaderboardsItems[i].setPosition(-leaderboardsItems[i].getWidth(), posY);
                leaderboardsItems[i].addAction(Actions.moveTo(64, posY, 0.4f, Interpolation.linear));
            } else {
                leaderboardsItems[i] = new Label("Getting info...", Assets.skin);
                leaderboardsItems[i].setPosition(-leaderboardsItems[i].getWidth(), posY);
                leaderboardsItems[i].addAction(Actions.moveTo(70, posY, 0.4f, Interpolation.linear));
                posY -= 28;
            }
        }
    }

    public void draw(Batch batch) {
        title.draw(batch, 1);

        leaderboardsDialog.draw(batch, 1);

        leaderboardsLabel.draw(batch, 1);

        for (Label label : leaderboardsItems)
            label.draw(batch, 1);
    }

    public void drawTut(Batch batch) {
        touchNHold.draw(batch, 1);
    }

    public void update(float delta) {
        title.act(delta);
        leaderboardsDialog.act(delta);
        leaderboardsLabel.act(delta);

        for (Label label : leaderboardsItems)
            label.act(delta);

        touchNHold.act(delta);
    }
}