package com.deeep.flycaveman.entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.deeep.flycaveman.classes.Assets;

/**
 * Created by scanevaro on 12/02/2015.
 */
public class DropButton extends Actor {
    private CaveMan caveMan;

    private ImageButton imageButton;
    private ImageButton.ImageButtonStyle imageButtonStyle;

    public DropButton() {

        imageButtonStyle = new ImageButton.ImageButtonStyle();
        imageButtonStyle.imageUp = new TextureRegionDrawable(Assets.dropUp);
        imageButtonStyle.imageDown = new TextureRegionDrawable(Assets.dropUp);
        imageButton = new ImageButton(imageButtonStyle);

        imageButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                caveMan.drop();
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