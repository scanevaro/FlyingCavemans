package com.deeep.flycaveman.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.deeep.flycaveman.Assets;

/**
 * Created by scanevaro on 12/02/2015.
 */
public class FlapButton extends Actor {
    private CaveMan caveMan;

    private ImageButton imageButton;
    private ImageButton.ImageButtonStyle imageButtonStyle;

    public FlapButton() {

        imageButtonStyle = new ImageButton.ImageButtonStyle();
        imageButtonStyle.imageUp = new TextureRegionDrawable(Assets.flapUp);
        imageButtonStyle.imageDown = new TextureRegionDrawable(Assets.flapUp);
        imageButton = new ImageButton(imageButtonStyle);

        imageButton.addListener(new ClickListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                super.touchDown(event, x, y, pointer, button);
                caveMan.flap();
                return true;
            }

            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                super.touchUp(event, x, y, pointer, button);
            }
        });
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        batch.setColor(getColor().r, getColor().g, getColor().b, getColor().a * parentAlpha);

        imageButton.draw(batch, parentAlpha);
    }

    @Override
    public boolean fire(Event event) {
        super.fire(event);

        imageButton.fire(event);

        return true;
    }

    public void setCaveMan(CaveMan caveMan) {
        this.caveMan = caveMan;
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        imageButton.setPosition(x, y);
    }

    @Override
    public void setSize(float width, float height) {
        super.setSize(width, height);

        imageButtonStyle.imageUp.setMinWidth(width);
        imageButtonStyle.imageUp.setMinHeight(height);
        imageButtonStyle.imageDown.setMinWidth(width);
        imageButtonStyle.imageDown.setMinHeight(height);

        imageButton.setSize(width, height);
    }
}