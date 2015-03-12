package com.deeep.flycaveman.widgets;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.deeep.flycaveman.Assets;
import com.deeep.flycaveman.Core;

/**
 * Created by scanevaro on 06/03/2015.
 */
public class StartScreen {
    public Image title, touchNHold, leaderboardsDialog;
    private Label leaderboardsLabel, leaderboardsItems[];

    public StartScreen() {
        title = new Image(Assets.title);
        title.setSize(400, 200);
        title.setOrigin(title.getWidth() / 2, title.getHeight() / 2);
        title.setPosition(Core.VIRTUAL_WIDTH / 2, Core.VIRTUAL_HEIGHT / 2);

        touchNHold = new Image(Assets.touchNH);
        touchNHold.setSize(632, 128);
        touchNHold.setOrigin(touchNHold.getWidth() / 2, touchNHold.getHeight() / 2);
        touchNHold.setPosition(150, 70);
        touchNHold.addAction(Actions.forever(Actions.sequence(Actions.fadeOut(0.6f), Actions.delay(0.2f), Actions.fadeIn(0.6f))));

        leaderboardsDialog = new Image(Assets.dialog);
        leaderboardsDialog.setSize(256, 350);
        leaderboardsDialog.setOrigin(leaderboardsDialog.getWidth() / 2, leaderboardsDialog.getHeight() / 2);
        leaderboardsDialog.setPosition(50, 120);

        leaderboardsLabel = new Label("Leaderboards", Assets.skin.get("defaultBackground", Label.LabelStyle.class));
        leaderboardsLabel.setAlignment(Align.center);
        leaderboardsLabel.setSize(180, 70);
        leaderboardsLabel.setPosition(76, 390);

        leaderboardsItems = new Label[6];
        int posY = 350;
        for (int i = 0; i < leaderboardsItems.length; i++) {
            if (i == leaderboardsItems.length - 1) {
                leaderboardsItems[i] = new Label("Getting your Best", Assets.skin.get("ownScore", Label.LabelStyle.class));
                posY -= 8;
                leaderboardsItems[i].setPosition(64, posY);
            } else {
                leaderboardsItems[i] = new Label("   Getting info...", Assets.skin);
                leaderboardsItems[i].setPosition(70, posY);
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
        touchNHold.act(delta);
    }
}