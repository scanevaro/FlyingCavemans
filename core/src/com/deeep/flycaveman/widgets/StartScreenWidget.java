package com.deeep.flycaveman.widgets;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.deeep.flycaveman.Assets;
import com.deeep.flycaveman.Core;

/**
 * Created by scanevaro on 06/03/2015.
 */
public class StartScreenWidget {
    private Image title, leaderboardsDialog;
    private Label leaderboardsLabel, leaderboardsItems[];
    private boolean getBest;

    public StartScreenWidget() {
        getBest = true;

        title = new Image(Assets.title);
        title.setSize(340, 190);
        title.setOrigin(title.getWidth() / 2, title.getHeight() / 2);
        title.setPosition(Core.VIRTUAL_WIDTH, Core.VIRTUAL_HEIGHT / 2);
        title.addAction(Actions.moveTo(Core.VIRTUAL_WIDTH / 2, Core.VIRTUAL_HEIGHT / 2, 0.35f, Interpolation.linear));

        leaderboardsDialog = new Image(Assets.dialog);
        leaderboardsDialog.setSize(255, 265);
        leaderboardsDialog.setOrigin(leaderboardsDialog.getWidth() / 2, leaderboardsDialog.getHeight() / 2);
        leaderboardsDialog.setPosition(-leaderboardsDialog.getWidth(), 170);
        leaderboardsDialog.addAction(Actions.moveTo(145, 170, 0.4f, Interpolation.linear));

        leaderboardsLabel = new Label("Leaderboards", Assets.skin.get("defaultBackground", Label.LabelStyle.class));
        leaderboardsLabel.setAlignment(Align.center);
        leaderboardsLabel.setSize(230, 60);
        leaderboardsLabel.setPosition(-leaderboardsLabel.getWidth(), 395);
        leaderboardsLabel.addAction(Actions.moveTo(150, 395, 0.4f, Interpolation.linear));

        leaderboardsItems = new Label[6];
        int posY = 360;
        for (int i = 0; i < leaderboardsItems.length; i++) {
            if (i == leaderboardsItems.length - 1) {
                leaderboardsItems[i] = new Label("Getting your Best", Assets.skin.get("ownScore", Label.LabelStyle.class));
                posY -= 8;
                leaderboardsItems[i].setPosition(-leaderboardsItems[i].getWidth(), posY);
                leaderboardsItems[i].addAction(Actions.moveTo(172, posY, 0.4f, Interpolation.linear));
            } else {
                leaderboardsItems[i] = new Label("Getting info...", Assets.skin.get("scoreDefault", Label.LabelStyle.class));
                leaderboardsItems[i].setPosition(-leaderboardsItems[i].getWidth(), posY);
                leaderboardsItems[i].addAction(Actions.moveTo(170, posY, 0.4f, Interpolation.linear));
                posY -= 28;
            }
        }
        getScores();
    }

    public void draw(Batch batch) {
        title.draw(batch, 1);
        leaderboardsDialog.draw(batch, 1);
        leaderboardsLabel.draw(batch, 1);
        for (Label label : leaderboardsItems) label.draw(batch, 1);
    }

    public void update(float delta, String name) {
        title.act(delta);
        leaderboardsDialog.act(delta);
        leaderboardsLabel.act(delta);
        for (int i = 0; i < leaderboardsItems.length; i++) leaderboardsItems[i].act(delta);
        getBest(name);
    }

    private void getScores() {
        Net.HttpRequest requestBests = new Net.HttpRequest(Net.HttpMethods.GET);
        if (Gdx.app.getType() == Application.ApplicationType.WebGL)
            requestBests.setUrl("http:/games/cavemanHighscores.php");
        else requestBests.setUrl("http://dreamlo.com/lb/55100e6a6e51b6057c1a1828/pipe/5");
        Gdx.net.sendHttpRequest(requestBests, new Net.HttpResponseListener() {
            @Override
            public void handleHttpResponse(Net.HttpResponse httpResponse) {
                System.out.println(httpResponse);
                String string = new String(httpResponse.getResultAsString());
                String scores[] = string.split("\n");
                if (scores.length > 0)
                    for (int i = 0; i < scores.length; i++) {
                        String score[] = scores[i].split("\\|");
                        leaderboardsItems[i].setText(String.valueOf(Integer.valueOf(score[score.length - 1]) + 1) + ")" + score[0] + ": " + score[1]);
                    }
            }

            @Override
            public void failed(Throwable t) {
                System.out.println(t);
            }

            @Override
            public void cancelled() {
                System.out.println("Cancel");
            }
        });
    }

    private void getBest(String name) {
        if (name != null && !name.equals("") && getBest) {
            Net.HttpRequest requestBest = new Net.HttpRequest("GET");
            if (Gdx.app.getType() == Application.ApplicationType.WebGL)
                requestBest.setUrl("http://deeepgames.com/games/cavemanHighscores.php?name=" + name);
            else requestBest.setUrl("http://dreamlo.com/lb/55100e6a6e51b6057c1a1828/pipe-get/" + name);
            Gdx.net.sendHttpRequest(requestBest, new Net.HttpResponseListener() {
                @Override
                public void handleHttpResponse(Net.HttpResponse httpResponse) {
                    String string = new String(httpResponse.getResult());
                    String bests[] = string.split("\n");
                    if (bests.length > 0)
                        for (int x = 0; x < bests.length; x++) {
                            String best[] = bests[0].split("\\|");
                            leaderboardsItems[5].setText(String.valueOf(Integer.valueOf(best[best.length - 1]) + 1) + ")ME: " + best[1]);
                            GameOverWidget.maxDistanceF = best[1];
                        }
                }

                @Override
                public void failed(Throwable t) {
                }

                @Override
                public void cancelled() {
                }
            });
            getBest = false;
        }
    }
}